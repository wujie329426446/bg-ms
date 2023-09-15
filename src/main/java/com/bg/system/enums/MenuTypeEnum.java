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
public enum MenuTypeEnum implements BaseEnum {

  ONE(1, "目录"),

  TWO(2, "菜单"),

  THREE(3, "按钮");

  @JsonValue
  @EnumValue
  private Integer code;
  private String desc;

}
