package com.luzko.order.dto;

import com.luzko.order.model.BlockingReason;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;

@Data
public class BlockingDto {
    private String blockingToken;
    @Enumerated(EnumType.STRING)
    private BlockingReason blockingReason;
    private BigDecimal blockedQuantity;
}
