package lesson6.NSQL;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ProductDAO {
    private static SessionFactory factory;
    private HashMap<String, Object> paramMap = new HashMap<>();
    private String SELECT_BY_ID = "SELECT * FROM PRODUCT WHERE ID = :ID";
    private String SELECT_BY_NAME = "SELECT * FROM PRODUCT WHERE NAME = :NAME";
    private String SELECT_BY_CONTAIN_NAME = "SELECT * FROM PRODUCT WHERE NAME LIKE :NAME";
    private String SELECT_BY_NAME_ASC_SORT = "SELECT * FROM PRODUCT WHERE NAME = :NAME ORDER BY NAME ASC";
    private String SELECT_BY_NAME_DESC_SORT = "SELECT * FROM PRODUCT WHERE NAME = :NAME ORDER BY NAME DESC";
    private String SELECT_BY_PRICE_DELTA = "SELECT * FROM PRODUCT WHERE PRICE BETWEEN :MDELTA AND :PDELTA";
    private String SELECT_BY_PRICE_DELTA_DESC_SORT = "SELECT * FROM PRODUCT WHERE PRICE BETWEEN :MDELTA AND :PDELTA ORDER BY PRICE DESC";


    public ProductDAO() {
        factory = new Configuration().configure().buildSessionFactory();
    }


    private List<Product> findByParam (String sqlQuery, HashMap<String, Object> params){
        List<Product> list = null;
        try (Session session = factory.openSession()) {
            NativeQuery query = session.createNativeQuery(sqlQuery);
            for(Map.Entry<String, Object> param : params.entrySet())
                query.setParameter(param.getKey(), param.getValue());
            query.addEntity(Product.class);
            list = query.list();
        } catch (HibernateException e) {
            e.printStackTrace();
        }

        return list;
    }

    public Product findById(Long id) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("ID", id);
        return findByParam(SELECT_BY_ID, paramMap).get(0);
    }

    public List<Product> findByName(String name) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("NAME", name);
        return findByParam(SELECT_BY_NAME, paramMap);
    }

    public List<Product> findByContainedName(String name){
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("NAME", "%" + name + "%");
        return findByParam(SELECT_BY_CONTAIN_NAME, paramMap);
    }

    public List<Product> findByPrice(int price, int delta){
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("MDELTA", price - delta);
        paramMap.put("PDELTA", price + delta);
        return findByParam(SELECT_BY_PRICE_DELTA, paramMap);
    }

    public List<Product> findByNameSortedAsc(String name){
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("NAME", name);
        return findByParam(SELECT_BY_NAME_ASC_SORT, paramMap);
    }

    public List<Product> findByNameSortedDesc(String name){
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("NAME", name);
        return findByParam(SELECT_BY_NAME_DESC_SORT, paramMap);
    }

    public List<Product> findByPriceSortedDesc(int price, int delta) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("MDELTA", price - delta);
        paramMap.put("PDELTA", price + delta);
        return findByParam(SELECT_BY_PRICE_DELTA, paramMap);
    }

}
