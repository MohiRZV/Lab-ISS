package Repo;

import Domain.Account;
import Domain.Product;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class ProductRepo implements ProductRepoInterface {
    @Override
    public Product getOne(Long id) {
        //        logger.traceEntry();
        Product product = null;
        try{
            Hibernater.initialize();
            try(Session session = Hibernater.sessionFactory.openSession()) {
                Transaction tx = null;
                try {
                    tx = session.beginTransaction();
                    product =
                            session.createQuery("from Product where id="+id, Product.class).getSingleResult();

                    System.out.println("product found:"+product);
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
        return product;
    }

    @Override
    public Iterable<Product> getAll() {
        //        logger.traceEntry();
        List<Product> products = null;
        try{
            Hibernater.initialize();
            try(Session session = Hibernater.sessionFactory.openSession()) {
                Transaction tx = null;
                try {
                    tx = session.beginTransaction();
                    products = session.createQuery("from Product", Product.class).
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
        return products;
    }

    @Override
    public Product add(Product entity) {
        //        logger.traceEntry("saving task {}",entity);
        try {
            Hibernater.initialize();
            try (Session session = Hibernater.sessionFactory.openSession()) {
                Transaction tx = null;
                try {
                    tx = session.beginTransaction();
                    Product product = new Product(entity.getId(), entity.getQuantity(), entity.getName(), entity.getPrice(), entity.getProducer());
                    session.save(product);
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

    @Override
    public Product update(Product entity) {
        try {
            Hibernater.initialize();
            try (Session session = Hibernater.sessionFactory.openSession()) {
                Transaction tx=null;
                try{
                    tx = session.beginTransaction();
                    Product product = session.load( Product.class, entity.getId() );
                    product.setProducer(entity.getProducer());
                    product.setPrice(entity.getPrice());
                    product.setName(entity.getName());
                    product.setQuantity(entity.getQuantity());
                    session.update(product);
                    tx.commit();

                } catch(RuntimeException ex){
                    if (tx!=null)
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
