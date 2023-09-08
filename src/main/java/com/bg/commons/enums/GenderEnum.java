package com.bg.commons.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ProjectName: bg-ms
 * @Author: jiewus
 * @Description: 性别枚举类
 * @Date: 2023/9/5 15:33
 */
@Getter
@AllArgsConstructor
public enum GenderEnum implements BaseEnum {

  SECRECY(0, "保密"),

  MALE(1, "男"),

  FEMALE(2, "女");

  @JsonValue
  @EnumValue
  private Integer code;

  private String desc;

}
