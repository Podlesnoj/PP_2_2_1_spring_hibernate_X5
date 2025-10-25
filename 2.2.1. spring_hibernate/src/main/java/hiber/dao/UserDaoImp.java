package hiber.dao;

import hiber.model.User;
import java.util.Optional;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {
    @Autowired
    private SessionFactory sessionFactory;



    @Override
    public void add(User user) {
        if (user.getCar() != null) {
            sessionFactory.getCurrentSession().save(user.getCar());
        }
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession()
                .createQuery("from User u join fetch u.car", User.class);
        return query.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Optional<User> getUserByCar(String model, int series) {
        try {
            TypedQuery<User> query = sessionFactory.getCurrentSession()
                    .createQuery("from User u join fetch u.car where u.car.model = :model and u.car.series = :series", User.class);
            query.setParameter("model", model);
            query.setParameter("series", series);
            return Optional.ofNullable(query.setMaxResults(1).getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
