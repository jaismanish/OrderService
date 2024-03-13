package OrderService.services;

import OrderService.entities.Customer;
import OrderService.entities.Order;
import OrderService.enums.OrderStatus;
import OrderService.exception.NoDeliveryValetFoundException;
import OrderService.exception.OrderNotFoundException;
import OrderService.exception.UserNotRegisteredException;
import OrderService.models.OrderRequest;
import OrderService.models.OrderResponse;
import OrderService.repositories.CustomerRepository;
import OrderService.repositories.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Override
    public OrderResponse create(String username, OrderRequest orderRequest) throws NoDeliveryValetFoundException {
        Customer customer = customerRepository.findByUsername(username).orElseThrow(()-> new UserNotRegisteredException("User Not Registered"));
        Order order = new Order();
        order.create(orderRequest.getRestaurantId(), customer, orderRequest.getItems());
        Order savedOrder = ordersRepository.save(order);

        try {
            savedOrder.assignDeliveryValet(orderRequest.getRestaurantId());
        } catch (NoDeliveryValetFoundException e) {
            ordersRepository.delete(savedOrder);
            throw new NoDeliveryValetFoundException("No Delivery Valet Found Nearby");
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

    @Override
    public OrderResponse fetch(String username, int orderId) throws UserNotRegisteredException, OrderNotFoundException {
        Customer customer = customerRepository.findByUsername(username).orElseThrow(()-> new UserNotRegisteredException("User Not Registered"));
        Order order = ordersRepository.findById(orderId).orElseThrow(()-> new OrderNotFoundException("Order Not Found"));

        if(!customer.getOrders().contains(order))
            throw new OrderNotFoundException("Order Id Not Found");
        return new OrderResponse(order.getOrderId(),
                order.getRestaurantId(),
                order.getTotalPrice(),
                username,
                order.getItems(),
                order.getOrderStatus(),
                order.getDeliveryValetId());
    }

    @Override
    public OrderResponse update(int orderId, OrderStatus status) throws OrderNotFoundException {
        Order order = ordersRepository.findById(orderId).orElseThrow(()-> new OrderNotFoundException("Order Not Found"));
        order.setOrderStatus(status);

        Order updatedOrder = ordersRepository.save(order);
        return new OrderResponse(updatedOrder.getOrderId(),
                updatedOrder.getRestaurantId(),
                updatedOrder.getTotalPrice(),
                updatedOrder.getCustomer().getUsername(),
                updatedOrder.getItems(),
                updatedOrder.getOrderStatus(),
                updatedOrder.getDeliveryValetId());
    }
}
