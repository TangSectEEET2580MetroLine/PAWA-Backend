package rmit.edu.vn.hcmc_metro.security_config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                // allow your front-end origin
                .allowedOrigins("http://localhost:3001")
                // include OPTIONS for preflight
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                // accept all headers (or list specific ones)
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
