package com.example.bonusapp.service;

import com.example.bonusapp.dto.MonthlyReportDto;
import com.example.bonusapp.entity.Customer;
import com.example.bonusapp.entity.Transaction;
import com.example.bonusapp.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {
    private TransactionRepository repository;
    private TransactionService transactionService;

    @BeforeEach
    public void setup() {
        repository = mock(TransactionRepository.class);
        transactionService = new TransactionService(repository);
    }

    @Test
    public void createOrSaveTransaction() {
        Transaction transaction = new Transaction();
        when(repository.save(transaction)).thenReturn(transaction);
        transactionService.createOrSaveTransaction(transaction);
        verify(repository, times(1))
                .save(any());
    }

    @Test
    public void getMonthlyTransactionsByCustomerId() {
        Customer customer = new Customer(1L, "Nuru");
        Transaction transaction = new Transaction(1L, LocalDateTime.now(), 120.0);
        transaction.setCustomer(customer);
        when(repository.findAllTransactionsByCustomerId(anyLong()))
                .thenReturn(Collections.singletonList(transaction));
        List<MonthlyReportDto> actual = transactionService.getMonthlyTransactionsByCustomerId(1L);
        assertThat(actual.get(0).getCustomerId()).isEqualTo(transaction.getId());
        assertThat(actual.get(0).getTotalPoint()).isEqualTo(90L);
        verify(repository, times(1)).findAllTransactionsByCustomerId(anyLong());
    }

}
