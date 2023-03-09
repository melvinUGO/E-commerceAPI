package com.Api.Controller;

import com.Api.Entity.Category;
import com.Api.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    @Autowired
    public CategoryService categoryService;

    @GetMapping("/")
    public ResponseEntity<List<Category>> getCategories(){
        return new ResponseEntity<>(categoryService.getAllCategories(),HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<String> createCategory(@Valid @RequestBody Category category){

//        checking if category already exist
        if(Objects.nonNull(categoryService.readCategory(category.getCategoryName()))){
//            returns an unsuccessful status if category already exist
            return new ResponseEntity<>("Category already exist",HttpStatus.CONFLICT);
        }

//        creating new category
        categoryService.createCategory(category);
        return new ResponseEntity<>("Created the Category",HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable Integer id,@Valid @RequestBody Category category ){
//        CHECK TO SEE IF THE CATEGORY ACTUALLY EXIST
        if(Objects.nonNull(categoryService.readCategory(id))){
//            UPDATING THE CATEGORY IF IT DOES EXIST
            categoryService.updateCategory(id, category);
            return new ResponseEntity<>("Updated Category",HttpStatus.OK);
        }

//        IF THE CATEGORY DOES NOT EXIST IT RETURNS A STATUS OF UNSUCCESSFUL
        return new ResponseEntity<>("Category does not exist",HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCategory(@PathVariable Integer id){
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
