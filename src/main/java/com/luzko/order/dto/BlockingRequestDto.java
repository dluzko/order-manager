package com.luzko.order.dto;

import lombok.Data;

@Data
public class BlockingRequestDto {
    private String productCode;
    private String address;
    private BlockingDto blockingInfo;
}
