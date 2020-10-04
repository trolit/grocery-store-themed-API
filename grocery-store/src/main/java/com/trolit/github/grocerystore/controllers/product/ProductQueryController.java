package com.trolit.github.grocerystore.controllers.product;

import com.trolit.github.grocerystore.dto.product.ProductQueryDto;
import com.trolit.github.grocerystore.plugins.ApiDescription;
import com.trolit.github.grocerystore.services.product.ProductQueryService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/v1/products")
@RestController
public class ProductQueryController {

    private final ProductQueryService productQueryService;

    @Autowired
    public ProductQueryController(ProductQueryService productQueryService) {
        this.productQueryService = productQueryService;
    }

    @GetMapping
    @ApiOperation(value = "Returns products (can be filtered)")
    @ApiDescription(value = "notes/getAllproductsDesc.md")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returned at least one product"),
            @ApiResponse(code = 204, message = "Request successful but no products found"),
            @ApiResponse(code = 400, message = "Filter percentagePriceDiff is not available")})
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<List<ProductQueryDto>> getAllProducts(
            @RequestParam(value = "search", required = false) String search) {
        if (search != null && search.contains("percentagePriceDiff")) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }

        List<ProductQueryDto> products = productQueryService.getAllProducts(search);
        if (products.size() <= 0) {
            return ResponseEntity
                    .noContent()
                    .build();
        } else {
            return new ResponseEntity<>(products, HttpStatus.OK);
        }
    }

    @GetMapping(path = "{id}")
    @ApiOperation(value = "Returns product within given id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Product returned"),
            @ApiResponse(code = 404, message = "Product not found")})
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<ProductQueryDto> getProduct(@PathVariable(value = "id") int id){
        ProductQueryDto product = productQueryService.getProduct(id);
        if (product == null) {
            return ResponseEntity
                    .notFound()
                    .build();
        } else {
            return new ResponseEntity<>(product, HttpStatus.OK);
        }
    }
}
