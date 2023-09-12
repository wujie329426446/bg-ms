package com.bg.config;

import com.bg.commons.constant.UploadsPrefix;
import com.bg.config.properties.ApiPrefixProperties;
import com.bg.config.properties.BgProperties;
import com.bg.config.properties.XssProperties;
import com.bg.framework.filter.JsonRequestBodyFilter;
import com.bg.framework.filter.TraceIdLogFilter;
import com.bg.framework.prefix.AdminApiRestController;
import com.bg.framework.xss.XssFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebMvc 配置
 *
 * @author jiewus
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  private final BgProperties bgProperties;

  private final XssProperties xssProperties;

  private final ApiPrefixProperties apiPrefixProperties;

  public WebMvcConfig(BgProperties bgProperties, XssProperties xssProperties, ApiPrefixProperties apiPrefixProperties) {
    this.bgProperties = bgProperties;
    this.xssProperties = xssProperties;
    this.apiPrefixProperties = apiPrefixProperties;
  }

  @Override
  public void configurePathMatch(PathMatchConfigurer configurer) {
    if (!StringUtils.isEmpty(apiPrefixProperties.getAdmin())) {
      configurer.addPathPrefix(apiPrefixProperties.getAdmin(), c -> c.isAnnotationPresent(AdminApiRestController.class));
    }
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler(UploadsPrefix.UPLOADS + "/**").addResourceLocations("file:" + bgProperties.getUploadFolder() + "/");
  }

  @Bean
  public FilterRegistrationBean traceIdLogFilter() {
    FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
    TraceIdLogFilter traceIdLogFilter = new TraceIdLogFilter();
    filterRegistrationBean.setFilter(traceIdLogFilter);
    filterRegistrationBean.setUrlPatterns(Arrays.asList("/*"));
    return filterRegistrationBean;
  }

  @Bean
  public FilterRegistrationBean jsonRequestBodyFilter() {
    FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
    JsonRequestBodyFilter jsonRequestBodyFilter = new JsonRequestBodyFilter();
    filterRegistrationBean.setFilter(jsonRequestBodyFilter);
    List<String> urls = new ArrayList<>();
    urls.add("/*");
    filterRegistrationBean.setUrlPatterns(urls);
    return filterRegistrationBean;
  }

  /**
   * XssFilter配置
   *
   * @return
   */
  @Bean
  public FilterRegistrationBean xssFilter() {
    FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
    filterRegistrationBean.setFilter(new XssFilter());
    filterRegistrationBean.setEnabled(xssProperties.isEnable());
    filterRegistrationBean.addUrlPatterns(xssProperties.getUrlPatterns());
    filterRegistrationBean.setOrder(xssProperties.getOrder());
    filterRegistrationBean.setAsyncSupported(xssProperties.isAsync());
    return filterRegistrationBean;
  }
}
