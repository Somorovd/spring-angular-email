package com.dsomorov.email.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer
{
  @Override
  public void addCorsMappings(CorsRegistry corsRegistry)
  {
    corsRegistry.addMapping("/**")
      .allowedOrigins("*")
      .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
      .allowedHeaders("*")
      .maxAge(3600)
    ;
  }
  
  @Override
  public void addViewControllers(ViewControllerRegistry registry)
  {
    registry.addViewController("/{spring:\\w+}")
            .setViewName("forward:/index.html");
    registry.addViewController("/*/{spring:\\w+}")
            .setViewName("forward:/index.html");
  }
}
