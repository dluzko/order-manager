package com.luzko.order.service.client;

import com.luzko.order.dto.BlockingRequestDto;
import com.luzko.order.service.exception.ErrorCode;
import com.luzko.order.service.exception.OrderManagerException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WarehouseManagerClient {

    private final RestTemplate warehouseManagerClientSettingsProperties;

    public void callBlockProducts(final List<BlockingRequestDto> blockingRequestDtos) {
        if (blockingRequestDtos.isEmpty()) {
            return;
        }
        try {
            warehouseManagerClientSettingsProperties.exchange(
                    "/products/block",
                    HttpMethod.POST,
                    new HttpEntity<>(blockingRequestDtos),
                    new ParameterizedTypeReference<>() {
                    }
            );
        } catch (Exception ex) {
            throw new OrderManagerException(ErrorCode.WAREHOUSE_MANAGER_ERROR, ex.toString());
        }
    }

    public void callUnblockProducts(final List<BlockingRequestDto> unblockingRequestDtos) {
        if (unblockingRequestDtos.isEmpty()) {
            return;
        }
        try {
            warehouseManagerClientSettingsProperties.exchange(
                    "/products/unblock",
                    HttpMethod.POST,
                    new HttpEntity<>(unblockingRequestDtos),
                    new ParameterizedTypeReference<>() {
                    }
            );
        } catch (Exception ex) {
            throw new OrderManagerException(ErrorCode.WAREHOUSE_MANAGER_ERROR, ex.toString());
        }
    }

    public void callDepartProducts(final List<BlockingRequestDto> departingRequestDtos) {
        if (departingRequestDtos.isEmpty()) {
            return;
        }
        try {
            warehouseManagerClientSettingsProperties.exchange(
                    "/products/depart",
                    HttpMethod.POST,
                    new HttpEntity<>(departingRequestDtos),
                    new ParameterizedTypeReference<>() {
                    }
            );
        } catch (Exception ex) {
            throw new OrderManagerException(ErrorCode.WAREHOUSE_MANAGER_ERROR, ex.toString());
        }
    }
}
