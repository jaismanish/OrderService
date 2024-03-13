package OrderService.controllers;

import OrderService.entities.Item;
import OrderService.enums.OrderStatus;
import OrderService.models.OrderRequest;
import OrderService.models.OrderResponse;
import OrderService.services.OrderServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
@SpringBootTest
@AutoConfigureMockMvc
class OrdersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderServiceImpl ordersService;

    @BeforeEach
    void setUp() {
        reset(ordersService);
    }
    @Test
    @WithMockUser(username = "testUser")
    void testCreateOrderSuccessfully() throws Exception {
        OrderRequest request = new OrderRequest(1, List.of("Fries","Curry"));
        List<Item> items = new ArrayList<>(List.of(new Item(1,1, "wings", 180.0), new Item(2,2, "rings", 180.0)));
        OrderResponse response = new OrderResponse(1,1, 360.0,"testUser",items,OrderStatus.ACCEPTED,1);
        when(ordersService.create("testUser", request)).thenReturn(response);

        mockMvc.perform(post("/api/v1/orders").
                        contentType(MediaType.APPLICATION_JSON).
                        content(objectMapper.writeValueAsString(request))).
                andExpect(status().isCreated()).
                andExpect(jsonPath("$.orderId").value(1)).
                andExpect(jsonPath("$.customer").value("testUser"));
        verify(ordersService, times(1)).create("testUser", request);
    }

    @Test
    @WithMockUser(username = "testUser")
    void testFetchOrderSuccessfully() throws Exception {
        OrderRequest request = new OrderRequest(1, List.of("wings","rings"));
        List<Item> items = new ArrayList<>(List.of(new Item(1,1, "wings", 180.0), new Item(2,2, "rings", 180.0)));
        OrderResponse response = new OrderResponse(1,1, 360.0,"testUser",items,OrderStatus.ACCEPTED,1);
        when(ordersService.fetch("testUser", 1)).thenReturn(response);

        mockMvc.perform(get("/api/v1/orders/1").
                        contentType(MediaType.APPLICATION_JSON).
                        content(objectMapper.writeValueAsString(request))).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.orderId").value(1)).
                andExpect(jsonPath("$.customer").value("testUser"));
        verify(ordersService, times(1)).fetch("testUser", 1);
    }

}