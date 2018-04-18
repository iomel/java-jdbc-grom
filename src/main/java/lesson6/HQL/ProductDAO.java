package lesson6.HQL;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ProductDAO {
    private static SessionFactory factory;
    private String SELECT_BY_ID = "FROM Product WHERE ID = :ID";
    private String SELECT_BY_NAME = "FROM Product WHERE NAME = :NAME";
    private String SELECT_BY_CONTAIN_NAME = "FROM Product WHERE NAME LIKE :NAME";
    private String SELECT_BY_NAME_ASC_SORT = "FROM Product WHERE NAME = :NAME ORDER BY NAME ASC";
    private String SELECT_BY_NAME_DESC_SORT = "FROM Product WHERE NAME = :NAME ORDER BY NAME DESC";
    private String SELECT_BY_PRICE_DELTA = "FROM Product WHERE PRICE BETWEEN :MDELTA AND :PDELTA";
    private String SELECT_BY_PRICE_DELTA_DESC_SORT = "FROM Product WHERE PRICE BETWEEN :MDELTA AND :PDELTA ORDER BY PRICE DESC";

    public ProductDAO() {
        factory = new Configuration().configure().buildSessionFactory();
    }

    private List<Product> findByParam (String sqlQuery, HashMap<String, Object> params){
        List<Product> list = null;
        try (Session session = factory.openSession()) {
            Query query = session.createQuery(sqlQuery);
            for(Map.Entry<String, Object> param : params.entrySet())
                query.setParameter(param.getKey(), param.getValue());
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
        return findByParam(SELECT_BY_PRICE_DELTA_DESC_SORT, paramMap);
    }

}
