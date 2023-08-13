package com.example.project3.Model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MerchantStock {

    @NotNull(message = "ID must be not empty!")
    private Integer id;

    @NotNull(message = "Product id must be not empty!")
    private Integer productId;

    @NotNull(message = "Merchant id must be not empty!")
    private Integer merchantId;

    @NotNull(message = "Stock must be not empty!")
    @Min(value = 10, message = "Stock must be have to be more than 10 at start")
    private Integer stock;

}