package com.example.bonusapp.service;

import com.example.bonusapp.dto.MonthlyReportDto;
import com.example.bonusapp.entity.Transaction;
import com.example.bonusapp.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(final TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction createOrSaveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public List<MonthlyReportDto> getMonthlyTransactionsByCustomerId(Long customerId) {
        List<Transaction> transactions = transactionRepository.findAllTransactionsByCustomerId(customerId);
        List<MonthlyReportDto> monthlyReportDtos = new ArrayList<>();

        if (!transactions.isEmpty()) {
            monthlyReportDtos  = transactions.stream().map(t -> new MonthlyReportDto(t.getCustomer().getId(),
                    t.getDate().getMonthValue(), t.getPoint())).collect(Collectors.toList());
        }

         return monthlyReportDtos;
    }
}
