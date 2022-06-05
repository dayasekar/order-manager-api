package net.bos.om.api.app.execution;

import lombok.extern.slf4j.Slf4j;
import net.bos.om.api.domain.Execution;
import net.bos.om.api.io.input.execution.AddExecutionInput;
import net.bos.om.api.io.output.execution.ExecutionOutput;
import net.bos.om.api.service.BookingService;
import net.bos.om.api.vo.InvocationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Component
@Slf4j
@Validated
public class ExecutionApp {

    @Autowired
    private BookingService service;

    public InvocationResponse<ExecutionOutput> addExecution(@Valid AddExecutionInput input) {
        log.info("Adding execution {}", input);
        Execution execution = getExecution(input);

        execution = service.addExecution(execution);

        InvocationResponse<ExecutionOutput> output = getInvocationResponse(execution);

        log.info("Added execution {}", output);
        return output;
    }

    private Execution getExecution(AddExecutionInput input) {
        Execution execution = new Execution();
        execution.setInstrumentID(input.getInstrumentID());
        execution.setPrice(input.getPrice());
        execution.setQuantity(input.getQuantity());
        return execution;
    }

    private InvocationResponse<ExecutionOutput> getInvocationResponse(Execution execution) {
        ExecutionOutput outputVO = new ExecutionOutput();
        outputVO.setExecution(execution);
        InvocationResponse<ExecutionOutput> output = new InvocationResponse<>();
        output.setResponse(outputVO);
        return output;
    }
}
