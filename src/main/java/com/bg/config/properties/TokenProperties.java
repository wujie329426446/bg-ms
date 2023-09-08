package com.bg.config.properties;

import java.util.concurrent.TimeUnit;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * token 配置属性
 *
 * @author jiewus
 */
@Data
@Configuration
@ConfigurationProperties(TokenProperties.TOKEN_PREFIX)
public class TokenProperties {

  public static final String TOKEN_PREFIX = "token";

  /**
   * 令牌自定义标识
   */
  private String header;

  /**
   * 令牌秘钥
   */
  private String secret;

  /**
   * 时间颗粒度
   */
  private TimeUnit timeUnit;

  /**
   * 令牌有效期
   */
  private int expireTime;

  public long getExpireTimeLong() {
    return TimeUnit.MILLISECONDS.convert(this.getExpireTime(), this.getTimeUnit());
  }

}
