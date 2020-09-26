package com.trolit.github.grocerystore;

import com.trolit.github.grocerystore.dto.product.ProductQueryDto;
import com.trolit.github.grocerystore.models.Product;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import static com.trolit.github.grocerystore.converters.StringConverters.basicPropertyConverter;

@Configuration
public class AppConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        AddProductCustomMappings(modelMapper);
        modelMapper.addConverter(basicPropertyConverter());
        return modelMapper;
    }

    private void AddProductCustomMappings(ModelMapper modelMapper) {
        modelMapper.typeMap(Product.class, ProductQueryDto.class).addMappings(mapper -> {
           mapper.map(src -> src.getCategory().getName(), (dest, v) -> dest.setCategory((String) v));
           mapper.map(src -> src.getCategory().getId(), (dest, v) -> dest.setCategoryId((Integer) v));
        });
    }

}