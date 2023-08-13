package com.example.project3.Controller;

import com.example.project3.ApiResponse.ApiResponse;
import com.example.project3.Model.Product;
import com.example.project3.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product/")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    @GetMapping("get")
    public ResponseEntity getProducts() {
        return ResponseEntity.status(200).body(productService.getProducts());
    }

    @PostMapping("add")
    public ResponseEntity addProduct(@RequestBody @Valid Product product, Errors errors){
        if (errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        boolean isAvalible = productService.addProduct(product);
        if(isAvalible){
            productService.addProduct(product);
            return ResponseEntity.status(200).body( new ApiResponse("Product has been added!"));
        }
        return ResponseEntity.status(400).body( new ApiResponse("Product ID is already exists!"));
    }

    @PutMapping("update/{id}")
    public ResponseEntity updateProduct(@PathVariable Integer id, @RequestBody @Valid Product product, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        boolean isUpdated = productService.updateProduct(id, product);
        if (isUpdated) {
            return ResponseEntity.status(200).body(new ApiResponse("The product has been updated"));
        }
        return ResponseEntity.status(400).body( new ApiResponse("Index not found or Product ID is already exists!"));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity deleteProduct(@PathVariable Integer id){
        boolean idDeleted = productService.deleteProduct(id);
        if (idDeleted){
            return ResponseEntity.status(200).body(new ApiResponse("The product has been deleted"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Index not found"));
    }
}
