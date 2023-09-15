package com.bg.system.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 是否内嵌
 *
 * @author jiewus
 */
@Getter
@AllArgsConstructor
public enum FrameEnum {
  YES(0, "内嵌"),

  NO(1, "不内嵌");

  @JsonValue
  @EnumValue
  private Integer code;
  private String desc;

}
