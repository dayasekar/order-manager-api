package net.bos.om.api.domain;

import lombok.Data;

import java.util.List;

@Data
public class OrderBook {

    private String instrumentID;
    private String instrumentType;
    private String status;
    private List<Order> orders;
    private List<Execution> executions;

}
