package com.example.project3.Service;

import com.example.project3.Model.Merchant;
import com.example.project3.Model.MerchantStock;
import com.example.project3.Model.Product;
import com.example.project3.Model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class UserService {

    private final ProductService productService;
    private final MerchantService merchantService;
    private final MerchantStockService merchantStockService;

    ArrayList<User> users = new ArrayList<>();

    public ArrayList<User> getUsers() {
        return users;
    }

    public boolean addUser(User user){
        //To make user id unique
        for (User userIndex : users) {
            if (userIndex.getId().equals(user.getId()) && userIndex.getUsername().equals(user.getUsername())) {
                return false;
            }
            //To make username unique
            if (userIndex.getUsername().equals(user.getUsername())) {
                return false;
            }
        }
        users.add(user);
        return true;
    }

    public boolean updateUser(Integer id, User user){
        for (int i = 0; i < users.size() ; i++) {
            //check if user id is existing in other indexes "without checking the current id"
            for (User userIndex : users) {
                if (userIndex.getId().equals(user.getId()) && !userIndex.getId().equals(id)){
                    return false;
                }
            }
            if(users.get(i).getId().equals(id)){
                users.set(i, user);
                return true;
            }
        }
        return false;
    }

    public boolean deleteUser(Integer id) {
        for (int i = 0; i < users.size() ; i++) {
            if(users.get(i).getId().equals(id)){
                users.remove(i);
                return true;
            }
        }
        return false;
    }

//    Create endpoint where user can buy a product directly
    public Integer buyProduct(Integer userId, Integer productId , Integer merchantId){
        ArrayList<User> newUsers = new ArrayList<>();
        ArrayList<Product> newProducts = new ArrayList<>();
        ArrayList<Merchant> newMerchants = new ArrayList<>();
        ArrayList<MerchantStock> newMerchantsStock = new ArrayList<>();

//        check if all the given ids are valid or not
        for (int i = 0; i < users.size() ; i++) {
            if (users.get(i).getId().equals(userId)) {
                newUsers.add(users.get(i));
            }
        }
        if(newUsers.isEmpty()){
            return 1;
        }

        for (int j = 0; j < productService.products.size(); j++) {
            if (productService.products.get(j).getId().equals(productId)) {
                newProducts.add(productService.products.get(j));
            }
        }
        if (newProducts.isEmpty()){
            return 2;
        }

        for (int j = 0; j < merchantService.merchants.size(); j++) {
            if (merchantService.merchants.get(j).getId().equals(merchantId)) {
                newMerchants.add(merchantService.merchants.get(j));
            }
        }
        if (newMerchants.isEmpty()){
            return 3;
        }
        if (merchantStockService.merchantStocks.isEmpty()) {
            return 6;
        }


//      check if the merchant has the product in stock or return bad request.
        for (int j = 0; j < merchantStockService.merchantStocks.size(); j++) {
                if (merchantStockService.merchantStocks.get(j).getStock() <= 0) {
                    return 4;
                }
         }

//        if balance is less than the product price return bad request
        for (int i = 0; i < users.size(); i++) {
            if (newUsers.get(i).getBalance()< newProducts.get(i).getPrice()){
                return 5;
            }
        }

        // reduce the stock from the MerchantStock.
        for (int i = 0; i < merchantStockService.merchantStocks.size(); i++) {

            if (merchantStockService.merchantStocks.get(i).getProductId().equals(productId)) {
                merchantStockService.merchantStocks.get(i).setStock(merchantStockService.merchantStocks.get(i).getStock() - 1);
            }
            //deducted the price of the product from the user balance
            newUsers.get(i).setBalance(newUsers.get(i).getBalance() - productService.products.get(i).getPrice());

        }
        return 0;
    }
}
