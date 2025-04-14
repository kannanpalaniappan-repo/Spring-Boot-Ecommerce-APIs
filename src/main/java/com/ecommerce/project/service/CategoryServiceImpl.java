package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{

    //private List<Category> categories = new ArrayList<>();
    private Long next = 1L;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {

        return categoryRepository.findAll();
    }

    @Override
    public String createCategory(Category category) {
        category.setCategoryId(next++);
        categoryRepository.save(category);
        return "Category Added Successfully";
    }

    @Override
    public String deleteCategory(Long categoryId) {

        List<Category> categories = categoryRepository.findAll();
        Category category = categories.stream().
                filter(c->c.getCategoryId().equals(categoryId))
                .findFirst().orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Resource Not Found"));

        categoryRepository.delete(category);
        return "category with category Id: "+categoryId+" deleted successfully";
    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {
        List<Category> categories = categoryRepository.findAll();
        Optional<Category> optionalCategory = categories.stream().
                filter(c->c.getCategoryId().equals(categoryId)).
                findFirst();

        if(optionalCategory.isPresent()){
            Category existCategory = optionalCategory.get();
            existCategory.setCategoryName(category.getCategoryName());
            Category savedCategory = categoryRepository.save(existCategory);
            return savedCategory;
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Category Not Found");
        }
    }
}
