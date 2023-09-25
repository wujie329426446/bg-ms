package com.bg.config.properties;

import com.bg.commons.constant.CommonConstant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author jiewus
 **/
@Data
@ConfigurationProperties(prefix = "bg.log-aop")
public class LogAopProperties {

  /**
   * 是否启用
   */
  private boolean enable = true;

  /**
   * 是否打印日志
   */
  private boolean printLog = true;

  /**
   * 登录路径
   */
  private List<String> loginUrls;

  /**
   * 是否打印请求头日志
   */
  private boolean printHeadLog = false;

  /**
   * 排除的路径
   */
  private List<String> excludePaths;


  public void setExcludePaths(List<String> excludePaths) {
    if (CollectionUtils.isNotEmpty(excludePaths)) {
      List<String> excludePathList = new ArrayList<>();
      for (String excludePath : excludePaths) {
        if (StringUtils.isBlank(excludePath)) {
          continue;
        }
        if (excludePath.contains(CommonConstant.COMMA)) {
          String[] excludeArray = excludePath.split(CommonConstant.COMMA);
          excludePathList.addAll(Arrays.asList(excludeArray));
        } else {
          excludePathList.add(excludePath);
        }
      }
      this.excludePaths = excludePathList;
    } else {
      this.excludePaths = excludePaths;
    }
  }

  public void setLoginUrls(List<String> loginUrls) {
    if (CollectionUtils.isNotEmpty(loginUrls)) {
      List<String> loginUrlList = new ArrayList<>();
      for (String loginUrl : loginUrls) {
        if (StringUtils.isBlank(loginUrl)) {
          continue;
        }
        if (loginUrl.contains(CommonConstant.COMMA)) {
          String[] excludeArray = loginUrl.split(CommonConstant.COMMA);
          loginUrlList.addAll(Arrays.asList(excludeArray));
        } else {
          loginUrlList.add(loginUrl);
        }
      }
      this.loginUrls = loginUrlList;
    } else {
      this.loginUrls = loginUrls;
    }
  }

}
