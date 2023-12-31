package com.bg.commons.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author jiewus
 **/
@Getter
@AllArgsConstructor
public enum StatusEnum implements BaseEnum {

  ENABLE(1, "启用"),

  DISABLE(0, "禁用");

  @JsonValue
  @EnumValue
  private Integer code;

  private String desc;

}
