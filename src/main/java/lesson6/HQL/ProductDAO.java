package lesson6.HQL;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;


public class ProductDAO {
    private static SessionFactory factory;
    private String SELECT_BY_ID = "from Product where id = :id";
    private String SELECT_BY_NAME = "from Product where name = :name";
    private String SELECT_BY_CONTAIN_NAME = "from Product where name like :name";
    private String SELECT_BY_NAME_ASC_SORT = "from Product where name = :name order by name asc";
    private String SELECT_BY_NAME_DESC_SORT = "from Product where name = :name order by name desc";
    private String SELECT_BY_PRICE_DELTA = "from Product where price between :mDelta and :pDelta";
    private String SELECT_BY_PRICE_DELTA_DESC_SORT = "from Product where price between :mDelta and :pDelta order by price desc";

    public ProductDAO() {
        factory = new Configuration().configure().buildSessionFactory();
    }

    private List<Product> findByOneParam (String sqlQuery, String paramName, Object paramValue){
        List<Product> list = null;
        try (Session session = factory.openSession()) {
            Query query = session.createQuery(sqlQuery);
            query.setParameter(paramName, paramValue);
            list = query.list();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return list;
    }

    private List<Product> findByTwoParam (String sqlQuery, String pName1, Object pValue1, String pName2, Object pValue2){
        List<Product> list = null;
        try (Session session = factory.openSession()) {
            Query query = session.createQuery(sqlQuery);
            query.setParameter(pName1, pValue1);
            query.setParameter(pName2, pValue2);
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
