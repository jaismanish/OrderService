package OrderService.services;

import OrderService.models.OrderRequest;
import OrderService.models.OrderResponse;

public interface OrderService {
    OrderResponse create(String username, OrderRequest orderRequest);

}
