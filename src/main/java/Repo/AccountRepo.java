package Repo;

import Domain.Account;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class AccountRepo implements AccountRepoInterface{
    @Override
    public Account getOne(String id) {
//        logger.traceEntry();
        Account account = null;
        try{
            Hibernater.initialize();
            try(Session session = Hibernater.sessionFactory.openSession()) {
                Transaction tx = null;
                try {
                    tx = session.beginTransaction();
                    account =
                            session.createQuery("from Account where id like '"+id+"'", Account.class).getSingleResult();

                    System.out.println("account found:"+account);
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
        return account;
    }

    @Override
    public Account add(Account entity) {
        //        logger.traceEntry("saving task {}",entity);
        try {
            Hibernater.initialize();
            try (Session session = Hibernater.sessionFactory.openSession()) {
                Transaction tx = null;
                try {
                    tx = session.beginTransaction();
                    Account account = new Account(entity.getId(), entity.getPassword(), entity.getUserType());
                    session.save(account);
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
