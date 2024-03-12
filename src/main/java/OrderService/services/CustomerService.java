package OrderService.services;

import OrderService.entities.Customer;
import OrderService.exception.UserAlreadyExistException;

public interface CustomerService {
    Customer register(Customer customerToBeRegistered) throws UserAlreadyExistException;


}
