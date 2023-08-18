package com.example.bonusapp.controller;

import com.example.bonusapp.entity.Customer;
import com.example.bonusapp.service.CustomerService;
import com.example.bonusapp.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class CustomerController {

    private final CustomerService customerService;
    private final TransactionService transactionService;

    @Autowired
    public CustomerController(CustomerService customerService, TransactionService transactionService) {
        this.customerService = customerService;
        this.transactionService = transactionService;
    }

    @PostMapping("/create")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        log.info("Started createCustomer!");
        return new ResponseEntity<>(customerService.createOrSaveCustomer(customer), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<String> hi() {
        return new ResponseEntity<String>("I am Nuruzzaman", HttpStatus.OK);
    }
}
