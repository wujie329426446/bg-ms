package com.bg.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: jiewus
 * @Description: 请求前缀（可配置多个）
 * @Date: 2023/4/4 12:00
 */
@Data
@Component
@ConfigurationProperties(prefix = "api.prefix")
public class ApiPrefixProperties {

  private String admin;

}
