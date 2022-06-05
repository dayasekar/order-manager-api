package net.bos.om.api.rest.input;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class OpenOrderBookInput {

    @NotBlank(message = "{orderBook.instrumentID.empty}")
    private String instrumentID;
}
