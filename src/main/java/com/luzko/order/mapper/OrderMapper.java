package com.luzko.order.mapper;

import com.luzko.order.dto.OrderRequestDto;
import com.luzko.order.dto.OrderResponseDto;
import com.luzko.order.model.Order;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring",
        uses = OrderMapper.class,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED
)
public interface OrderMapper {
    Order toOrder(OrderRequestDto orderRequestDto);

    OrderResponseDto toOrderResponseDto(Order order);
}
