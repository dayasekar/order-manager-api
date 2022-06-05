package net.bos.om.api.vo;

import lombok.Data;

import java.util.List;

@Data
public class InvocationResponse<T> {

    private T response;
    private List<String> errorMessages;
}
