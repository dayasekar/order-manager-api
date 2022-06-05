package net.bos.om.api.app.execution;

import lombok.extern.slf4j.Slf4j;
import net.bos.om.api.io.input.execution.AddExecutionInput;
import net.bos.om.api.io.output.execution.ExecutionOutput;
import net.bos.om.api.vo.InvocationResponse;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Component
@Slf4j
@Validated
public class ExecutionApp {

    public InvocationResponse<ExecutionOutput> addExecution(@Valid AddExecutionInput input) {
        log.info("Adding execution {}", input);
        InvocationResponse<ExecutionOutput> output = new InvocationResponse<>();
        output.setResponse(new ExecutionOutput());
        log.info("Added execution {}", output);
        return output;
    }
}
