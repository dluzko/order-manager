package com.luzko.order.mapper;

import com.luzko.order.dto.BlockingRequestDto;
import com.luzko.order.dto.OrderRequestDto;
import com.luzko.order.dto.OrderResponseDto;
import com.luzko.order.model.Order;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        uses = OrderMapper.class,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED
)
public interface OrderMapper {
    Order toOrder(OrderRequestDto orderRequestDto);

    OrderResponseDto toOrderResponseDto(Order order);

    @Mapping(source = "orderId", target = "blockingInfo.blockingToken", qualifiedByName = "setBlockingToken")
    @Mapping(expression = "java(com.luzko.order.model.BlockingReason.ORDER)", target = "blockingInfo.blockingReason")
    @Mapping(source = "quantity", target = "blockingInfo.blockedQuantity")
    BlockingRequestDto toBlockingRequestDto(Order order);

    @Named("setBlockingToken")
    static String setBlockingToken(Long orderId) {
        return String.format("ORDER-%05d", orderId);
    }
}
