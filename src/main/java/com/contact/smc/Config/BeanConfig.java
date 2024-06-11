package com.contact.smc.Config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class BeanConfig {

    @Value("${cloudinary.cloud_name}")
    public String cloudName;

    @Value("${cloudinary.api_key}")
    public String apiKey;

    @Value("${cloudinary.api_secret}")
    public String apiSecret;

    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    Cloudinary cloudinary() {
        return new Cloudinary(
                ObjectUtils.asMap(
                        "cloud_name", cloudName,
                        "api_key", apiKey,
                        "api_secret", apiSecret
                )
        );
    }


}
