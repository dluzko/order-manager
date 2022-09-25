package com.luzko.order.web.controller;

import com.luzko.order.dto.OrderRequestDto;
import com.luzko.order.dto.OrderResponseDto;
import com.luzko.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponseDto>> getAllOrders() {
        final var response = orderService.getAllOrders();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/orders")
    public ResponseEntity<OrderResponseDto> placeOrder(
            @Valid @RequestBody OrderRequestDto orderRequestDto) {
        final var response = orderService.createOrder(orderRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/orders/{id}/cancel")
    public ResponseEntity<OrderResponseDto> cancelOrder(@PathVariable("id") Long id) {
        final var response = orderService.cancelOrder(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/orders/{id}/depart")
    public ResponseEntity<OrderResponseDto> departOrder(@PathVariable("id") Long id) {
        final var response = orderService.departOrder(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}