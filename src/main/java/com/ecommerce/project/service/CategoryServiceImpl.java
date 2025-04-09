package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{

    private List<Category> categories = new ArrayList<>();
    private Long next = 1L;

    @Override
    public List<Category> getAllCategories() {
        return categories;
    }

    @Override
    public String createCategory(Category category) {
        category.setCategoryId(next++);
        categories.add(category);
        return "Category Added Successfully";
    }

    @Override
    public String deleteCategory(Long categoryId) {

        Category category = categories.stream().
                filter(c->c.getCategoryId().equals(categoryId))
                .findFirst().orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Resource Not Found"));

        categories.remove(category);

        return "category with category Id: "+categoryId+" deleted successfully";
    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {

        Optional<Category> optionalCategory = categories.stream().
                filter(c->c.getCategoryId().equals(categoryId)).
                findFirst();

        if(optionalCategory.isPresent()){
            Category existCategory = optionalCategory.get();
            existCategory.setCategoryName(category.getCategoryName());
            return existCategory;
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Category Not Found");
        }
    }
}
