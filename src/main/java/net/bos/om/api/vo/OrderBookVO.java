package net.bos.om.api.vo;

import lombok.Data;

import java.util.List;

@Data
public class OrderBookVO {

    private String instrumentID;
    private String instrumentType;
    private String status;
    private List<OrderVO> orders;
    private List<ExecutionVO> executions;

}
