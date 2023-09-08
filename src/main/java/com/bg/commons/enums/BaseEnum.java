package com.bg.commons.enums;

/**
 * 枚举类型父接口
 */
public interface BaseEnum {

  /**
   * 获取枚举标识
   *
   * @return
   */
  <T> T getCode();

  /**
   * 获取枚举描述
   *
   * @return
   */
  <T> T getDesc();

}
