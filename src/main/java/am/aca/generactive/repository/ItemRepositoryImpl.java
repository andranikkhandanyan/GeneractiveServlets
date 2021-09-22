package am.aca.generactive.repository;

import am.aca.generactive.config.ApplicationContainer;
import am.aca.generactive.model.Group;
import am.aca.generactive.model.Item;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Component
public class ItemRepositoryImpl implements ItemRepository {

    private SessionFactory sessionFactory;

    public ItemRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void attachItemToGroup(long itemId, long groupId) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Item item = session.get(Item.class, itemId);
        Group group = session.get(Group.class, groupId);

        item.setName("Manually updated");

        group.addItem(item);

        transaction.commit();
        session.close();
    }

    @Override
    public Item insert(Item item) {
        Session session = sessionFactory.getCurrentSession();
//        Transaction transaction = session.beginTransaction();

        if (item.getItemDetail() != null) {
            item.getItemDetail().setItem(item);
        }
        session.save(item);

//        transaction.commit();

//        session.close();

        return item;
    }

    @Override
    public Item update(Item item) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Item existing = session.get(Item.class, item.getId());
        existing.setName(item.getName());
        existing.setBasePrice(item.getBasePrice());

        transaction.commit();

        session.close();

        return existing;
    }

    @Override
    public Optional<Item> findById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        // Hibernate query to get item by id
        // equivalent sql query: select * from item where id = ?;
        // equivalent JPQL query: select i from Item i where id = :id;
        // Item is the name of the Entity defined by @Entity annotation
        // Query will be executed right at this point
        Item item = session.get(Item.class, id);

        transaction.commit();
        session.close();

        return Optional.ofNullable(item);
    }

    @Override
    public List<? extends Item> getAllItems() {
        Session session = sessionFactory.getCurrentSession();

        Transaction transaction = session.beginTransaction();

        // HQL query to get all the items
        // equivalent sql query: select * from item;
        // equivalent JPQL query: select i from Item i;
        // Item is the name of the Entity defined by @Entity annotation
        String q = "from Item i";
        // Create query (query will not be executed at this point)
        Query<Item> query = session.createQuery(q, Item.class);
        // Execute the query and get list of results
        List<? extends Item> items = query.getResultList();

        transaction.commit();
        session.close();

        return items;
    }

    @Override
    public boolean deleteById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        // HQL query to get all the items
        // equivalent sql query: delete from item where id = ?;
        // Item is the name of the Entity defined by @Entity annotation
        String q = "delete from Item i" +
                " where i.id = :id";

        // Create query (query will not be executed at this point)
        Query query = session.createQuery(q);
        // Set named parameter
        query.setParameter("id", id);
        // Execute the query
        int deleted = query.executeUpdate();

        transaction.commit();
        session.close();

        return deleted != 0;
    }

    @Override
    public boolean delete(Item item) {
        Session session = sessionFactory.getCurrentSession();

        session.remove(item);

        session.close();

        return true;
    }

    public List<Item> findItems(Predicate<Item> searchPredicate) {
        return new ArrayList<>();
    }
}
