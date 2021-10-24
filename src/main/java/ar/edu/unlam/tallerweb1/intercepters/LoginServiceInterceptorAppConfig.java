package ar.edu.unlam.tallerweb1.intercepters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Component
public class LoginServiceInterceptorAppConfig extends WebMvcConfigurerAdapter {

    @Autowired
    LoginServiceInterceptor loginServiceInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginServiceInterceptor);
    }
}