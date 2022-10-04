package com.luzko.order.dto;

import com.luzko.order.model.OrderStatus;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Enumerated;
import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class OrderResponseDto {
    private Long orderId;
    private Long clientId;
    private String productCode;
    private BigDecimal quantity;
    private String address;
    @Enumerated
    private OrderStatus status;
}
