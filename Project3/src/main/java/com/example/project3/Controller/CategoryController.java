package com.example.project3.Controller;

import com.example.project3.ApiResponse.ApiResponse;
import com.example.project3.Model.Category;
import com.example.project3.Service.CategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/category/")
public class CategoryController {
    private final CategoryService categoryService;

   @GetMapping("get")
    public ResponseEntity getCategories(){
        return ResponseEntity.status(200).body(categoryService.getCategories());
    }

    @PostMapping("add")
    public ResponseEntity addCategory(@RequestBody @Valid Category category, Errors errors){
       if(errors.hasErrors()){
           return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
       }
       boolean isAvailable = categoryService.addCategory(category);
       if (isAvailable){
           categoryService.addCategory(category);
           return ResponseEntity.status(200).body( new ApiResponse("Category added!"));
       }
        return ResponseEntity.status(400).body( new ApiResponse("Category ID is already exists!"));
    }

    @PutMapping("update/{id}")
    public ResponseEntity updateCategory(@PathVariable Integer id, @RequestBody @Valid Category category, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        boolean isUpdated = categoryService.updateCategory(id, category);
        if (isUpdated) {
            return ResponseEntity.status(200).body(new ApiResponse("The category has been updated"));
        }
        return ResponseEntity.status(400).body( new ApiResponse("Index not found or category id is already exists!"));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity deleteCategory(@PathVariable Integer id){
        boolean idDeleted = categoryService.deleteCategory(id);
        if (idDeleted){
            return ResponseEntity.status(200).body(new ApiResponse("The category has been deleted"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Index not found"));
    }
}
