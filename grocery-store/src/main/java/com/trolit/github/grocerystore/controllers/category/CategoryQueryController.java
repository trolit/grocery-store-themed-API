package com.trolit.github.grocerystore.controllers.category;

import com.trolit.github.grocerystore.dto.category.CategoryQueryDto;
import com.trolit.github.grocerystore.services.category.CategoryQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("api/v1/categories")
@RestController
public class CategoryQueryController {

    private final CategoryQueryService categoryQueryService;

    @Autowired
    public CategoryQueryController(CategoryQueryService categoryQueryService) {
        this.categoryQueryService = categoryQueryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryQueryDto>> getAllCategories(){
        List<CategoryQueryDto> categories = categoryQueryService.getAllCategories();
        if (categories.size() <= 0) {
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body(null);
        } else {
            return new ResponseEntity<>(categories, HttpStatus.OK);
        }
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<CategoryQueryDto> getCategory(@PathVariable(value = "id") int id){
        CategoryQueryDto category = categoryQueryService.getCategory(id);
        if(category == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        } else {
            return new ResponseEntity<>(category, HttpStatus.OK);
        }
    }
}
