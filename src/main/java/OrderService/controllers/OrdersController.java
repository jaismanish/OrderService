package OrderService.controllers;

import OrderService.enums.OrderStatus;
import OrderService.exception.OrderNotFoundException;
import OrderService.exception.UserNotRegisteredException;
import OrderService.models.OrderRequest;
import OrderService.models.OrderResponse;
import OrderService.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/orders")
public class OrdersController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> create(@RequestBody OrderRequest ordersRequest) throws Exception {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        OrderResponse response = orderService.create(username, ordersRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{order_id}")
    public ResponseEntity<OrderResponse> fetch(@PathVariable("order_id") int orderId) throws UserNotRegisteredException, OrderNotFoundException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        OrderResponse response = orderService.fetch(username, orderId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{order_id}")
    public ResponseEntity<OrderResponse> update(@PathVariable("order_id") int orderId, @RequestParam OrderStatus status) throws OrderNotFoundException {
        OrderResponse response = orderService.update(orderId, status);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
