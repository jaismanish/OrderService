package OrderService.services;

import OrderService.entities.Customer;

public interface CustomerService {
    Customer register(Customer customerToBeRegistered);
}
