package am.aca.generactive.repository;

import am.aca.generactive.model.Basket;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Component
public class BasketRepository {


    @PersistenceContext
    private EntityManager entityManager;

    private final SessionFactory sessionFactory;

    public BasketRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Optional<Basket> getBasket(Long basketId) {
        Basket basket = entityManager
                .createQuery("select b from Basket b" +
                        " where b.id = 1", Basket.class)
                .getSingleResult();

        return Optional.ofNullable(basket);
    }
}
