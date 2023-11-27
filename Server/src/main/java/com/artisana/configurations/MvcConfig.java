package com.artisana.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.stripe.Stripe;

import jakarta.annotation.PostConstruct;

@Configuration
public class MvcConfig implements WebMvcConfigurer{
	  @Override
	    public void addResourceHandlers(ResourceHandlerRegistry registry) {
	        registry.addResourceHandler("/uploads/**").addResourceLocations("file:uploads/");
	    }
	  
//	   @Value("${stripe.api.secretKey}")
//	    private String secretKey;
//	    @PostConstruct
//	    public void  initSecretKey(){
//	        Stripe.apiKey = secretKey;
//	    }
}
