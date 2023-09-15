package com.bg.system.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * 是否外链
 *
 * @author jiewus
 */
@Getter
@AllArgsConstructor
public enum LinkExternalEnum {

  YES(1, "是"),
  NO(0, "否");

  @JsonValue
  @EnumValue
  private Integer code;
  private String desc;

}
