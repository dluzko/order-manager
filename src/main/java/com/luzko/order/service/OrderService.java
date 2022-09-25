package com.luzko.order.service;

import com.luzko.order.dto.*;
import com.luzko.order.mapper.OrderMapper;
import com.luzko.order.model.BlockingReason;
import com.luzko.order.model.Order;
import com.luzko.order.repository.OrderRepository;
import com.luzko.order.service.client.WarehouseManagerClient;
import com.luzko.order.service.exception.ErrorCode;
import com.luzko.order.service.exception.OrderManagerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final WarehouseManagerClient warehouseManagerClient;

    public List<OrderResponseDto> getAllOrders() {
        List<Order> foundOrders = orderRepository.findAll();
        return foundOrders.stream()
                .map(orderMapper::toOrderResponseDto)
                .toList();
    }

    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {
        orderRequestDto.setComment("APPROVED");
        Order order = orderRepository.saveAndFlush(orderMapper.toOrder(orderRequestDto));
        try {
            warehouseManagerClient.callBlockProducts(List.of(toBlockProductRequestDto(order)));
        } catch (Exception ex) {
            throw new OrderManagerException(ErrorCode.WAREHOUSE_MANAGER_ERROR, ex.toString());
        }
        return orderMapper.toOrderResponseDto(order);
    }

    @Transactional
    public OrderResponseDto cancelOrder(Long id) {
        Order order = orderRepository.findByOrderId(id);
        order.setComment("CANCELED");
        order = orderRepository.saveAndFlush(order);
        try {
            warehouseManagerClient.callUnblockProducts(List.of(toBlockProductRequestDto(order)));
        } catch (Exception ex) {
            throw new OrderManagerException(ErrorCode.WAREHOUSE_MANAGER_ERROR, ex.toString());
        }
        return orderMapper.toOrderResponseDto(order);
    }

    @Transactional
    public OrderResponseDto departOrder(Long id) {
        Order order = orderRepository.findByOrderId(id);
        order.setComment("DEPARTED");
        order = orderRepository.saveAndFlush(order);
        try {
            warehouseManagerClient.callDepartProducts(List.of(toBlockProductRequestDto(order)));
        } catch (Exception ex) {
            throw new OrderManagerException(ErrorCode.WAREHOUSE_MANAGER_ERROR, ex.toString());
        }
        return orderMapper.toOrderResponseDto(order);
    }

    private BlockProductRequestDto toBlockProductRequestDto(Order order) {
        BlockProductDto blockProductDto = new BlockProductDto();
        blockProductDto.setBlockedQuantity(order.getQuantity());
        blockProductDto.setBlockingReason(BlockingReason.ORDER);
        blockProductDto.setBlockingToken(String.format("ORDER-%05d", order.getOrderId()));

        BlockProductRequestDto blockProductRequestDto = new BlockProductRequestDto();
        blockProductRequestDto.setProductCode(order.getProductCode());
        blockProductRequestDto.setAddress(order.getAddress());
        blockProductRequestDto.setBlockInfo(blockProductDto);
        return blockProductRequestDto;
    }
}
