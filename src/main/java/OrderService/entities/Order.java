package OrderService.entities;

import OrderService.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer orderId;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    private Integer restaurantId;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    private Double totalPrice;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Item> items;
    private Integer deliveryValetId;

    public void create(int restaurantId, Customer customer, List<String> items){
        Restaurant restaurant = new CatalogServiceClient().fetchRestaurantFromCatalogService(restaurantId);
        List<String> restaurantItems = restaurant.getMenu().getItems().stream().map((Item::getItemName)).toList();
        List<Item> itemsToOrder = new ArrayList<>();
        for(String item : items){
            if(!restaurantItems.contains(item))
                throw new ItemNotInRestaurantException(ITEM_NOT_IN_RESTAURANT + item);
            itemsToOrder.add(restaurant.getMenu().getItems().get(restaurantItems.indexOf(item)));
        }
        double total_price = itemsToOrder.stream().mapToDouble(Item::getPrice).sum();
        this.restaurantId = restaurantId;
        this.items = itemsToOrder;
        this.totalPrice = total_price;
        this.orderStatus = OrderStatus.ACCEPTED;
        this.customer = customer;
    }
    public void assignDeliveryValet(Integer restaurantId){}
}
