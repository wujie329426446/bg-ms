package com.bg.config.properties;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

/**
 * @author jiewus
 */
@Slf4j
@Data
@Component
public class StaticProperties {

  public static String INFO_PROJECT_VERSION = "";

  @Value("${info.project-version}")
  private String infoProjectVersion;

  @PostConstruct
  public void init() {
    INFO_PROJECT_VERSION = this.infoProjectVersion;
    log.debug("INFO_PROJECT_VERSION:" + INFO_PROJECT_VERSION);
  }

}
