package com.luzko.order.dto;

import com.luzko.order.model.OrderStatus;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class OrderRequestDto {
    @NotNull(message = "Client id should not be empty")
    private Long clientId;
    @NotNull(message = "Product code should not be empty")
    @Pattern(regexp = "PROD-\\d{5}$")
    private String productCode;
    @NotNull(message = "Quantity should not be empty")
    private BigDecimal quantity;
    @NotNull(message = "Address should not be empty")
    @Pattern(regexp = "\\d-\\d{2}-\\d{3}$")
    private String address;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Status should not be empty")
    private OrderStatus status;
}
