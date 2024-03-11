package OrderService.entities;

import OrderService.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    public void create(){

    }
    public void assignDeliveryValet(){}
}
