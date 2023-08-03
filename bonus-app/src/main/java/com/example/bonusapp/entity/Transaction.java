package com.example.bonusapp.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Setter
@Getter
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime date;
    private double price; // In real project, we should use BigDecimal for price
    private Long point;
    @ManyToOne
    @JoinColumn(name ="customer_id")
    private Customer customer;

    public Transaction(Long id, LocalDateTime date, double price) {
        this.id = id;
        this.date = date;
        this.price = price;
        this.point = getPoint(price);
    }

    private Long getPoint(double price) { // In real project, we should use BigDecimal for Price
        Long points = 0L;

       if (price > 100) {
           points = (long) ((price - 100) * 2 + 50 * 1);
       }
        return points;
    }

}
