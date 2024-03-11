package OrderService.services;

import OrderService.entities.Customer;
import OrderService.exception.UserAlreadyExistException;
import OrderService.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.net.UnknownServiceException;

public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Customer register(Customer customer) throws UserAlreadyExistException {
        if(customerRepository.findByUsername(customer.getUsername()).isPresent())
            throw new UserAlreadyExistException();
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        return customerRepository.save(customer);
    }
}
