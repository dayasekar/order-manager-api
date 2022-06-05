package net.bos.om.api.vo;

import lombok.Data;

import java.util.Date;

@Data
public class OrderVO {
    private Integer quantity;
    private Date entryDate;
    private String instrumentID;
    private String priceType;
    private Double price;
}
