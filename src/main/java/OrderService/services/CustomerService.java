package OrderService.services;

import OrderService.entities.Customer;
import OrderService.exception.UserAlreadyExistException;

import java.net.UnknownServiceException;

public interface CustomerService {
    Customer register(Customer customerToBeRegistered) throws UserAlreadyExistException;
}
