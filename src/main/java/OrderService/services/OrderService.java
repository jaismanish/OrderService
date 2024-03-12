package OrderService.services;

import OrderService.exception.OrderNotFoundException;
import OrderService.exception.UserNotRegisteredException;
import OrderService.models.OrderRequest;
import OrderService.models.OrderResponse;

public interface OrderService {
    OrderResponse create(String username, OrderRequest orderRequest) throws Exception;
    OrderResponse fetch(String username, int orderId) throws UserNotRegisteredException, OrderNotFoundException;

}
