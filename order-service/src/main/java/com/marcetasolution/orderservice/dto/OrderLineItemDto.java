package com.marcetasolution.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderLineItemDto {
    private Long id;
    private String skyCode;
    private BigDecimal price;
    private Integer quantity;

}
