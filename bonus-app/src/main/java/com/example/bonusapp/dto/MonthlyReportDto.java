package com.example.bonusapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
@Getter
@Setter
public class MonthlyReportDto {
    private Long customerId;
    private Integer month ;
    private Long totalPoint;

}
