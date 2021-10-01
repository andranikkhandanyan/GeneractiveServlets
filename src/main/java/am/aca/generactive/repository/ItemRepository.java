package am.aca.generactive.repository;

import am.aca.generactive.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long>, JpaSpecificationExecutor<Item> {

    @Query("select i from Item i join fetch i.group" +
            " where lower(i.name) = lower(:name)" +
            " and i.basePrice > :priceFrom")
    List<Item> find(@Param("name") String name, @Param("priceFrom") Integer priceFrom);

    @Query("select i.name from Item i")
    List<String> getAllNames();
}
