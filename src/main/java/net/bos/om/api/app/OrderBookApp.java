package net.bos.om.api.app;

import lombok.extern.slf4j.Slf4j;
import net.bos.om.api.rest.input.CloseOrderBookInput;
import net.bos.om.api.rest.input.CreateOrderBookInput;
import net.bos.om.api.rest.input.OpenOrderBookInput;
import net.bos.om.api.rest.output.OrderBookOutput;
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
