package am.aca.generactive.model;

public class StockItem extends Item {
    public StockItem() {
    }

    public StockItem(long id, int basePrice, String name) {
        super(id, basePrice, name);
    }

    @Override
    public int calculatePrice(Configuration configuration) {
        return getBasePrice() * configuration.getResolution().getCoefficient();
    }
}
