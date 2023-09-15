package com.bg.framework.annotation;


import com.bg.commons.enums.LogTypeEnum;

/**
 * @author jiewus
 * @date 2022/8/3
 **/
public @interface Log {

  /**
   * 描述
   *
   * @return
   */
  String value() default "";


  LogTypeEnum type() default LogTypeEnum.OTHER;

}
