package net.bos.om.api.io.input.orderbook;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateOrderBookInput {

    @NotBlank(message = "{instrumentID.empty}")
    private String instrumentID;
    @NotBlank(message = "{instrumentType.empty}")
    private String instrumentType;
}
