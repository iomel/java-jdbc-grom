package lesson4.file_storage;

import lesson4.file_storage.dao.FileDAO;
import lesson4.file_storage.model.File;

public class Demo {
    public static void main(String[] args) {
        File file1 = new File(123, "text1", "txt", 110);
        FileDAO fileDAO = new FileDAO();

     //   fileDAO.save(file1);

        System.out.println(fileDAO.findById(123).toString());
    }
}
