package am.aca.generactive.service;

import am.aca.generactive.model.Item;
import am.aca.generactive.service.dto.ItemDTO;

import java.util.List;
import java.util.Optional;

public interface ItemService {
    ItemDTO create(ItemDTO item);
    Item update(Item item);
    boolean delete(Long id);
    Optional<ItemDTO> getItem(Long id);
    List<? extends ItemDTO> getAll();
    List<? extends ItemDTO> find(String name);
}
