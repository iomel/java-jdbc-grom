package lesson6.NSQL;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;

import java.util.List;


public class ProductDAO {
    private static SessionFactory factory;
    private String SELECT_BY_ID = "SELECT * FROM Product WHERE id = :id";
    private String SELECT_BY_NAME = "SELECT * FROM Product WHERE name = :name";
    private String SELECT_BY_CONTAIN_NAME = "SELECT * FROM Product WHERE name LIKE :name";
    private String SELECT_BY_NAME_ASC_SORT = "SELECT * FROM Product WHERE name = :name ORDER BY name ASC";
    private String SELECT_BY_NAME_DESC_SORT = "SELECT * FROM Product WHERE name = :name ORDER BY name DESC";
    private String SELECT_BY_PRICE_DELTA = "SELECT * FROM Product WHERE price BETWEEN :mDelta AND :pDelta";
    private String SELECT_BY_PRICE_DELTA_DESC_SORT = "SELECT * FROM Product WHERE price BETWEEN :mDelta AND :pDelta ORDER BY price DESC";


    public ProductDAO() {
        factory = new Configuration().configure().buildSessionFactory();
    }

    private List<Product> findByOneParam (String sqlQuery, String paramName, Object paramValue){
        List<Product> list = null;
        try (Session session = factory.openSession()) {
            NativeQuery query = session.createNativeQuery(sqlQuery);
            query.setParameter(paramName, paramValue);
            query.addEntity(Product.class);
            list = query.list();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return list;
    }

    private List<Product> findByTwoParam (String sqlQuery, String pName1, Object pValue1, String pName2, Object pValue2){
        List<Product> list = null;
        try (Session session = factory.openSession()) {
            NativeQuery query = session.createNativeQuery(sqlQuery);
            query.setParameter(pName1, pValue1);
            query.setParameter(pName2, pValue2);
            query.addEntity(Product.class);
            list = query.list();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return list;
    }


    public Product findById(Long id) {
        return findByOneParam(SELECT_BY_ID, "id", id).get(0);
    }

    public List<Product> findByName(String name) {
        return findByOneParam(SELECT_BY_NAME, "name", name);
    }

    public List<Product> findByContainedName(String name){
        return findByOneParam(SELECT_BY_CONTAIN_NAME, "name", "%" + name + "%");
    }

    public List<Product> findByPrice(int price, int delta){
        return findByTwoParam(SELECT_BY_PRICE_DELTA,
                "mDelta", price - delta,
                "pDelta", price + delta);
    }

    public List<Product> findByNameSortedAsc(String name){
        return findByOneParam(SELECT_BY_NAME_ASC_SORT, "name", name);
    }

    public List<Product> findByNameSortedDesc(String name){
        return findByOneParam(SELECT_BY_NAME_DESC_SORT, "name", name);
    }

    public List<Product> findByPriceSortedDesc(int price, int delta) {
        return findByTwoParam(SELECT_BY_PRICE_DELTA_DESC_SORT,
                "mDelta", price - delta,
                "pDelta", price + delta);
    }

}
