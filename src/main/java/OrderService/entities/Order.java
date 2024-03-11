package OrderService.entities;

import OrderService.OrderStatus;
import jakarta.persistence.*;

import java.util.List;

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
}
