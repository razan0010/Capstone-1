package com.example.project3.Service;

import com.example.project3.Model.Merchant;
import com.example.project3.Model.MerchantStock;
import com.example.project3.Model.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class MerchantStockService {

    ArrayList<MerchantStock> merchantStocks =new ArrayList<>();
    private final ProductService productService;
    private final MerchantService merchantService;

    public ArrayList<MerchantStock> getMerchantStocks() {
        return merchantStocks;
    }

    public Integer addMerchantStocks(MerchantStock merchantStock){
        ArrayList<Product> newProduct = new ArrayList<>();
        ArrayList<Merchant> newMerchant = new ArrayList<>();
        //To make merchantStock id unique
        for (MerchantStock merchantStockIndex : merchantStocks) {
            if (merchantStockIndex.getId().equals(merchantStock.getId())) {
                return 1;
            }
        }
        //To check if product id is filled
        if(productService.getProducts().isEmpty()){
            return 2;
        }
        //To check if product id is existing
        for (Product product : productService.products) {
            if (product.getId().equals(merchantStock.getProductId())) {
                newProduct.add(product);
            }
            if (newProduct.isEmpty()) {
                return 2;
            }
        }
        //To check if merchant is filled
        if (merchantService.getMerchants().isEmpty()){
            return 3;
        }
        //To check if merchant id is existing
        for (Merchant merchant : merchantService.merchants) {
                if (merchant.getId().equals(merchantStock.getMerchantId())) {
                    newMerchant.add(merchant);
                }
                if (newMerchant.isEmpty()){
                    return 3;
                }
        }
        merchantStocks.add(merchantStock);
        return 4;
    }

    public boolean updateMerchantStocks(Integer id, MerchantStock merchantStock){
        for (int i = 0; i < merchantStocks.size() ; i++) {
            //check if merchantStock id is existing in other indexes "without checking the current id"
            for (MerchantStock merchantStockIndex : merchantStocks) {
                if (merchantStockIndex.getId().equals(merchantStock.getId()) && !merchantStockIndex.getId().equals(id)){
                    return false;
                }
            }
            if(merchantStocks.get(i).getId().equals(id)){
                merchantStocks.set(i, merchantStock);
                return true;
            }
        }
        return false;
    }

    public boolean deleteMerchantStocks(Integer id) {
        for (int i = 0; i < merchantStocks.size() ; i++) {
            if(merchantStocks.get(i).getId().equals(id)){
                merchantStocks.remove(i);
                return true;
            }
        }
        return false;
    }

    //Create endpoint where merchant can add more stocks of product to a merchant Stock.
    public boolean addToStock(Integer productID, Integer merchantID,Integer amount){
        for (int i = 0; i < merchantStocks.size() ; i++) {
            if (merchantStocks.get(i).getProductId().equals(productID) && merchantStocks.get(i).getMerchantId().equals(merchantID)){
                merchantStocks.get(i).setStock(merchantStocks.get(i).getStock()+ amount);
                return true;
            }
        }
        return false;
    }
}
