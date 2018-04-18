package lesson7;

import java.util.HashMap;
import java.util.List;

public class HotelDAO extends GeneralDAO<Hotel> {

    private String SELECT_BY_ID = "FROM Hotel WHERE H_ID = :ID";

    // delete
    public void delete(long id) {
        delete(findById(id));
    }

    public Hotel findById(Long id) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("ID", id);
        List<Hotel> result = findByParam(SELECT_BY_ID, paramMap);
        return result.size() > 0 ? result.get(0) : new Hotel();
    }

}
