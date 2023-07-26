package com.ask.example.config;

import com.ask.example.controller.resolver.ClientInfoArgumentResolver;
import com.ask.example.server.LoggingInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.List;

@Configuration
public class WebServerConfig implements WebMvcConfigurer {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new MappingJackson2HttpMessageConverter());
        converters.add(new MappingJackson2XmlHttpMessageConverter());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Custom Interceptor 등록
        registry.addInterceptor(new LoggingInterceptor())
                .addPathPatterns("/**");

        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("locale");
        registry.addInterceptor(localeChangeInterceptor)
                .excludePathPatterns("/favicon.ico")
                .addPathPatterns("/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new ClientInfoArgumentResolver());
    }

    @Bean
    public FilterRegistrationBean<CharacterEncodingFilter> defaultCharacterEncodingFilter() {
        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
        encodingFilter.setEncoding("utf-8");
        encodingFilter.setForceEncoding(true);

        FilterRegistrationBean<CharacterEncodingFilter> filterBean = new FilterRegistrationBean<>();
        filterBean.setFilter(encodingFilter);
        filterBean.addInitParameter("paramName", "paramValue");
        filterBean.addUrlPatterns("*");
        filterBean.setOrder(1);
        return filterBean;
    }

}

