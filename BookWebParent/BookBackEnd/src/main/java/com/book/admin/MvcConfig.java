package com.book.admin;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//Config photos
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        String dirName = "user-photos";
//        Path userPhotosDir = Paths.get(dirName);
//        String userPhotosPath = userPhotosDir.toFile().getAbsolutePath();
//        registry.addResourceHandler("/" + dirName + "/**")
//                .addResourceLocations("file:" + userPhotosPath + "/");
//
//        String categoryImagesDirName = "category-images";
//        Path categoryImagesDir = Paths.get(categoryImagesDirName);
//        String categoryImagesPath = categoryImagesDir.toFile().getAbsolutePath();
//        registry.addResourceHandler("/" + categoryImagesDirName + "/**")
//                .addResourceLocations("file:" + categoryImagesPath + "/");
//    }

    private void exposeDirectory(String pathPattern, ResourceHandlerRegistry registry) {
        Path path = Paths.get(pathPattern);
        String absolutePath = path.toFile().getAbsolutePath();

        String logicalPath = pathPattern.replace("../", "") + "/**";

        registry.addResourceHandler(logicalPath)
                .addResourceLocations("file:" + absolutePath + "/");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        exposeDirectory("user-photos", registry);
        exposeDirectory("../category-images", registry);
        exposeDirectory("../product-images", registry);
        exposeDirectory("../site-logo", registry);

    }

}
