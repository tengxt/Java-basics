package com.tengxt.crowd.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "upload")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadFileConfig {
    private String location;
    private String resourceHandler;
}
