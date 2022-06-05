package net.bos.om.api.app.order;

import lombok.extern.slf4j.Slf4j;
import net.bos.om.api.io.input.order.AddOrderInput;
import net.bos.om.api.io.output.order.OrderOutput;
import net.bos.om.api.vo.InvocationResponse;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Component
@Slf4j
@Validated
public class OrderApp {

    public InvocationResponse<OrderOutput> addOrder(@Valid AddOrderInput input) {
        log.info("Adding order {}", input);
        InvocationResponse<OrderOutput> output = new InvocationResponse<>();
        output.setResponse(new OrderOutput());
        log.info("Added order book {}", output);
        return output;
    }
}
