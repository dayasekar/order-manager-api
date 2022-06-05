package net.bos.om.api.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Order {
    private Integer quantity;
    private Date entryDate;
    private String instrumentID;
    private OrderType orderType;
    private Double price;
}
