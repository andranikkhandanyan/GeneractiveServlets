package am.aca.generactive.repository;

import am.aca.generactive.model.Item;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {
    List<? extends Item> getAllItems();
    Optional<Item> findById(Long id);
    Item insert(Item item);
    Item update(Item item);
    boolean deleteById(Long id);
    boolean delete(Item item);
}
