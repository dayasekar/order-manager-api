package net.bos.om.api.app.orderbook;

import lombok.extern.slf4j.Slf4j;
import net.bos.om.api.domain.OrderBook;
import net.bos.om.api.io.input.orderbook.CloseOrderBookInput;
import net.bos.om.api.io.input.orderbook.CreateOrderBookInput;
import net.bos.om.api.io.input.orderbook.OpenOrderBookInput;
import net.bos.om.api.io.output.orderbook.OrderBookOutput;
import net.bos.om.api.service.BookingService;
import net.bos.om.api.vo.InvocationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.ArrayList;

@Component
@Slf4j
@Validated
public class OrderBookApp {

    @Autowired
    private BookingService service;

    public InvocationResponse<OrderBookOutput> createOrderBook(@Valid CreateOrderBookInput input) {
        log.info("Creating order book {}", input);
        OrderBook orderBook = createOrderBookDomain(input);
        orderBook = service.createOrderBook(orderBook);

        InvocationResponse<OrderBookOutput> output = getInvocationResponse(orderBook);
        log.info("Created order book {}", output);
        return output;
    }

    public InvocationResponse<OrderBookOutput> openOrderBook(@Valid OpenOrderBookInput input) {
        log.info("Opening order book {}", input);
        OrderBook orderBook = service.openOrderBook(input.getInstrumentID());

        InvocationResponse<OrderBookOutput> output = getInvocationResponse(orderBook);
        log.info("Opened order book {}", output);
        return output;
    }

    public InvocationResponse<OrderBookOutput> closeOrderBook(@Valid CloseOrderBookInput input) {
        log.info("Closing order book {}", input);
        OrderBook orderBook = service.closeOrderBook(input.getInstrumentID());

        InvocationResponse<OrderBookOutput> output = getInvocationResponse(orderBook);
        log.info("Closed order book {}", output);
        return output;
    }

    private OrderBook createOrderBookDomain(CreateOrderBookInput input) {
        OrderBook orderBook = new OrderBook();
        orderBook.setInstrumentID(input.getInstrumentID());
        orderBook.setInstrumentType(input.getInstrumentType());
        orderBook.setOrders(new ArrayList<>());
        orderBook.setExecutions(new ArrayList<>());
        return orderBook;
    }

    private InvocationResponse<OrderBookOutput> getInvocationResponse(OrderBook orderBook) {
        OrderBookOutput outputVO = new OrderBookOutput();
        outputVO.setOrderBook(orderBook);

        InvocationResponse<OrderBookOutput> output = new InvocationResponse<>();
        output.setResponse(outputVO);
        return output;
    }
}
