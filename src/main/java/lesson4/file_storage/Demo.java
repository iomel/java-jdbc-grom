package lesson4.file_storage;

public class Demo {
    public static void main(String[] args) throws Exception{
        Controller controller = new Controller();
        File file1 = new File(123, "text1", "txt", 110);
        File file2 = new File(113, "text2", "txt", 90);
        File file3 = new File(124, "pict1", "png", 110);


        FileDAO fileDAO = new FileDAO();

        File workFile = fileDAO.findById(124);
        if(workFile == null)
            System.out.println("NO SUCH FILE IN DB");
        else
            System.out.println(fileDAO.findById(124).toString());

     //   fileDAO.save(file1);
        Storage storage1 = new Storage(11, new String[] {"txt","jpg"}, "UA", 1000 );
        Storage storage2 = new Storage(12, new String[] {"txt","doc"}, "UA", 300 );

        StorageDAO storageDAO = new StorageDAO();
//        storageDAO.save(storage2);

//        controller.put(storage1, file2);
//        storage1.setFiles(fileDAO.findByStorageId(storage1.getId()));
//        storage2.setFiles(fileDAO.findByStorageId(storage2.getId()));
//
//        controller.transferAll(storage2, storage1);
        controller.put(storage1, file3);
        System.out.println(fileDAO.findById(124).toString());
    }
}
