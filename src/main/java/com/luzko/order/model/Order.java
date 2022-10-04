package com.luzko.order.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "client_order")
@Data
@Accessors(chain = true)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_id_seq")
    @SequenceGenerator(name = "order_id_seq", sequenceName = "order_id_seq", allocationSize = 1)
    private Long orderId;
    private Long clientId;
    private String productCode;
    private BigDecimal quantity;
    private String address;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
