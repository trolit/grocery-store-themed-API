package com.trolit.github.grocerystore.controllers.category;

import com.trolit.github.grocerystore.dto.category.CategoryQueryDto;
import com.trolit.github.grocerystore.services.category.CategoryQueryService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @ApiOperation(value = "Returns all available categories")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returned at least one category"),
            @ApiResponse(code = 204, message = "Request successful but no categories found")})
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<List<CategoryQueryDto>> getAllCategories(){
        List<CategoryQueryDto> categories = categoryQueryService.getAllCategories();
        if (categories.size() <= 0) {
            return ResponseEntity
                    .noContent()
                    .build();
        } else {
            return new ResponseEntity<>(categories, HttpStatus.OK);
        }
    }

    @GetMapping(path = "{id}")
    @ApiOperation(value = "Returns category within given id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Category returned"),
            @ApiResponse(code = 404, message = "Category not found")})
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<CategoryQueryDto> getCategory(@PathVariable(value = "id") int id){
        CategoryQueryDto category = categoryQueryService.getCategory(id);
        if (category == null) {
            return ResponseEntity
                    .notFound()
                    .build();
        } else {
            return new ResponseEntity<>(category, HttpStatus.OK);
        }
    }
}
