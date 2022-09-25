package com.luzko.order.service.client;

import com.luzko.order.dto.BlockProductRequestDto;
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

    public void callBlockProducts(final List<BlockProductRequestDto> blockProductRequestDtos) {
        if (blockProductRequestDtos.isEmpty()) {
            return;
        }
        warehouseManagerClientSettingsProperties.exchange(
                "/products/block",
                HttpMethod.POST,
                new HttpEntity<>(blockProductRequestDtos),
                new ParameterizedTypeReference<>() {
                }
        );
    }

    public void callUnblockProducts(final List<BlockProductRequestDto> unblockProductRequestDtos) {
        if (unblockProductRequestDtos.isEmpty()) {
            return;
        }
        warehouseManagerClientSettingsProperties.exchange(
                "/products/unblock",
                HttpMethod.POST,
                new HttpEntity<>(unblockProductRequestDtos),
                new ParameterizedTypeReference<>() {
                }
        );
    }

    public void callDepartProducts(final List<BlockProductRequestDto> departProductRequestDtos) {
        if (departProductRequestDtos.isEmpty()) {
            return;
        }
        warehouseManagerClientSettingsProperties.exchange(
                "/products/depart",
                HttpMethod.POST,
                new HttpEntity<>(departProductRequestDtos),
                new ParameterizedTypeReference<>() {
                }
        );
    }
}
