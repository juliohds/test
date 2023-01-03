package com.sigma.software.test.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TransactionsApiTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testPostTransactions() throws Exception {
        // Arrange
        String validTransactionJson = "{\"amount\": \"12.3343\", \"timestamp\": \"" + Instant.now() + "\"}";
        String invalidTransactionJson = "{\"amount\": \"invalid\", \"timestamp\": \"2018-07-17T09:59:51.312Z\"}";
        String oldTransactionJson = "{\"amount\": \"12.3343\", \"timestamp\": \"2010-07-17T09:59:51.312Z\"}";
        String futureTransactionJson = "{\"amount\": \"12.3343\", \"timestamp\": \"2050-07-17T09:59:51.312Z\"}";

        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validTransactionJson))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(oldTransactionJson))
                .andExpect(status().isNoContent());

        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidTransactionJson))
                .andExpect(status().isUnprocessableEntity());

        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(futureTransactionJson))
                .andExpect(status().isUnprocessableEntity());
    }
}

