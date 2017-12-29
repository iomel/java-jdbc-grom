package lesson4.file_storage;

public class Controller {

    private FileDAO fileDAO = new FileDAO();
    private StorageDAO storageDAO = new StorageDAO();


    public void put(Storage storage, File file) throws Exception
    {
        if (storage != null && file != null) {
            storage = storageDAO.findById(storage.getId());

            if(storage.getFreeSpace() < file.getSize())
                throw new Exception("Not enough free space to save file ID:" + file.getId() + " to storage ID:" + storage.getId());
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
        if(storage != null && file != null) {
            file = fileDAO.findById(file.getId());

            if (file.getStorageId() == storage.getId()) {
                file.setStorageId(0);
                fileDAO.update(file);
            }
        }
    }

    public void transferAll (Storage storageFrom, Storage storageTo) throws Exception {
        if (storageFrom != null && storageTo != null) {
            storageFrom = storageDAO.findById(storageFrom.getId());
            storageTo = storageDAO.findById(storageTo.getId());

            if(storageFrom.getUsedSpace() > storageTo.getFreeSpace())
                throw new Exception("Transfer stopped - Not enough space. Source storage:" + storageFrom.getId()
                        + " Destination storage:" + storageTo.getId());

            File[] filesToTransfer = storageFrom.getFiles();
            for (File file : filesToTransfer)
                if(formatsAllowed(storageTo, file))
                    file.setStorageId(storageTo.getId());
                else
                    throw new Exception("Transfer stopped - file format mismatch! File ID: " + file.getId() +
                            "Source storage:" + storageFrom.getId() + " Destination storage:" + storageTo.getId());
            fileDAO.update(filesToTransfer);
        }
    }

    public void transferFile (Storage storageFrom, Storage storageTo, long id) throws Exception {
        if (storageTo != null) {
            put(storageTo, fileDAO.findById(id));
        }
    }

    private boolean formatsAllowed(Storage storage, File file) throws Exception
    {
        for (String format : storage.getFormatsSupported())
            if(format.equals(file.getFormat()))
                return true;
        throw new Exception("File format is not allowed in the storage: " + storage.getId() + "    file ID: " + file.getId());
    }

}
