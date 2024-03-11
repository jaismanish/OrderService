package OrderService.entities;

import jakarta.persistence.*;

public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer orderId;
    private Customer customer;
    private Integer restaurantId;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    private Double totalPrice;

    private List<Item> items;
    private Integer deliveryValetId;
}
