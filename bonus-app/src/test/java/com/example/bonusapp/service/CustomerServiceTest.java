package com.example.bonusapp.service;

import com.example.bonusapp.entity.Customer;
import com.example.bonusapp.exception.CustomerNotFoundException;
import com.example.bonusapp.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
    private CustomerRepository repository;
    private CustomerService customerServiceImpl;

    @BeforeEach
    public void setup() {
        repository = mock(CustomerRepository.class);
        customerServiceImpl = new CustomerService(repository);
    }

    @Test
    public void getCustomerById() {
        Customer customer = new Customer(1L, "Nuru");
        when(repository.findById(anyLong())).thenReturn(Optional.of(customer));
        Customer actual = customerServiceImpl.getCustomerById(1L).get();
        assertThat(actual.getId()).isEqualTo(customer.getId());
        assertThat(actual.getName()).isEqualTo(customer.getName());
        verify(repository, times(1))
                .findById(anyLong());
    }

    @Test
    public void getCustomerById_throws_exception() {
        when(repository.findById(anyLong())).thenThrow(CustomerNotFoundException.class);
        assertThrows(CustomerNotFoundException.class, () -> customerServiceImpl.getCustomerById(1L));
    }
}
