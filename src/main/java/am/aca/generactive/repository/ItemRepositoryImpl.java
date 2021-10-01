package am.aca.generactive.repository;

import am.aca.generactive.model.Group;
import am.aca.generactive.model.Item;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class ItemRepositoryImpl {

    @PersistenceContext
    private EntityManager entityManager;

    public void attachItemToGroup(long itemId, long groupId) {
        Item item = entityManager.find(Item.class, itemId);
        Group group = entityManager.find(Group.class, groupId);

        item.setName("Manually updated");

        group.addItem(item);
    }

    
    public Item insert(Item item) {
        if (item.getItemDetail() != null) {
            item.getItemDetail().setItem(item);
        }

        entityManager.persist(item);

        return item;
    }

    
    public Item update(Item item) {
        Item existing = entityManager.find(Item.class, item.getId());
        existing.setName(item.getName());
        existing.setBasePrice(item.getBasePrice());

        return existing;
    }

    
    public Optional<Item> findById(Long id) {
        // Hibernate query to get item by id
        // equivalent sql query: select * from item where id = ?;
        // equivalent JPQL query: select i from Item i where id = :id;
        // Item is the name of the Entity defined by @Entity annotation
        // Query will be executed right at this point
        Item item = entityManager.find(Item.class, id);

        return Optional.ofNullable(item);
    }

    
    public List<? extends Item> getAllItems() {
        // HQL query to get all the items
        // equivalent sql query: select * from item;
        // equivalent JPQL query: select i from Item i;
        // Item is the name of the Entity defined by @Entity annotation
        String q = "from Item";
        // Create query (query will not be executed at this point)
        List<? extends Item> items = entityManager.createQuery(q, Item.class)
                .getResultList();

        return items;
    }

    
    public boolean deleteById(Long id) {
        // HQL query to get all the items
        // equivalent sql query: delete from item where id = ?;
        // Item is the name of the Entity defined by @Entity annotation
        String q = "delete from Item i" +
                " where i.id = :id";

        // Create query (query will not be executed at this point)
        int deleted = entityManager.createQuery(q)
                .setParameter("id", id) // Set named parameter
                .executeUpdate(); // Execute the query

        return deleted != 0;
    }

    
    public boolean delete(Item item) {
        entityManager.remove(item);

        return true;
    }

    
    public List<Item> getEverything() {
        return null;
    }

    public List<Item> findItems(Predicate<Item> searchPredicate) {
        return new ArrayList<>();
    }
}
