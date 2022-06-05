package net.bos.om.api.app.orderbook;

import lombok.extern.slf4j.Slf4j;
import net.bos.om.api.io.input.orderbook.CloseOrderBookInput;
import net.bos.om.api.io.input.orderbook.CreateOrderBookInput;
import net.bos.om.api.io.input.orderbook.OpenOrderBookInput;
import net.bos.om.api.io.output.orderbook.OrderBookOutput;
import net.bos.om.api.vo.InvocationResponse;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Component
@Slf4j
@Validated
public class OrderBookApp {

    public InvocationResponse<OrderBookOutput> createOrderBook(@Valid CreateOrderBookInput input) {
        log.info("Creating order book {}", input);
        InvocationResponse<OrderBookOutput> output = new InvocationResponse<>();
        output.setResponse(new OrderBookOutput());
        log.info("Created order book {}", output);
        return output;
    }

    public InvocationResponse<OrderBookOutput> openOrderBook(@Valid OpenOrderBookInput input) {
        log.info("Opening order book {}", input);
        InvocationResponse<OrderBookOutput> output = new InvocationResponse<>();
        output.setResponse(new OrderBookOutput());
        log.info("Opened order book {}", output);
        return output;
    }

    public InvocationResponse<OrderBookOutput> closeOrderBook(@Valid CloseOrderBookInput input) {
        log.info("Closing order book {}", input);
        InvocationResponse<OrderBookOutput> output = new InvocationResponse<>();
        output.setResponse(new OrderBookOutput());
        log.info("Closed order book {}", output);
        return output;
    }
}
