package com.trolit.github.grocerystore.controllers.category;

import com.trolit.github.grocerystore.dto.category.CategoryCreateDto;
import com.trolit.github.grocerystore.dto.category.CategoryQueryDto;
import com.trolit.github.grocerystore.dto.category.CategoryUpdateDto;
import com.trolit.github.grocerystore.services.category.CategoryCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("api/v1/categories")
@RestController
public class CategoryCommandController {

    private final CategoryCommandService categoryCommandService;

    @Autowired
    public CategoryCommandController(CategoryCommandService categoryCommandService) {
        this.categoryCommandService = categoryCommandService;
    }

    @PostMapping
    public ResponseEntity<Integer> createCategory(@Valid @RequestBody CategoryCreateDto categoryCreateDto){
        return new ResponseEntity<>(categoryCommandService.createCategory(categoryCreateDto), HttpStatus.CREATED);
    }

    @PutMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryQueryDto> updateCategory(@PathVariable(value = "id") int id,
                                                          @RequestBody CategoryUpdateDto categoryUpdateDto) {
        CategoryQueryDto categoryQueryDto = categoryCommandService.updateCategory(id, categoryUpdateDto);
        if (categoryQueryDto == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        } else {
            return new ResponseEntity<>(categoryQueryDto, HttpStatus.OK);
        }
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable(value = "id") int id) {
        int result = categoryCommandService.deleteCategory(id);
        if (result == 1) {
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body(null);
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Category with given Id not found.");
        }
    }
}
