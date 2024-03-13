package OrderService.services;

import OrderService.entities.Customer;
import OrderService.entities.Item;
import OrderService.entities.Order;
import OrderService.enums.OrderStatus;
import OrderService.exception.NoDeliveryValetFoundException;
import OrderService.exception.UserNotRegisteredException;
import OrderService.models.OrderRequest;
import OrderService.models.OrderResponse;
import OrderService.repositories.CustomerRepository;
import OrderService.repositories.OrdersRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrderServiceTest {
    @Mock
    private OrdersRepository ordersRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @Mock
    private Order orderMock;

    @InjectMocks
    private OrderServiceImpl ordersService;

    @Test
    void testCreateOrderSuccessfully() throws NoDeliveryValetFoundException {
        Customer customer = new Customer(1, "test", "test", "test", new ArrayList<>());
        List<Item> items = new ArrayList<>(List.of(new Item(1,1, "wings", 180.0), new Item(2,2, "rings", 180.0)));
        Order order = new Order(1,  customer,1,OrderStatus.ACCEPTED,360.0,  items,1);
        OrderRequest request = new OrderRequest(1, List.of("wings", "rings"));
        when(customerRepository.findByUsername("test")).thenReturn(Optional.of(customer));
        when(authentication.getName()).thenReturn("test");
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(ordersRepository.save(any())).thenReturn(order);
        OrderResponse expected = new OrderResponse(1,1,360.0,"test", items,OrderStatus.ACCEPTED,1);

        OrderResponse actual = ordersService.create("test", request);

        assertEquals(expected,actual);
        verify(customerRepository, times(1)).findByUsername("test");
        verify(ordersRepository, times(1)).save(any());
    }

}