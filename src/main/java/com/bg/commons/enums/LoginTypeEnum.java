package com.bg.commons.enums;

import io.swagger.v3.oas.annotations.media.Schema;
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

  USER_NAME("USER_NAME", "用户名"),
  EMAIL("EMAIL", "邮箱"),
  PHONE("PHONE", "手机");

  private String code;

  private String desc;

}
