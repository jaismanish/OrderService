package OrderService.entities;

import OrderService.clients.CatalogServiceClients;
import OrderService.clients.entities.Restaurant;
import OrderService.enums.OrderStatus;
import OrderService.exception.NoDeliveryValetFoundException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import proto.Catalog;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    private final CatalogServiceClients catalogServiceClient = new CatalogServiceClients();

//    public Order(CatalogServiceClients catalogServiceClient) {
//        this.catalogServiceClient = catalogServiceClient;
//    }

    public void create(int restaurantId, Customer customer, List<String> items){
        Catalog.GetMenuItemsResponse menuItemsResponse = catalogServiceClient.getMenuItems(restaurantId);

        List<Catalog.MenuItem> menuItems = menuItemsResponse.getMenuItemsList();

        List<Item> validItems = validateAndFilterItems(menuItems, items);

        double total = calculateTotalPrice(menuItems, validItems);

        this.restaurantId = restaurantId;
        this.items = validItems;
        this.totalPrice = total;
        this.orderStatus = OrderStatus.ACCEPTED;
        this.customer = customer;
    }
    private List<Item> validateAndFilterItems(List<Catalog.MenuItem> menuItems, List<String> itemNames) {
        return itemNames.stream()
                .filter(itemName -> menuItems.stream().anyMatch(menuItem -> menuItem.getName().equals(itemName)))
                .map(itemName -> {
                    Catalog.MenuItem matchingMenuItem = menuItems.stream()
                            .filter(menuItem -> menuItem.getName().equals(itemName))
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("Menu item not found: " + itemName));

                    return new Item(matchingMenuItem.getName());
                })
                .collect(Collectors.toList());
    }
    private double calculateTotalPrice(List<Catalog.MenuItem> menuItems, List<Item> selectedItems) {
        double total = 0.0;

        for (Item selectedItem : selectedItems) {
            Catalog.MenuItem menuItem = menuItems.stream()
                    .filter(item -> {
                        item.getName();
                        return false;
                    })
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Menu item not found: " + selectedItem));

            total += menuItem.getPrice();
        }

        return total;
    }
    public void assignDeliveryValet(Integer restaurantId) throws NoDeliveryValetFoundException {
        throw new NoDeliveryValetFoundException("");
    }
}
