package lesson4.file_storage;

public class Controller {

    private FileDAO fileDAO = new FileDAO();
    private StorageDAO storageDAO = new StorageDAO();


    public void put(Storage storage, File file) throws Exception
    {
        if (storage != null && file != null) {
            storage = storageDAO.findById(storage.getId());

            isEnoughSpace(storage, file);
            formatsAllowed(storage, file);
            file.setStorageId(storage.getId());

            if (fileDAO.findById(file.getId()) != null)
                fileDAO.update(file);
            else
                fileDAO.save(file);
        }
    }

    public void delete (Storage storage, File file) throws Exception
    {
        if (storage != null && file != null && fileDAO.findById(file.getId()).getStorageId() == storage.getId()) {
            file.setStorageId(0);
            fileDAO.update(file);
        }
    }

    public void transferAll (Storage storageFrom, Storage storageTo) throws Exception {
        if (storageFrom != null && storageTo != null) {
            storageFrom = storageDAO.findById(storageFrom.getId());
            storageTo = storageDAO.findById(storageTo.getId());

            hasSpaceForTransfer(storageFrom, storageTo);
            includeFormats(storageFrom, storageTo);

            File[] filesToTransfer = storageFrom.getFiles();
            for (File file : filesToTransfer)
                file.setStorageId(storageTo.getId());
            fileDAO.update(filesToTransfer);
        }
    }

    public void transferFile (Storage storageFrom, Storage storageTo, long id) throws Exception {
        if (storageTo != null) {
            put(storageTo, fileDAO.findById(id));
        }
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

        if(storageTo.getFiles() != null)
            for (File f : storageTo.getFiles())
                if(f != null && !f.isEmpty())
                    toFilesSize += f.getSize();

        if (storageTo.getStorageSize() - toFilesSize < fromFilesSize)
            throw new Exception("Transfer stopped - Not enough space. Source storage:" + storageFrom.getId()
                    + " Destination storage:" + storageTo.getId());
    }

    private boolean formatsAllowed(Storage storage, File file) throws Exception
    {
        for (String format : storage.getFormatsSupported())
            if(format.equals(file.getFormat()))
                return true;
        throw new Exception("File format is not allowed in the storage: " + storage.getId() + "    file ID: " + file.getId());
    }

    private void isEnoughSpace(Storage storage, File file) throws Exception
    {
        long totalSize = storage.getStorageSize();
        if(storage.getFiles() != null)
            for (File f : storage.getFiles())
                if(f != null)
                    totalSize -= f.getSize();

        if (totalSize < file.getSize())
            throw new Exception("Not enough free space in the storage: " + storage.getId() + "    file ID: " + file.getId());
    }

}
