package repository;

import domain.entities.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class UserRepository {

    private final SessionFactory sessionFactory;

    public UserRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public User findByUsernameAndPassword(String username, String password) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            User user =  session.createQuery("from User user where user.username=?1 and user.password=?2", User.class)
                    .setParameter(1, username)
                    .setParameter(2, password)
                    .setMaxResults(1)
                    .uniqueResult();
            session.getTransaction().commit();
            return user;
        }
        catch (HibernateException e) {
            e.printStackTrace();
        }
        return null;
    }
}