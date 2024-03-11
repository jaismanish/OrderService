package OrderService.models;

import OrderService.entities.Item;
import OrderService.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private Integer orderId;
    private Integer restaurantId;
    private Double totalPrice;
    private String customer;
    private List<Item> items;
    private OrderStatus orderStatus;
    private Integer deliveryValetId;
}
