package com.example.project3.Controller;

import com.example.project3.ApiResponse.ApiResponse;
import com.example.project3.Model.Merchant;
import com.example.project3.Model.MerchantStock;
import com.example.project3.Service.MerchantService;
import com.example.project3.Service.MerchantStockService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/merchant/")
public class MerchantController {

    private final MerchantService merchantService;
    private final MerchantStockService merchantStockService;

    @GetMapping("get")
    public ResponseEntity getMerchants(){
        return ResponseEntity.status(200).body(merchantService.getMerchants());
    }

    @PostMapping("add")
    public ResponseEntity addMerchant(@RequestBody @Valid Merchant merchant, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        boolean isAvailable = merchantService.addMerchant(merchant);
        if (isAvailable){
            merchantService.addMerchant(merchant);
            return ResponseEntity.status(200).body( new ApiResponse("Merchant added!"));
        }
        return ResponseEntity.status(400).body( new ApiResponse("Merchant ID is already exists!"));
    }

    @PutMapping("update/{id}")
    public ResponseEntity updateMerchant(@PathVariable Integer id, @RequestBody @Valid Merchant merchant, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        boolean isUpdated = merchantService.updateMerchant(id, merchant);
        if (isUpdated) {
            return ResponseEntity.status(200).body(new ApiResponse("The merchant has been updated"));
        }
        return ResponseEntity.status(400).body( new ApiResponse("Index not found or merchant id is already exists!"));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity deleteMerchant(@PathVariable Integer id){
        boolean idDeleted = merchantService.deleteMerchant(id);
        if (idDeleted){
            return ResponseEntity.status(200).body(new ApiResponse("The merchant has been deleted"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Index not found"));
    }

    @GetMapping("addToStock/{productID}/{merchantID}/{amount}")
    public ResponseEntity addToStock(@PathVariable Integer productID, @PathVariable Integer merchantID, @PathVariable Integer amount){
        boolean isFound = merchantStockService.addToStock(productID, merchantID, amount);
        if (isFound){
            return ResponseEntity.status(200).body(merchantStockService.getMerchantStocks());
        }
        else return ResponseEntity.status(400).body(new ApiResponse("Not found"));
    }
}
