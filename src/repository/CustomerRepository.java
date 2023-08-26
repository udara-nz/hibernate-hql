package repository;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import entity.CustomerEntity;
import entity.OrderEntity;
import util.SessionFactoryConfiguration;

public class CustomerRepository {
    Session session = SessionFactoryConfiguration.getInstance().getSession();

    public CustomerEntity getCustomer(String id) {
        CustomerEntity customerEntity = session.get(CustomerEntity.class, id);
        return customerEntity;
    }

    public List<CustomerEntity> getAllCustomer() {
        String hql = "FROM  CusomerEntity";
        Query query = session.createQuery(hql);
        List<CustomerEntity> customerEntities = query.list();
        return customerEntities;
    }

    public List<Object[]> getAllCustomerNativeQuery(){
        String sql = "SELECT * FROM Customer";
        Query query = session.createQuery(sql);
        List<Object[]> data = query.list();
        return data;
    }

    public List<Object[]> getAllCustomerNameAndDob() {
        String hql = "SELECT c.id, c.name, c.dob FROM CustomerEntity AS c";
        Query query = session.createQuery(hql);
        List<Object[]> data = query.list();
        return data;
    }

    public CustomerEntity findCustomerById(String id) {
        String hql = "FROM CustomerEntity WHERE id = '" + id + "'";
        Query query = session.createQuery(hql);
        CustomerEntity customerEntity = (CustomerEntity) query.uniqueResult();
        return customerEntity;
    }

    public CustomerEntity findCustomerByIdUsingNamedParameters(String id) {
        String hql = "FROM CustomerEntity WHERE id=:id";
        Query query = session.createQuery(hql);
        query.setParameter("id", id);

        CustomerEntity customerEntity = (CustomerEntity) query.uniqueResult();
        return customerEntity;

    }

    public List<CustomerEntity> getAllCustomersOrderByNameDesc() {
        String hql = "FROM CustomerEntity ORDER BY name DESC";
        Query query = session.createQuery(hql);
        List<CustomerEntity> customerEntities = query.list();
        return customerEntities;
    }

    // SELECT count(*), max(salary), min(salary), avg(salary), sum(salary) from Customer;
    public Object[] getCutsomerSummery() {
        String hql = "SELECT count(id), max(salary), min(salary), avg(salary), sum(salary) FROM CustomerEntity";
        Query query = session.createQuery(hql);
        Object[] data = (Object[]) query.uniqueResult();
        return data;
    }

    //SELECT province, count(CustID) AS count,  sum(salary) AS total, avg(salary) AS average FROM Customer GROUP BY province;

    public List<Object[]> getCutsomerSummeryByProvince() {
        String hql = "SELECT province, count(id), max(salary), min(salary), avg(salary), sum(salary) FROM CustomerEntity GROUP BY province";
        Query query = session.createQuery(hql);
        List<Object[]> data = query.list();
        return data;
    }

    public List<OrderEntity> getOrdersBeforeDateAndProvince(Date date, String province){
        String hql = "SELECT ord FROM CustomerEntity as cust inner join cust.orderEntities as ord WHERE ord.date < :date AND cust.province=:province";
        Query query = session.createQuery(hql);
        query.setParameter("date", date);
        query.setParameter("province", province);
        List<OrderEntity> orderEntities = query.list();
        return orderEntities;
        
    }
}
