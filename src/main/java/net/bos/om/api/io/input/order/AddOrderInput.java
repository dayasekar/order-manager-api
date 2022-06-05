package net.bos.om.api.io.input.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import net.bos.om.api.validation.constraint.OrderTypeConstraint;
import net.bos.om.api.validation.constraint.PriceConstraint;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@PriceConstraint
public class AddOrderInput {

    @NotNull(message = "{quantity.empty}")
    @Min(value = 100, message = "{quantity.min}")
    @Max(value = 100000, message = "{quantity.max}")
    private Integer quantity;

    @NotNull(message = "{order.entryDate.empty}")
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private Date entryDate;

    @NotBlank(message = "{instrumentID.empty}")
    private String instrumentID;

    @OrderTypeConstraint
    private String orderType;

    private Double price;
}
