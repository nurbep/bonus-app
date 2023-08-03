package com.example.bonusapp.controller;

import com.example.bonusapp.dto.MonthlyReportDto;
import com.example.bonusapp.dto.TransactionRequest;
import com.example.bonusapp.entity.Customer;
import com.example.bonusapp.entity.Transaction;
import com.example.bonusapp.exception.CustomerNotFoundException;
import com.example.bonusapp.service.CustomerService;
import com.example.bonusapp.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@Slf4j
public class TransactionController {

    private final TransactionService transactionService;
    private final CustomerService customerService;

    @Autowired
    public TransactionController(final TransactionService transactionService, final CustomerService customerService) {
        this.transactionService = transactionService;
        this.customerService = customerService;
    }

    @PostMapping("/{customerId}/create")
    public ResponseEntity<Transaction> createTransaction(@PathVariable Long customerId, @RequestBody TransactionRequest request) throws Exception {
        log.info("Started createTransaction!");

        Transaction transaction =  new Transaction(request.getId(), request.getDate(), request.getPrice());
        Customer customer = customerService.getCustomerById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer is not found with customerId " + customerId));
        transaction.setCustomer(customer);
        return new ResponseEntity<>(transactionService.createOrSaveTransaction(transaction), HttpStatus.CREATED);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<List<MonthlyReportDto>> getMonthlyTransactionsForCustomer(@PathVariable Long customerId ) {
        log.info("Started getMonthlyTransactionsForCustomer!");
        return new ResponseEntity<>(transactionService.getMonthlyTransactionsByCustomerId(customerId), HttpStatus.OK);
    }

}
