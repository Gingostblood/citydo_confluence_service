package cn.gingost.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author:lezzy
 * @Date:2020/7/27 16:09
 */

@Configuration
@EnableWebMvc
public class ConfigurerAdapter implements WebMvcConfigurer {
    @Value("${file.path}")
    private String path;

    @Value("${file.avatar}")
    private String avatar;

    @Bean
    public CorsFilter corsFilter() {
        //注册jar包内 内置的filter等等
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
    //拦截请求访问本地的资源
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String avatarUtl = "file:" + avatar.replace("\\","/");
        String pathUtl = "file:" + path.replace("\\","/");
        registry.addResourceHandler("/avatar/**").addResourceLocations(avatarUtl).setCachePeriod(0);
        registry.addResourceHandler("/file/**").addResourceLocations(pathUtl).setCachePeriod(0);
        registry.addResourceHandler("/**").addResourceLocations("classpath:/META-INF/resources/").setCachePeriod(0);
    }
}
