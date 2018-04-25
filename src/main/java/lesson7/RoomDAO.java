package lesson7;

import java.util.HashMap;
import java.util.List;

public class RoomDAO extends GeneralDAO<Room> {

    private String SELECT_BY_ID = "FROM Room WHERE R_ID = :ID";
    private String DELETE_BY_ID = "DELETE FROM Room WHERE R_ID = :ID";

    // delete
    public void delete(long id)
    {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("ID", id);
        deleteByParam(DELETE_BY_ID, paramMap);
    }

    public Room findById(Long id) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("ID", id);
        List<Room> result = findByParam(SELECT_BY_ID, paramMap);
        return result.size() > 0 ? result.get(0) : new Room();
    }

}
