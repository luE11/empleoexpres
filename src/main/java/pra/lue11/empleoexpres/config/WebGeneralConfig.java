package pra.lue11.empleoexpres.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

/**
 * @author luE11 on 31/07/23
 */
@Configuration
public class WebGeneralConfig implements WebMvcConfigurer {

    /**
     * Serve uploaded images from project root directory
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/files-upload/**")
                .addResourceLocations("file:files-upload/");
    }

}
