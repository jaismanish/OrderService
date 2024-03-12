package OrderService.controllers;

import OrderService.entities.Customer;
import OrderService.exception.UserAlreadyExistException;
import OrderService.repositories.CustomerRepository;
import OrderService.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CustomersControllerTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterNewCustomer() throws UserAlreadyExistException {
        Customer newCustomer = new Customer(1, "new_user", "password123", "27th main", new ArrayList<>());

        when(customerRepository.findByUsername("new_user")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(customerRepository.save(newCustomer)).thenReturn(new Customer(1, "new_user", "encodedPassword", "27th main", new ArrayList<>()));

        Customer registeredCustomer = customerService.register(newCustomer);

        assertNotNull(registeredCustomer);
        assertEquals("new_user", registeredCustomer.getUsername());
        assertEquals("encodedPassword", registeredCustomer.getPassword());
        assertEquals("27th main", registeredCustomer.getAddress());
        verify(customerRepository, times(1)).findByUsername("new_user");
        verify(passwordEncoder, times(1)).encode("password123");
        verify(customerRepository, times(1)).save(newCustomer);
    }

    @Test
    public void testRegisterExistingCustomer() {
        Customer existingCustomer = new Customer(1, "existing_user", "password456", "27th main", new ArrayList<>());

        when(customerRepository.findByUsername("existing_user")).thenReturn(Optional.of(existingCustomer));

        assertThrows(UserAlreadyExistException.class, () -> {
            customerService.register(existingCustomer);
        });

        verify(customerRepository, times(1)).findByUsername("existing_user");
        verifyNoMoreInteractions(passwordEncoder, customerRepository);
    }




}