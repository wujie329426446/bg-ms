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
public enum DeletedEnum implements BaseEnum {

  DELETED(1, "已删除"),

  NORMAL(0, "未删除");

  @JsonValue
  @EnumValue
  private Integer code;

  private String desc;

}
