package OrderService.controllers;

import OrderService.entities.Customer;
import OrderService.exception.UserAlreadyExistException;
import OrderService.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomersController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<Customer> register(@RequestBody Customer customer) throws UserAlreadyExistException {
        return new ResponseEntity<>(customerService.register(customer), HttpStatus.CREATED);
    }
}