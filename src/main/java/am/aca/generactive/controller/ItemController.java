package am.aca.generactive.controller;

import am.aca.generactive.service.ItemService;
import am.aca.generactive.service.dto.ItemDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public List<? extends ItemDTO> getAll() {
        return itemService.getAll();
    }

    @GetMapping("/search")
    public List<? extends ItemDTO> search(@RequestParam String name) {
        return itemService.find(name);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDTO> getItem(@PathVariable Long id) {
        return ResponseEntity.of(itemService.getItem(id));
    }

    @PostMapping
    public @ResponseBody ItemDTO create(@RequestBody @Valid ItemDTO itemDTO) {
        return itemService.create(itemDTO);
    }
}
