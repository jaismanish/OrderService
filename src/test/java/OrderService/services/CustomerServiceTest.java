package OrderService.services;

import OrderService.entities.Customer;
import OrderService.exception.UserAlreadyExistException;
import OrderService.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    void testCustomerRegistrationSuccessfully() throws UserAlreadyExistException {
        Customer customer = new Customer(1, "test", "password", "test", new ArrayList<>());
        Customer expected = new Customer(1, "test", "testPassword", "test", new ArrayList<>());

        when(passwordEncoder.encode("test")).thenReturn("testPassword");
        when(customerRepository.save(customer)).thenReturn(expected);

        Customer actual = customerService.register(customer);

        assertEquals(expected,actual);
        verify(customerRepository,times(1)).save(expected);
        verify(passwordEncoder, times(1)).encode("password");
    }

    @Test
    void testCustomerRegistration_ShouldThrowUserAlreadyExistException() {
        Customer customer = new Customer(1, "test", "test", "test", new ArrayList<>());
        Customer expected = new Customer(1, "test", "password", "test", new ArrayList<>());

        when(passwordEncoder.encode("test")).thenReturn("encoded");
        when(customerRepository.save(customer)).thenReturn(expected);
        when(customerRepository.findByUsername("test")).thenReturn(Optional.of(expected));

        assertThrows(UserAlreadyExistException.class,()-> customerService.register(customer));
    }

}