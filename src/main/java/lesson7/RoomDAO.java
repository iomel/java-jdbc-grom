package lesson7;

import java.util.HashMap;
import java.util.List;

public class RoomDAO extends GeneralDAO<Room> {

    private String SELECT_BY_ID = "FROM Room WHERE R_ID = :ID";

    // delete
    public void delete(long id) {
        delete(findById(id));
    }

    public Room findById(Long id) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("ID", id);
        List<Room> result = findByParam(SELECT_BY_ID, paramMap);
        return result.size() > 0 ? result.get(0) : new Room();
    }

}
