package net.bos.om.api.controller.execution;

import net.bos.om.api.app.execution.ExecutionApp;
import net.bos.om.api.io.input.execution.AddExecutionInput;
import net.bos.om.api.io.output.execution.ExecutionOutput;
import net.bos.om.api.vo.InvocationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExecutionController {

    @Autowired
    private ExecutionApp executionApp;

    @PostMapping("/execution/add")
    public InvocationResponse<ExecutionOutput> addOrder(AddExecutionInput addExecutionInput) {
        return executionApp.addExecution(addExecutionInput);
    }
}
