package com.example.project3.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {

    @NotNull(message = "ID must be not empty!")
    private Integer id;

    @NotEmpty(message = "Name must be not empty!")
    @Size(min = 3, message = "Length of name must be more than 3")
    private String name;

    @NotNull(message = "Price must be not empty!")
    @Positive(message = "Price must be positive number!")
    private Double price;

    @NotNull(message = "Category ID must be not empty!")
    private Integer categoryID;
}
