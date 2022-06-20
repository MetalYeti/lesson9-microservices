package contract.entities;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class ShoppingCart {
    private List<OrderItem> items;
    private BigDecimal totalCost;

    public ShoppingCart() {
        items = new ArrayList<>();
        totalCost = BigDecimal.ZERO;
    }

    public void add(Product product) {
        OrderItem orderItem = findOrderFromProduct(product);
        if (orderItem == null) {
            orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setItemPrice(product.getPrice());
            orderItem.setQuantity(0L);
            orderItem.setId(0L);
            orderItem.setTotalPrice(BigDecimal.ZERO);
            items.add(orderItem);
        }
        orderItem.setQuantity(orderItem.getQuantity() + 1);
        recalculate();
    }

    public void setQuantity(Product product, Long quantity) {
        OrderItem orderItem = findOrderFromProduct(product);
        if (orderItem == null) {
            return;
        }
        orderItem.setQuantity(quantity);
        recalculate();
    }

    public void remove(Product product) {
        OrderItem orderItem = findOrderFromProduct(product);
        if (orderItem == null) {
            return;
        }
        items.remove(orderItem);
        recalculate();
    }

    private void recalculate() {
        totalCost = BigDecimal.ZERO;
        for (OrderItem o : items) {
            o.setTotalPrice(o.getProduct().getPrice().multiply(BigDecimal.valueOf(o.getQuantity())));
            totalCost = totalCost.add(o.getTotalPrice());
        }
    }

    private OrderItem findOrderFromProduct(Product product) {
        return items.stream().filter(o -> o.getProduct().getId().equals(product.getId())).findFirst().orElse(null);
    }
}
