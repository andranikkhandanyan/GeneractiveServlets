package am.aca.generactive.service.dto;

import am.aca.generactive.model.Item;
import am.aca.generactive.model.ItemDetails;

import java.util.Objects;

public class ItemDetailsDTO {

    private Long id;
    private String description;

    public static ItemDetailsDTO mapToDTO(ItemDetails entity) {
        if (entity == null) {
            return null;
        }

        ItemDetailsDTO dto = new ItemDetailsDTO();
        dto.setId(entity.getId());
        dto.setDescription(entity.getDescription());

        return dto;
    }

    public static ItemDetails mapToEntity(ItemDetailsDTO itemDetails) {
        return null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemDetailsDTO that = (ItemDetailsDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ItemDetailsDTO{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
