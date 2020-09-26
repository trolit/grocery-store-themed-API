package com.trolit.github.grocerystore.plugins;

import com.google.common.io.Resources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.OperationBuilderPlugin;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.spring.web.DescriptionResolver;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

// https://stackoverflow.com/questions/58998687/swagger-read-documentation-from-properties-file

@Component
public class ApiDescriptionPlugin implements OperationBuilderPlugin {

    private final DescriptionResolver resolver;

    @Autowired
    public ApiDescriptionPlugin(DescriptionResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public void apply(OperationContext context) {

        Optional<ApiDescription> descOptional = context.findAnnotation(ApiDescription.class);
        boolean hasText = descOptional.isPresent() && StringUtils.hasText(descOptional.get().value());
        if(!hasText) {
            return;
        }

        final String file = descOptional.get().value();
        final URL url = Resources.getResource(file);

        String description;
        try {
            description = Resources.toString(url, StandardCharsets.UTF_8);
        } catch(IOException e) {
            e.printStackTrace();
            description = String.format("Markdown file %s not loaded", file);
        }
        context.operationBuilder().notes(resolver.resolve(description));
    }

    @Override
    public boolean supports(DocumentationType type) {
        return true;
    }

}