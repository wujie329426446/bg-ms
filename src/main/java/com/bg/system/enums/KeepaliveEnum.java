package com.bg.system.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum KeepaliveEnum {

  NO(0, "不缓存"),


  YES(1, "缓存");

  @JsonValue
  @EnumValue
  private Integer code;
  private String desc;

}
