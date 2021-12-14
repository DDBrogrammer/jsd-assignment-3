package com.example.jsd_assignment_3.configure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ResourceConfig implements WebMvcConfigurer {
    @Value("${config.upload_folder}")
    String UPLOAD_FOLDER;
//lag thế @@
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("uploads/**")
                .addResourceLocations("file:"+UPLOAD_FOLDER);
    }
}
