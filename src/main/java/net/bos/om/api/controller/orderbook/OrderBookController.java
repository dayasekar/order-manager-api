package net.bos.om.api.controller.orderbook;

import net.bos.om.api.app.orderbook.OrderBookApp;
import net.bos.om.api.io.input.orderbook.CloseOrderBookInput;
import net.bos.om.api.io.input.orderbook.CreateOrderBookInput;
import net.bos.om.api.io.input.orderbook.OpenOrderBookInput;
import net.bos.om.api.io.output.orderbook.OrderBookOutput;
import net.bos.om.api.vo.InvocationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderBookController {

    @Autowired
    private OrderBookApp orderBookApp;

    @PostMapping("/order-book/create")
    public InvocationResponse<OrderBookOutput> createOrderBook(CreateOrderBookInput input) {
        return orderBookApp.createOrderBook(input);
    }

    @PostMapping("/order-book/open")
    public InvocationResponse<OrderBookOutput> openOrderBook(OpenOrderBookInput input) {
        return orderBookApp.openOrderBook(input);
    }

    @PostMapping("/order-book/close")
    public InvocationResponse<OrderBookOutput> closeOrderBook(CloseOrderBookInput input) {
        return orderBookApp.closeOrderBook(input);
    }
}
