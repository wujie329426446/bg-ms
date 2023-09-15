package com.bg.system.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.bg.commons.enums.BaseEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 层级枚举
 *
 * @author jiewus
 **/
@Getter
@AllArgsConstructor
public enum MenuLevelEnum implements BaseEnum {

  ONE(1, "一级菜单"),

  TWO(2, "二级菜单"),

  THREE(3, "功能菜单");

  @JsonValue
  @EnumValue
  private Integer code;
  private String desc;

}
