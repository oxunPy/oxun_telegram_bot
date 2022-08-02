package com.example.telegramterminaluz.configuration.resources;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;

@Configuration
@PropertySources({
        @PropertySource(name = "application", value = "classpath:/application.properties", encoding = "UTF-8", ignoreResourceNotFound = true),
        @PropertySource(name = "dev", value = "classpath:/application-dev.properties", encoding = "UTF-8", ignoreResourceNotFound = true),
        @PropertySource(name = "messages", value = "classpath:/application-messages.properties", encoding = "UTF-8", ignoreResourceNotFound = true)
})
public class ApplicationProperties {
    @Resource
    private Environment environment;

    public String getProperty(String name){
        return environment.getProperty(name).trim();
    }

    public Integer getCode(String name){
        return Integer.parseInt(environment.getProperty(name).trim());
    }


}
