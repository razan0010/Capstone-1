package com.example.project3.Service;

import com.example.project3.Model.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CategoryService {

    ArrayList<Category> categories = new ArrayList<>();

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public boolean addCategory(Category category){
        //To make category id unique
        for (Category categoryIndex : categories) {
            if (categoryIndex.getId().equals(category.getId())) {
                return false;
            }
        }
        categories.add(category);
        return true;
    }

    public boolean updateCategory(Integer id, Category category){
        for (int i = 0; i < categories.size() ; i++) {
            //check if category id is existing in other indexes "without checking the current id"
            for (Category categoryIndex : categories) {
                if (categoryIndex.getId().equals(category.getId()) && !categoryIndex.getId().equals(id)){
                    return false;
                }
            }
            if(categories.get(i).getId().equals(id)){
                categories.set(i, category);
                return true;
            }
        }
        return false;
    }

    public boolean deleteCategory(Integer id) {
        for (int i = 0; i < categories.size() ; i++) {
            if(categories.get(i).getId().equals(id)){
                categories.remove(i);
                return true;
            }
        }
        return false;
    }
}
