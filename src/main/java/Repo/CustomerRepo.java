package Repo;

import Domain.Account;
import Domain.Customer;
import Domain.Product;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class CustomerRepo implements CustomerRepoInterface {
    @Override
    public Customer getOne(Long id) {
//        logger.traceEntry();
        Customer customer = null;
        try{
            Hibernater.initialize();
            try(Session session = Hibernater.sessionFactory.openSession()) {
                Transaction tx = null;
                try {
                    tx = session.beginTransaction();
                    customer =
                            session.createQuery("from Customer where id="+id, Customer.class).getSingleResult();

                    System.out.println("customer found:"+customer);
                    tx.commit();
                } catch (RuntimeException ex) {
                    if (tx != null)
                        tx.rollback();
                }
            }
        }catch (Exception ex){
//            logger.error(ex);
            System.err.println("Error DB"+ex);
        }
        finally {
            Hibernater.close();
//            logger.traceExit();
        }
        return customer;
    }

    @Override
    public Iterable<Customer> getAll() {
        //        logger.traceEntry();
        List<Customer> customers = null;
        try{
            Hibernater.initialize();
            try(Session session = Hibernater.sessionFactory.openSession()) {
                Transaction tx = null;
                try {
                    tx = session.beginTransaction();
                    customers = session.createQuery("from Customer", Customer.class).
//                            setFirstResult(10).setMaxResults(5).
        list();

                    tx.commit();
                } catch (RuntimeException ex) {
                    if (tx != null)
                        tx.rollback();
                }
            }
        }catch (Exception ex){
//            logger.error(ex);
            System.err.println("Error DB"+ex);
        }
        finally {
            Hibernater.close();
//            logger.traceExit();
        }
        return customers;
    }

    @Override
    public Customer add(Customer entity) {
        //        logger.traceEntry("saving task {}",entity);
        try {
            Hibernater.initialize();
            try (Session session = Hibernater.sessionFactory.openSession()) {
                Transaction tx = null;
                try {
                    tx = session.beginTransaction();
                    Customer customer = new Customer(entity.getId(), entity.getName(), entity.getCompany());
                    session.save(customer);
                    tx.commit();
                } catch (RuntimeException ex) {
                    if (tx != null)
                        tx.rollback();
                }
            }
        }catch (Exception ex){
//            logger.error(ex);
            System.err.println("Error DB"+ex);
        }finally {
            Hibernater.close();
//            logger.traceExit();

        }
        return null;
    }
}
