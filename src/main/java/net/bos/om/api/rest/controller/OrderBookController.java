package net.bos.om.api.rest.controller;

import net.bos.om.api.app.OrderBookApp;
import net.bos.om.api.rest.input.CloseOrderBookInput;
import net.bos.om.api.rest.input.CreateOrderBookInput;
import net.bos.om.api.rest.input.OpenOrderBookInput;
import net.bos.om.api.rest.output.OrderBookOutput;
import net.bos.om.api.vo.InvocationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderBookController {

    @Autowired
    private OrderBookApp orderBookApp;

    @PutMapping("/order-book/create")
    public InvocationResponse<OrderBookOutput> createOrderBook(CreateOrderBookInput input) {
        return orderBookApp.createOrderBook(input);
    }

    @PutMapping("/order-book/open")
    public InvocationResponse<OrderBookOutput> openOrderBook(OpenOrderBookInput input) {
        return orderBookApp.openOrderBook(input);
    }

    @PutMapping("/order-book/close")
    public InvocationResponse<OrderBookOutput> closeOrderBook(CloseOrderBookInput input) {
        return orderBookApp.closeOrderBook(input);
    }
}
