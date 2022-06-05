package net.bos.om.api.domain;

import lombok.Data;

@Data
public class Execution {
    private String instrumentID;
    private Integer quantity;
    private Double price;
}
