package am.aca.generactive.repository;

import am.aca.generactive.model.Basket;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BasketRepository {

    private final SessionFactory sessionFactory;

    public BasketRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Optional<Basket> getBasket(Long basketId) {
        Session session = sessionFactory.getCurrentSession();
//        Transaction transaction = session.beginTransaction();

        Basket basket = session.get(Basket.class, 1L);

//        transaction.commit();
//        session.close();

        return Optional.ofNullable(basket);
    }
}
