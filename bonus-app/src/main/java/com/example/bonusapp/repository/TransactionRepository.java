package com.example.bonusapp.repository;

import com.example.bonusapp.entity.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    @Query(
            value = "SELECT max(id) as id, date, price,customer_id, sum(point) as point FROM transaction WHERE customer_id = ?1 GROUP BY YEAR(date), month(date)",
            nativeQuery = true)
    List<Transaction> findAllTransactionsByCustomerId(Long customerId);

}
