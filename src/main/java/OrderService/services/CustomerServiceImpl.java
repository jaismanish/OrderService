package OrderService.services;

import OrderService.entities.Customer;
import OrderService.exception.UserAlreadyExistException;
import OrderService.repositories.CustomerRepository;
import OrderService.repositories.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Customer register(Customer customer) throws UserAlreadyExistException {
        if(customerRepository.findByUsername(customer.getUsername()).isPresent())
            throw new UserAlreadyExistException("User Already Exist");
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        return customerRepository.save(customer);
    }


}
