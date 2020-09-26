package com.trolit.github.grocerystore.controllers.category;

import com.trolit.github.grocerystore.dto.category.CategoryCreateDto;
import com.trolit.github.grocerystore.dto.category.CategoryQueryDto;
import com.trolit.github.grocerystore.dto.category.CategoryUpdateDto;
import com.trolit.github.grocerystore.services.category.CategoryCommandService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @ApiOperation(value = "Creates category")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Category created")})
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<Integer> createCategory(@Valid @RequestBody CategoryCreateDto categoryCreateDto){
        return new ResponseEntity<>(
                categoryCommandService.createCategory(categoryCreateDto), HttpStatus.CREATED);
    }

    @PutMapping(path = "{id}")
    @ApiOperation(value = "Updates category")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Category updated"),
            @ApiResponse(code = 404, message = "Category not found")})
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<CategoryQueryDto> updateCategory(@PathVariable(value = "id") int id,
                                                          @RequestBody CategoryUpdateDto categoryUpdateDto) {
        CategoryQueryDto categoryQueryDto = categoryCommandService.updateCategory(id, categoryUpdateDto);
        if (categoryQueryDto == null) {
            return ResponseEntity
                    .notFound()
                    .build();
        } else {
            return new ResponseEntity<>(categoryQueryDto, HttpStatus.OK);
        }
    }

    @DeleteMapping(path = "{id}")
    @ApiOperation(value = "Deletes category")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Category deleted"),
            @ApiResponse(code = 404, message = "Category not found")})
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteCategory(@PathVariable(value = "id") int id) {
        int result = categoryCommandService.deleteCategory(id);
        if (result == 1) {
            return ResponseEntity
                    .noContent()
                    .build();
        } else {
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }
}
