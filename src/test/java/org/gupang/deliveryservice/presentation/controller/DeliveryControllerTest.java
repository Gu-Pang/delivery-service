package org.gupang.deliveryservice.presentation.controller;

import org.gupang.deliveryservice.application.service.DeliveryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DeliveryController.class)
class DeliveryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DeliveryService deliveryService;

    @Test
    void deliveryCreateTest() throws Exception {

        String json = """
        {
            "orderId": "11111111-1111-1111-1111-111111111111",
            "supplierId": "22222222-2222-2222-2222-222222222222",
            "receiverId": "33333333-3333-3333-3333-333333333333",
            "address": "서울",
            "addressDetail": "상세",
            "recipientName": "홍길동"
        }
        """;

        mockMvc.perform(post("/api/v1/deliveries/test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                        .andDo(print())
                .andExpect(status().isOk());

//        verify(deliveryService).createDelivery(any());
    }
}