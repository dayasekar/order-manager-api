package net.bos.om.api.io.input.execution;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class AddExecutionInput {

    @NotNull(message = "{quantity.empty}")
    @Min(value = 100, message = "{quantity.min}")
    @Max(value = 100000, message = "{quantity.max}")
    private Integer quantity;

    @NotNull(message = "{price.empty}")
    @Min(value = 1, message = "{price.min}")
    private Double price;
}
