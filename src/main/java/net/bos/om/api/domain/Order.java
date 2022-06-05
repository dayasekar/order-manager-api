package net.bos.om.api.domain;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Order {
    private Integer quantity;
    private LocalDate entryDate;
    private String instrumentID;
    private OrderType orderType;
    private Double price;
}
