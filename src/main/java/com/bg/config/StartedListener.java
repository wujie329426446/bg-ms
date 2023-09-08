package com.bg.config;

import com.bg.config.properties.SwaggerProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * The method executed after the application is started.
 *
 * @author jiewus
 */
@Slf4j
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class StartedListener implements ApplicationListener<ApplicationStartedEvent> {

  private final SwaggerProperties swaggerProperties;

  @Override
  public void onApplicationEvent(@NotNull ApplicationStartedEvent event) {
    // save halo version to database
    this.printStartInfo();
  }

  private void printStartInfo() {
    if (swaggerProperties.isEnable()) {
      log.info(AnsiOutput.toString(AnsiColor.BRIGHT_BLUE, "Bg api Knife4j doc was enabled at ", "/doc.html"));
    }
    log.info(AnsiOutput.toString(AnsiColor.BRIGHT_YELLOW, "Bg has started successfully!"));
  }

}
