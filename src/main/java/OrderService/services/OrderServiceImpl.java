package OrderService.services;

import OrderService.entities.Customer;
import OrderService.entities.Item;
import OrderService.entities.Order;
import OrderService.enums.OrderStatus;
import OrderService.exception.NoDeliveryValetFoundException;
import OrderService.exception.UserNotRegisteredException;
import OrderService.models.OrderRequest;
import OrderService.models.OrderResponse;
import OrderService.repositories.CustomerRepository;
import OrderService.repositories.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Override
    public OrderResponse create(String username, OrderRequest orderRequest) throws Exception {
        Customer customer = customerRepository.findByUsername(username).orElseThrow(()-> new UserNotRegisteredException("User Not Registered"));
        Order order = new Order();
        order.create(orderRequest.getRestaurantId(), customer, orderRequest.getItems());
        Order savedOrder = ordersRepository.save(order);

        try {
            savedOrder.assignDeliveryValet(orderRequest.getRestaurantId());
        } catch (NoDeliveryValetFoundException e) {
            ordersRepository.delete(savedOrder);
            throw new Exception("No Delivery Valet Found Nearby");
        }
        Order assignedOrder = ordersRepository.save(savedOrder);
        return new OrderResponse(assignedOrder.getOrderId(),
                assignedOrder.getRestaurantId(),
                assignedOrder.getTotalPrice(),
                username,
                assignedOrder.getItems(),
                assignedOrder.getOrderStatus(),
                assignedOrder.getDeliveryValetId()
        );
    }
}
