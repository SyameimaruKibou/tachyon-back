package webserver.config;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import webserver.filter.TokenInterceptor;

import java.util.List;

@Slf4j
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    private final TokenInterceptor tokenInterceptor;

    @Value("#{'${interceptor-config.limit-path}'.replaceAll(' ', '').split(',')}")
    private List<String> limitPathList;

    @Value("#{'${interceptor-config.white-list}'.replaceAll(' ', '').split(',')}")
    private List<String> whiteList;

    //构造方法
    public InterceptorConfig(TokenInterceptor tokenInterceptor) {
        this.tokenInterceptor = tokenInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(tokenInterceptor);
        log.info("limitPathList:" + JSON.toJSONString(limitPathList));
        log.info("whiteList:" + JSON.toJSONString(whiteList));
        registration.addPathPatterns(limitPathList);
        registration.excludePathPatterns(whiteList);
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
