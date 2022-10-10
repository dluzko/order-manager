package com.luzko.order.service;

import com.luzko.order.dto.*;
import com.luzko.order.mapper.OrderMapper;
import com.luzko.order.model.Order;
import com.luzko.order.model.OrderStatus;
import com.luzko.order.repository.OrderRepository;
import com.luzko.order.service.client.WarehouseManagerClient;
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
        orderRequestDto.setStatus(OrderStatus.APPROVED);
        Order order = orderRepository.save(orderMapper.toOrder(orderRequestDto));
        warehouseManagerClient.callBlockProducts(List.of(orderMapper.toBlockingRequestDto(order)));
        return orderMapper.toOrderResponseDto(order);
    }

    @Transactional
    public OrderResponseDto cancelOrder(Long id) {
        Order order = orderRepository.findByOrderId(id);
        order.setStatus(OrderStatus.CANCELED);
        order = orderRepository.save(order);
        warehouseManagerClient.callUnblockProducts(List.of(orderMapper.toBlockingRequestDto(order)));
        return orderMapper.toOrderResponseDto(order);
    }

    @Transactional
    public OrderResponseDto departOrder(Long id) {
        Order order = orderRepository.findByOrderId(id);
        order.setStatus(OrderStatus.DEPARTED);
        order = orderRepository.save(order);
        warehouseManagerClient.callDepartProducts(List.of(orderMapper.toBlockingRequestDto(order)));
        return orderMapper.toOrderResponseDto(order);
    }
}
