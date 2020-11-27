package com.trolit.github.grocerystore.controllers.product;

import com.trolit.github.grocerystore.plugins.ApiDescription;
import com.trolit.github.grocerystore.dto.product.*;
import com.trolit.github.grocerystore.services.product.ProductCommandService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
    @ApiOperation(value = "Creates product")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Product resource created"),
            @ApiResponse(code = 400, message = "Product not created (category not found)")})
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<Integer> createProduct(@Valid @RequestBody ProductCreateDto productCreateDto) {
        int result = productCommandService.createProduct(productCreateDto);
        if (result == 0) {
            return ResponseEntity
                    .badRequest()
                    .build();
        } else {
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        }
    }

    @PutMapping(path = "{id}")
    @ApiOperation(value = "Updates product")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Product updated"),
            @ApiResponse(code = 404, message = "Product or given category not found")})
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<ProductQueryDto> updateProduct(@PathVariable(value = "id") int id,
                                                         @RequestBody ProductUpdateDto productUpdateDTO) {
        ProductQueryDto productQueryDto = productCommandService.updateProduct(id, productUpdateDTO);
        if (productQueryDto == null) {
            return ResponseEntity
                    .notFound()
                    .build();
        } else {
            return new ResponseEntity<>(productQueryDto, HttpStatus.OK);
        }
    }

    @DeleteMapping(path = "{id}")
    @ApiOperation(value = "Deletes product")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Product deleted"),
            @ApiResponse(code = 404, message = "Product not found")})
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteProduct(@PathVariable(value = "id") int id) {
        int result = productCommandService.deleteProduct(id);
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

    @PatchMapping(path = "order")
    @ApiOperation(value = "Updates given products stocks on successful order")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Products stocks updated"),
            @ApiResponse(code = 400, message = "No products given"),
            @ApiResponse(code = 400, message = "One of the product does not exist or it's stock is not enough to process order")})
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity<String> buyProducts(@RequestBody ProductsOrderDto productsOrderDto) {
        if (productsOrderDto.getOrder().size() <= 0) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Order aborted. No products given.");
        }
        int result = productCommandService.buyProducts(productsOrderDto.getOrder());
        if (result == 1) {
            return ResponseEntity
                    .noContent()
                    .build();
        } else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Order cancelled. One of the products does not exist or it's stock is not " +
                            "big enough to complete order.");
        }
    }

    @PatchMapping(path = "{id}/stock")
    @ApiOperation(value = "Sets only product's stock")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Product stock updated"),
            @ApiResponse(code = 400, message = "Product not found")})
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> partialUpdateProductStock(@PathVariable(value = "id") int id,
                                                            @Valid @RequestBody ProductStockOnlyDto productStockOnlyDto) {
        int result = productCommandService.setProductStock(id, productStockOnlyDto);
        if (result == 1) {
            return ResponseEntity
                    .noContent()
                    .build();
        } else {
            return ResponseEntity
                    .badRequest()
                    .build();
        }
    }

    @PatchMapping(path = "{id}/price")
    @ApiOperation(value = "Changes product price by percentage")
    @ApiDescription(value = "notes/changeProductPriceDesc.md")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Product price updated"),
            @ApiResponse(code = 400, message = "Product not found")})
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<UpdatedProductPriceDto> changeProductPriceByPercentage(@PathVariable(value = "id") int id,
                                                                 @Valid @RequestBody ProductPriceChangeDto productPriceChangeDto) {
        UpdatedProductPriceDto result = productCommandService.changeProductPriceByPercentage(id, productPriceChangeDto);
        if (result.getPrice() != null) {
            return ResponseEntity
                    .ok(result);
        } else {
            return ResponseEntity
                    .badRequest()
                    .build();
        }
    }
}
