package com.example.SocialNetwork.config.cloudinary;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryStorageConfig {
    @Value("${CLOUDINARY_API_SECRET}")
    private String api_secret;
    @Value("${CLOUDINARY_API_KEY}")
    private String api_key;

    @Value("${CLOUDINARY_NAME}")
    private String name;

    @Bean
    public Cloudinary cloudinaryClient() {
        Map<String, String> valuesMap = new HashMap<>();
        valuesMap.put("cloud_name", name);
        valuesMap.put("api_key", api_key);
        valuesMap.put("api_secret", api_secret);
        return new Cloudinary(valuesMap);
    }
}
