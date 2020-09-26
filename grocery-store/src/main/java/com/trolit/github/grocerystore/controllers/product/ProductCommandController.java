package com.trolit.github.grocerystore.controllers.product;

import com.trolit.github.grocerystore.dto.product.*;
import com.trolit.github.grocerystore.services.product.ProductCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        return new ResponseEntity<>(
                productCommandService.createProduct(productCreateDto), HttpStatus.CREATED);
    }

    @PutMapping(path = "{id}")
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

    @PostMapping(path = "order")
    public ResponseEntity<String> buyProducts(@RequestBody ProductsOrderDto productsOrderDto) {
        if (productsOrderDto.getOrder().size() <= 0) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Order aborted. No products given.");
        }
        int result = productCommandService.buyProducts(productsOrderDto.getOrder());
        if (result == 1) {
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body(null);
        } else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Order cancelled. One of the product does not exist or it's stock is not " +
                            "big enough to complete order.");
        }
    }

    @PatchMapping(path = "{id}")
    public ResponseEntity<String> partialUpdateProductStock(@PathVariable(value = "id") int id,
                                                            @Valid @RequestBody ProductStockOnlyDto productStockOnlyDto) {
        int result = productCommandService.setProductStock(id, productStockOnlyDto);
        if (result == 1) {
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body(null);
        } else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Product not found. Setting product stock failed.");
        }
    }

    @PostMapping(path = "{id}")
    public ResponseEntity<String> changeProductPriceByPercentage(@PathVariable(value = "id") int id,
                                                                 @Valid @RequestBody ProductPriceChangeDto productPriceChangeDto) {
        int result = productCommandService.changeProductPriceByPercentage(id, productPriceChangeDto);
        if (result == 1) {
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body(null);
        } else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Changing product price by percentage failed.");
        }
    }
}
