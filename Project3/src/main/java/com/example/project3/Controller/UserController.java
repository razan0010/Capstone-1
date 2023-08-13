package com.example.project3.Controller;

import com.example.project3.ApiResponse.ApiResponse;
import com.example.project3.Model.User;
import com.example.project3.Service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("get")
    public ResponseEntity getUsers(){
        return ResponseEntity.status(200).body(userService.getUsers());
    }

    @PostMapping("add")
    public ResponseEntity addUser(@RequestBody @Valid User user, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        boolean isAvailable = userService.addUser(user);
        if (isAvailable){
            userService.addUser(user);
            return ResponseEntity.status(200).body( new ApiResponse("User added!"));
        }
        return ResponseEntity.status(400).body( new ApiResponse("User ID or username is already exists!"));
    }

    @PutMapping("update/{id}")
    public ResponseEntity updateMerchantStocks(@PathVariable Integer id, @RequestBody @Valid User user, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        boolean isUpdated = userService.updateUser(id, user);
        if (isUpdated) {
            return ResponseEntity.status(200).body(new ApiResponse("The user data has been updated"));
        }
        return ResponseEntity.status(400).body( new ApiResponse("Index not found or userId/username is already exists!"));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity deleteMerchantStocks(@PathVariable Integer id){
        boolean idDeleted = userService.deleteUser(id);
        if (idDeleted){
            return ResponseEntity.status(200).body(new ApiResponse("The user has been deleted"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Index not found"));
    }

    @GetMapping("buyProduct/{userId}/{productId}/{merchantId}")
    public ResponseEntity buyProduct(@PathVariable Integer userId, @PathVariable Integer productId, @PathVariable Integer merchantId){
        Integer returnValue = userService.buyProduct(userId, productId, merchantId);
        switch (returnValue){
            case 0: {
                return ResponseEntity.status(200).body(new ApiResponse("Purchase completed successfully!"));
            }
            case 1:{
                return ResponseEntity.status(400).body(new ApiResponse("User ID not found"));
            }
            case 2:{
                return ResponseEntity.status(400).body(new ApiResponse("Product ID not found"));
            }
            case 3:{
                return ResponseEntity.status(400).body(new ApiResponse("Merchant ID not found"));
            }
            case 4:{
                return ResponseEntity.status(400).body(new ApiResponse("The product is out of stock"));
            }
            case 5:{
                return ResponseEntity.status(400).body(new ApiResponse("Insufficient balance!"));
            }
            case 6:{
                return ResponseEntity.status(400).body(new ApiResponse("Merchant Stocks empty!"));
            }
        }
        return ResponseEntity.status(400).body(returnValue);
    }

}
