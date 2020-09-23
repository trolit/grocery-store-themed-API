package com.trolit.github.grocerystore.controllers;

import com.trolit.github.grocerystore.dto.ProductCreateDto;
import com.trolit.github.grocerystore.dto.ProductQueryDto;
import com.trolit.github.grocerystore.dto.ProductUpdateDto;
import com.trolit.github.grocerystore.services.ProductCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("api/v1/products")
@RestController
public class ProductCommandController {

    private final ProductCommandService productCommandService;

    @Autowired
    public ProductCommandController(ProductCommandService productCommandService) {
        this.productCommandService = productCommandService;
    }

    @PostMapping
    public ResponseEntity<Integer> createProduct(@Valid @RequestBody ProductCreateDto productCreateDto){
        return new ResponseEntity<>(productCommandService.createProduct(productCreateDto), HttpStatus.CREATED);
    }

    @PutMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductQueryDto> updateProduct(@PathVariable(value = "id") int id,
                                                         @RequestBody ProductUpdateDto productUpdateDTO) {
        ProductQueryDto productQueryDto = productCommandService.updateProduct(id, productUpdateDTO);
        if (productQueryDto == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        } else {
            return new ResponseEntity<>(productQueryDto, HttpStatus.OK);
        }
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable(value = "id") int id) {
        int result = productCommandService.deleteProduct(id);
        if (result == 1) {
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body(null);
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Product with given Id not found.");
        }
    }
}
