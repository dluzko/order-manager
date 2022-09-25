package com.luzko.order.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class OrderResponseDto {
    private Long orderId;
    private Long clientId;
    private String productCode;
    private BigDecimal quantity;
    private String address;
    private String comment;
}
