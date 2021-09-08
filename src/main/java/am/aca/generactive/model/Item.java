package am.aca.generactive.model;

import java.util.Objects;

public abstract class Item {
    private long id;
    private int basePrice;
    private String name;
    private String imageUrl;
    private Group group;

    public Item() {
    }

    public Item(long id, int basePrice, String name) {
        this.id = id;
        this.basePrice = basePrice;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(int basePrice) {
        this.basePrice = basePrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    void setGroup(Group group) {
        this.group = group;
    }

    public abstract int calculatePrice(Configuration configuration);

    public void print() {
        System.out.printf("ITEM(%s) - id: {%d} {%s} {%d}%n",
                this.getClass().getSimpleName(), id, name, basePrice);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id == item.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
