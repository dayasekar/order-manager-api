package net.bos.om.api.io.input.orderbook;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateOrderBookInput {

    @NotBlank(message = "{orderBook.instrumentID.empty}")
    private String instrumentID;
    @NotBlank(message = "{orderBook.instrumentType.empty}")
    private String instrumentType;
}
