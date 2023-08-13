package com.example.project3.Controller;

import com.example.project3.ApiResponse.ApiResponse;
import com.example.project3.Model.MerchantStock;
import com.example.project3.Service.MerchantStockService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/merchantStock/")
@RestController
@AllArgsConstructor
public class MerchantStockController {
    private final MerchantStockService merchantStockService;

    @GetMapping("get")
    public ResponseEntity getMerchantStocks(){
        return ResponseEntity.status(200).body(merchantStockService.getMerchantStocks());
    }

    @PostMapping("add")
    public ResponseEntity addMerchantStocks(@RequestBody @Valid MerchantStock merchantStock, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        Integer returnValue = merchantStockService.addMerchantStocks(merchantStock);
        switch (returnValue){
            case 1: {
                return ResponseEntity.status(400).body(new ApiResponse("Merchant Stock ID is already exists!"));
            }
            case 2: {
                return ResponseEntity.status(400).body(new ApiResponse("Product ID not found!"));
            }
            case 3: {
                return ResponseEntity.status(400).body(new ApiResponse("Merchant ID not found!"));
            }
            case 4:{
                return ResponseEntity.status(200).body( new ApiResponse("Merchant Stock added!"));
            }
        }
        return ResponseEntity.status(200).body( new ApiResponse("Merchant Stock added!"));
    }

    @PutMapping("update/{id}")
    public ResponseEntity updateMerchantStocks(@PathVariable Integer id, @RequestBody @Valid MerchantStock merchantStock, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        boolean isUpdated = merchantStockService.updateMerchantStocks(id, merchantStock);
        if (isUpdated) {
            return ResponseEntity.status(200).body(new ApiResponse("The merchant stock has been updated"));
        }
        return ResponseEntity.status(400).body( new ApiResponse("Index not found or merchant id is already exists!"));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity deleteMerchantStocks(@PathVariable Integer id){
        boolean idDeleted = merchantStockService.deleteMerchantStocks(id);
        if (idDeleted){
            return ResponseEntity.status(200).body(new ApiResponse("The merchant has been deleted"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Index not found"));
    }
}
