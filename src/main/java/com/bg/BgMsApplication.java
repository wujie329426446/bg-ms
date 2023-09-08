package com.bg;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * Spring boot
 *
 * @author jiewus
 */
@EnableAsync
@EnableCaching
@EnableTransactionManagement
@MapperScan({"com.bg.**.mapper"})
@EnableScheduling
@EnableConfigurationProperties
@ServletComponentScan
@SpringBootApplication(scanBasePackages = {"com.bg"})
public class BgMsApplication {

  public static void main(String[] args) {
    SpringApplication.run(BgMsApplication.class, args);
  }
}
