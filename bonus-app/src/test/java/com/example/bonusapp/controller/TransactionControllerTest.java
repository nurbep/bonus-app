package com.example.bonusapp.controller;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.example.bonusapp.dto.MonthlyReportDto;
import com.example.bonusapp.dto.TransactionRequest;
import com.example.bonusapp.entity.Customer;
import com.example.bonusapp.entity.Transaction;
import com.example.bonusapp.service.CustomerService;
import com.example.bonusapp.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private TransactionService transactionService;

    private static ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        mapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void createTransaction() throws Exception {
        Customer customer = new Customer(1L, "Nuru");
        Mockito.when(customerService.getCustomerById(1L)).thenReturn(Optional.of(customer));
        LocalDateTime time = LocalDateTime.now();
        TransactionRequest request = new TransactionRequest(1L, time, 120.0);
        String json = mapper.writeValueAsString(request);
        Transaction transaction = new Transaction(1L, time, 120.0);
        transaction.setCustomer(customer);
        when(transactionService.createOrSaveTransaction(transaction)).thenReturn(transaction);
        mockMvc.perform(post("/transactions/1/create").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8").content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.id").exists());
    }

    @Test
    public void getMonthlyTransactionsForCustomer() throws Exception {
        when(transactionService.getMonthlyTransactionsByCustomerId(1L))
                .thenReturn(Collections.singletonList(new MonthlyReportDto(1L, 2, 170L)));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/transactions/1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }

}
