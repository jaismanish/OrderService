package OrderService.controllers;

import OrderService.models.OrderRequest;
import OrderService.models.OrderResponse;
import OrderService.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/orders")
public class OrdersController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> create(@RequestBody OrderRequest ordersRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        OrderResponse response = orderService.create(username, ordersRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
