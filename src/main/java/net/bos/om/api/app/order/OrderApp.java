package net.bos.om.api.app.order;

import lombok.extern.slf4j.Slf4j;
import net.bos.om.api.domain.Order;
import net.bos.om.api.domain.OrderType;
import net.bos.om.api.io.input.order.AddOrderInput;
import net.bos.om.api.io.output.order.OrderOutput;
import net.bos.om.api.service.BookingService;
import net.bos.om.api.vo.InvocationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Component
@Slf4j
@Validated
public class OrderApp {

    @Autowired
    private BookingService service;

    public InvocationResponse<OrderOutput> addOrder(@Valid AddOrderInput input) {
        log.info("Adding order {}", input);
        Order order = getOrder(input);

        order = service.addOrder(order);

        InvocationResponse<OrderOutput> output = getInvocationResponse(order);

        log.info("Added order book {}", output);
        return output;
    }

    private Order getOrder(AddOrderInput input) {
        Order order = new Order();
        order.setOrderType(OrderType.valueOf(input.getOrderType()));
        order.setEntryDate(input.getEntryDate());
        order.setInstrumentID(input.getInstrumentID());
        order.setPrice(input.getPrice());
        order.setQuantity(input.getQuantity());
        return order;
    }

    private InvocationResponse<OrderOutput> getInvocationResponse(Order order) {
        OrderOutput outputVO = new OrderOutput();
        outputVO.setOrder(order);
        InvocationResponse<OrderOutput> output = new InvocationResponse<>();
        output.setResponse(outputVO);
        return output;
    }
}
