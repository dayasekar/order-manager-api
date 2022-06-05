package net.bos.om.api.io.input.orderbook;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CloseOrderBookInput {

    @NotBlank(message = "{instrumentID.empty}")
    private String instrumentID;
}
