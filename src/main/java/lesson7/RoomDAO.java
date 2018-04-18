package lesson7;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.HashMap;

public class RoomDAO extends GeneralDAO<Room> {

    private String SELECT_BY_ID = "FROM Room WHERE R_ID = :ID";

    // delete
    public void delete(long id) {
        delete(findById(id));
    }

    public Room findById(Long id) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("ID", id);
        return findByParam(SELECT_BY_ID, paramMap).get(0);
    }

}
