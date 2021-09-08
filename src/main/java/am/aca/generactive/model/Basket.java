package am.aca.generactive.model;

import java.util.ArrayList;
import java.util.List;

public class Basket {
    private final List<BasketItem> basketItems = new ArrayList<>();

    public void add(BasketItem basketItem) {
        basketItems.add(basketItem);
    }

    public boolean remove(BasketItem basketItem) {
        return basketItems.remove(basketItem);
    }

    public void print() {
        System.out.println("Basket ----- ");
        for(BasketItem basketItem : basketItems) {
            basketItem.print();
        }

        System.out.printf("Total price: %d $", getTotalPrice());
    }

    public int getTotalPrice() {
        int sum = 0;

        for(BasketItem basketItem : basketItems) {
            sum += basketItem.getPrice();
        }

        return sum;
    }
}
