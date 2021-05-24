package Repo;

import Domain.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OrderRepo implements OrderRepoInterface{
    @Override
    public Iterable<Product> getMostFrqOrderedProducts(Long clientID) {
        //        logger.traceEntry();
        List<Product> products = null;
        try{
            Hibernater.initialize();
            try(Session session = Hibernater.sessionFactory.openSession()) {
                Transaction tx = null;
                try {
                    tx = session.beginTransaction();
                    products = session.createQuery("select NEW Product(P.id, SUM(OP.quantity), P.name, P.price, P.producer)" +
                            " from Order O inner join OrderProduct OP on O.id=OP.orderId\n" +
                            "inner join Product P on OP.productId=P.id where O.customerID= " + clientID.toString() +
                            " group by P.id", Product.class).list();
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
    public Long getNumberOfOrders(Long clientID) {
        //        logger.traceEntry();
        Long number = 0L;
        try{
            Hibernater.initialize();
            try(Session session = Hibernater.sessionFactory.openSession()) {
                Transaction tx = null;
                try {
                    tx = session.beginTransaction();
                    number = session.createQuery("from Order where customerID="+clientID).stream().count();
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
        return number;
    }

    @Override
    public Iterable<SalesmanDTO> getTopSalesman() {
        //        logger.traceEntry();
        List<SalesmanDTO> salesmanDTOS = null;
        try{
            Hibernater.initialize();
            try(Session session = Hibernater.sessionFactory.openSession()) {
                Transaction tx = null;
                try {
                    tx = session.beginTransaction();
                    salesmanDTOS = session.createQuery("select new Domain.SalesmanDTO(O.salesmanID, SUM(P.price*OP.quantity))" +
                            " from Order O inner join OrderProduct OP on O.id=OP.orderId\n" +
                            "    inner join Product P on OP.productId=P.id\n" +
                            "    group by salesmanID", SalesmanDTO.class).
//                            .setMaxResults(5).
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
        return salesmanDTOS;
    }

    @Override
    public Iterable<ClientDTO> getTopClients() {
        //        logger.traceEntry();
        List<ClientDTO> clientDTOS = null;
        try{
            Hibernater.initialize();
            try(Session session = Hibernater.sessionFactory.openSession()) {
                Transaction tx = null;
                try {
                    tx = session.beginTransaction();
                    clientDTOS = session.createQuery("select new Domain.ClientDTO(O.customerID, SUM(P.price*OP.quantity))" +
                            " from Order O inner join OrderProduct OP on O.id=OP.orderId\n" +
                            " inner join Product P on OP.productId=P.id\n" +
                            "group by customerID\n order by SUM(P.price*OP.quantity)", ClientDTO.class).
//                            .setMaxResults(5).
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
        return clientDTOS;
    }

    /* select for total price of order by salesman
        select salesmanID, SUM(P.price*OP.quantity) as totalPrice from Orders O inner join OrderProduct OP on O.id=OP.orderId
        inner join Product P on OP.productId=P.id;
        */
    @Override
    public Order getOne(Long id) {
        //        logger.traceEntry();
        Order order = null;
        try{
            Hibernater.initialize();
            try(Session session = Hibernater.sessionFactory.openSession()) {
                Transaction tx = null;
                try {
                    tx = session.beginTransaction();
                    order =
                            session.createQuery("from Order where id="+id, Order.class).getSingleResult();

                    System.out.println("order found:"+order);
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
        return order;
    }

    @Override
    public Iterable<Order> getAll() {
        //        logger.traceEntry();
        List<Order> orders = null;
        try{
            Hibernater.initialize();
            try(Session session = Hibernater.sessionFactory.openSession()) {
                Transaction tx = null;
                try {
                    tx = session.beginTransaction();
                    orders = session.createQuery("from Order", Order.class).
//                            setFirstResult(10).setMaxResults(5).
        list();
                    //fill products list
                    for(Order order : orders){
                        List<Product> products = session.createQuery(
                                "select P.id, OP.quantity, P.name, P.price, P.producer from Order O " +
                                        "inner join OrderProduct OP on O.id=OP.orderId " +
                                "inner join Product P on OP.productId=P.id where O.id="+order.getId(), Product.class).list();
                        order.setProducts(new HashSet<Product>(products));
                    }

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
        return orders;
    }

    @Override
    public Order add(Order entity) {
        //        logger.traceEntry("saving task {}",entity);
        try {
            Hibernater.initialize();
            try (Session session = Hibernater.sessionFactory.openSession()) {
                Transaction tx = null;
                try {
                    tx = session.beginTransaction();
                    Order order = new Order(entity.getId(), entity.getProducts(), entity.getDate(), entity.getStatus(), entity.getCustomerID(), entity.getSalesmanID());


                    Long id = (Long) session.save(order);
                    //also save in the intermediary table
                    for(Product product : (Set<Product>)entity.getProducts()){
                        OrderProduct orderProduct = new OrderProduct(id, product.getId(), product.getQuantity());
                        session.save(orderProduct);
                    }
                    tx.commit();
                } catch (RuntimeException ex) {
                    if (tx != null)
                        tx.rollback();
                }
            }
        } catch (Exception ex) {
//            logger.error(ex);
            System.err.println("Error DB" + ex);
        } finally {
            Hibernater.close();
//            logger.traceExit();
        }
        return entity;
    }

    @Override
    public Order delete(Long id) {
        //        logger.traceEntry("deleting task {}",entity);
        try {
            Hibernater.initialize();
            try (Session session = Hibernater.sessionFactory.openSession()) {
                Transaction tx = null;
                try {
                    tx = session.beginTransaction();
                    session.createQuery("delete from Order where id = :id").setParameter("id", id).executeUpdate();
                    session.createQuery("delete from OrderProduct where orderId= :id").setParameter("id", id).executeUpdate();
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
    public Order update(Order entity) {
        return null;
    }

    @Override
    public Iterable<Order> getAllBySalesman(String id) {
        //        logger.traceEntry();
        List<Order> orders = new ArrayList<>();
        try{
            Hibernater.initialize();
            try(Session session = Hibernater.sessionFactory.openSession()) {
                Transaction tx = null;
                try {
                    tx = session.beginTransaction();
                    orders = session.createQuery("from Order where salesmanID='"+id+"'", Order.class).
//                            setFirstResult(10).setMaxResults(5).
        list();
                    //fill products list
                    for(Order order : orders){
                        List<Product> products = session.createQuery(
                                "select NEW Product(P.id, OP.quantity, P.name, P.price, P.producer) from Order O " +
                                        "inner join OrderProduct OP on O.id=OP.orderId " +
                                        "inner join Product P on OP.productId=P.id where O.id="+order.getId(), Product.class).list();
                        order.setProducts(new HashSet<Product>(products));
                    }

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
        return orders;
    }
}
