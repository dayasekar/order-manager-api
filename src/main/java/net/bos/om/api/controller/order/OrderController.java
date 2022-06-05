package net.bos.om.api.controller.order;

import net.bos.om.api.app.order.OrderApp;
import net.bos.om.api.io.input.order.AddOrderInput;
import net.bos.om.api.io.output.order.OrderOutput;
import net.bos.om.api.vo.InvocationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    private OrderApp orderApp;

    @PostMapping("/order/add")
    public InvocationResponse<OrderOutput> addOrder(AddOrderInput addOrderInput) {
        return orderApp.addOrder(addOrderInput);
    }
}
