package com.bg.commons.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ProjectName: bg-ms
 * @Author: jiewus
 * @Description: 登录类型枚举类
 * @Date: 2023/9/5 14:49
 */
@Getter
@AllArgsConstructor
public enum LoginTypeEnum implements BaseEnum {

  USER_NAME("USER_NAME", "用户名登录"),
  EMAIL("EMAIL", "邮箱登录"),
  PHONE("PHONE", "手机登录");

  private String code;

  private String desc;

}
