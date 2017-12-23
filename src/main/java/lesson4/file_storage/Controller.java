package lesson4.file_storage;

import lesson4.file_storage.dao.FileDAO;
import lesson4.file_storage.dao.StorageDAO;
import lesson4.file_storage.model.File;
import lesson4.file_storage.model.Storage;

import java.util.Arrays;

public class Controller {

    private FileDAO fileDAO = new FileDAO();
    private StorageDAO storageDAO = new StorageDAO();


    public Storage put(Storage storage, File file) throws Exception
    {
        nullAbsentCheck(storage, file);
        isEnoughSpace(storage, file);
        formatsAllowed(storage, file);

        if(hasFile(storage, file))
            throw new Exception("There is such file in the storage: " + storage.getId() + "    file: " + file.getId());
        File[] oldFiles = storage.getFiles();
        File[] newFiles = Arrays.copyOf(oldFiles, oldFiles.length+1);
        newFiles[newFiles.length-1] = file;
        storage.setFiles(newFiles);

        return storageDAO.update(storage);
    }

    public void delete (Storage storage, File file) throws Exception
    {
        nullAbsentCheck(storage, file);

        if(!hasFile(storage, file))
            throw new Exception("There is no such file in the storage: " + storage.getId() + "    file: " + file.getId());

        File[] oldFiles = storage.getFiles();
        File[] newFiles = new File[oldFiles.length-1];
        int index = 0;
        for (File f : oldFiles)
            if (f.getId() != file.getId())
                newFiles[index++] = f;

        storage.setFiles(newFiles);
        storageDAO.update(storage);
    }

    public void transferAll (Storage storageFrom, Storage storageTo) throws Exception {
        nullCheckStorages(storageFrom, storageTo);
        hasSpaceForTransfer(storageFrom,storageTo);
        includeFormats(storageFrom, storageTo);
        idDuplicate(storageFrom, storageTo);

        for (File fileToTransfer : storageFrom.getFiles()) {
            if (fileToTransfer !=null && !fileToTransfer.isEmpty()) {
                put(storageTo, fileToTransfer);
                delete(storageFrom, fileToTransfer);
            }
        }
    }

    public void transferFile (Storage storageFrom, Storage storageTo, long id) throws Exception {
        nullCheckStorages(storageFrom, storageTo);

        File fileToTransfer = fileDAO.findById(id);
        storageTo = put(storageTo, fileToTransfer);
        storageFrom = storageDAO.findById(storageFrom.getId());
    }

    private void nullCheckStorages (Storage storageFrom, Storage storageTo) throws Exception {
        if (storageFrom == null || storageFrom.getFiles() == null
                || storageTo == null || storageTo.getFiles() == null
                || storageFrom.getId() == storageTo.getId()
                || storageFrom.getFormatsSupported() == null
                || storageTo.getFormatsSupported() == null )
            throw new Exception("Transfer stopped - some data is NULL. Source storage:" + storageFrom.getId()
                    + "Destination storage:" + storageTo.getId());
    }


    private void includeFormats (Storage storageFrom, Storage storageTo)throws Exception{
        for (File sourceFile : storageFrom.getFiles())
            if (sourceFile != null && !sourceFile.isEmpty())
                formatsAllowed(storageTo, sourceFile);
    }

    private void hasSpaceForTransfer(Storage storageFrom, Storage storageTo) throws Exception {
        long fromFilesSize = 0;
        long toFilesSize = 0;
        for (File f : storageFrom.getFiles())
            if(f != null && !f.isEmpty())
                fromFilesSize += f.getSize();

        for (File f : storageTo.getFiles())
            if(f != null && !f.isEmpty())
                toFilesSize += f.getSize();

        if (storageTo.getStorageSize() - toFilesSize < fromFilesSize)
            throw new Exception("Transfer stopped - Not enough space. Source storage:" + storageFrom.getId()
                    + " Destination storage:" + storageTo.getId());
    }

    private void idDuplicate (Storage storageFrom, Storage storageTo) throws Exception {
        for (File sourceFile : storageFrom.getFiles()) {
            if (sourceFile != null && !sourceFile.isEmpty())
                if (hasFile(storageTo, sourceFile))
                    throw new Exception("Duplicate files. Storage1 id:" + storageFrom.getId() + "    Storage2 id: " + storageTo.getId());
        }
    }

    private void nullAbsentCheck (Storage storage, File file) throws Exception
    {
        if (storage == null
                || storage.getFiles() == null
                || storage.getFormatsSupported() == null
                || file == null
                || file.isEmpty()
                || file.getFormat() == null)
            throw new Exception("Put operation break - some data is NULL. Storage id:" + storage.getId() + "    file: " + file.getId());
    }

    private boolean formatsAllowed(Storage storage, File file) throws Exception
    {
        for (String format : storage.getFormatsSupported())
            if(format.equals(file.getFormat()))
                return true;
        throw new Exception("File format is not allowed in the storage: " + storage.getId() + "    file: " + file.getId());
    }

    private void isEnoughSpace(Storage storage, File file) throws Exception
    {
        long totalSize = storage.getStorageSize();
        for (File f : storage.getFiles())
            if(f != null)
                totalSize -= f.getSize();

        if (totalSize < file.getSize())
            throw new Exception("Not enough free space in the storage: " + storage.getId() + "    file: " + file.getId());
    }

    public boolean hasFile (Storage storage, File file)
    {
        for (File f : storage.getFiles())
            if (f != null && f.getId() == file.getId())
                return true;
        return false;
    }

}
