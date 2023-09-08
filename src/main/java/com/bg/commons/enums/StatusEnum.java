package com.bg.commons.enums;

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

  private Integer code;

  private String desc;

}
