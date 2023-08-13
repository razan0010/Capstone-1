package com.example.project3.Service;

import com.example.project3.ApiResponse.ApiResponse;
import com.example.project3.Model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProductService {

    ArrayList<Product> products = new ArrayList<>();

    public ArrayList<Product> getProducts() {
        return products;
    }

    public boolean addProduct(Product product){
        //To make product id unique
        for (Product productIndex : products) {
            if (productIndex.getId().equals(product.getId())) {
                return false;
            }
        }
        products.add(product);
        return true;
    }

    public boolean updateProduct(Integer id, Product product){
        for (int i = 0; i < products.size() ; i++) {
            //check if product id is existing in other indexes "without checking the current id"
            for (Product productIndex : products) {
                if (productIndex.getId().equals(product.getId()) && !productIndex.getId().equals(id)){
                    return false;
                }
            }
             if (products.get(i).getId().equals(id)) {
                    products.set(i, product);
                    return true;
                }
            }
        return false;
    }

    public boolean deleteProduct(Integer id) {
        for (int i = 0; i < products.size() ; i++) {
            if(products.get(i).getId().equals(id)){
                products.remove(i);
                return true;
            }
        }
        return false;
    }
}
