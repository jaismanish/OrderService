package OrderService.services;

import OrderService.entities.Customer;
import OrderService.entities.Order;
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
    public OrderResponse create(String username, OrderRequest orderRequest) throws Exception {
        Customer customer = customerRepository.findByUsername(username).orElseThrow(()-> new UserNotRegisteredException("User Not Registered"));
        Order order = new Order();
        order.create();
        Order savedOrder = ordersRepository.save(order);

        try {
            savedOrder.assignDeliveryExecutive();
        } catch (Exception e) {
            ordersRepository.delete(savedOrder);
            throw new Exception("No delivery executive found at requested location.");
        }
        Order assignedOrder = ordersRepository.save(savedOrder);
        return new OrderResponse();
    }
}
