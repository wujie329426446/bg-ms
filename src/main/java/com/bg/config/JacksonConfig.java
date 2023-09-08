package com.bg.config;

import cn.hutool.core.date.DatePattern;
import com.bg.framework.jackson.deserializer.JacksonDateDeserializer;
import com.bg.framework.jackson.deserializer.JacksonStringDeserializer;
import com.bg.framework.jackson.serializer.JacksonBigDecimalSerializer;
import com.bg.framework.jackson.serializer.JacksonStringSerializer;
import com.bg.framework.xss.XssJacksonDeserializer;
import com.bg.framework.xss.XssJacksonSerializer;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author jiewus
 **/
@Configuration
public class JacksonConfig {

  @Value("${bg.xss.enable}")
  private boolean enableXss;

  @Bean
  public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
    return builder -> {
      builder.locale(Locale.CHINA);
      builder.timeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));
      builder.simpleDateFormat(DatePattern.NORM_DATETIME_PATTERN);
      // 反序列化(处理请求参数)
      // 去掉请求参数中字符串左右两边的空格
      builder.deserializerByType(String.class, JacksonStringDeserializer.INSTANCE);
      builder.deserializerByType(Date.class, JacksonDateDeserializer.INSTANCE);
      builder.deserializerByType(LocalDateTime.class, JacksonDateDeserializer.INSTANCE);
      // 序列化(处理响应结果)
      // 避免long类型精度丢失，将long类型序列化成字符串
      builder.serializerByType(Long.class, ToStringSerializer.instance);
      // 去掉响应结果中字符串左右两边的空格
      builder.serializerByType(String.class, JacksonStringSerializer.INSTANCE);
      builder.serializerByType(BigDecimal.class, new JacksonBigDecimalSerializer());
      // 系列化枚举值为前端返回值
      builder.featuresToEnable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
      // XSS序列化
      if (enableXss) {
        builder.serializerByType(String.class, new XssJacksonSerializer());
        builder.deserializerByType(String.class, new XssJacksonDeserializer());
      }

    };
  }

}
