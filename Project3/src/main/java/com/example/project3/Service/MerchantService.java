package com.example.project3.Service;

import com.example.project3.Model.Merchant;
import com.example.project3.Model.MerchantStock;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MerchantService {

     MerchantStockService merchantStockService;
    ArrayList<Merchant> merchants = new ArrayList<>();

    public ArrayList<Merchant> getMerchants() {
        return merchants;
    }

    public boolean addMerchant(Merchant merchant){
        //To make merchant id unique
        for (Merchant merchantIndex : merchants) {
            if (merchantIndex.getId().equals(merchant.getId())) {
                return false;
            }
        }
        merchants.add(merchant);
        return true;
    }

    public boolean updateMerchant(Integer id, Merchant merchant){
        for (int i = 0; i < merchants.size() ; i++) {
            //check if merchant id is existing in other indexes "without checking the current id"
            for (Merchant merchantIndex : merchants) {
                if (merchantIndex.getId().equals(merchant.getId()) && !merchantIndex.getId().equals(id)){
                    return false;
                }
            }
            if(merchants.get(i).getId().equals(id)){
                merchants.set(i, merchant);
                return true;
            }
        }
        return false;
    }

    public boolean deleteMerchant(Integer id) {
        for (int i = 0; i < merchants.size() ; i++) {
            if(merchants.get(i).getId().equals(id)){
                merchants.remove(i);
                return true;
            }
        }
        return false;
    }

}
