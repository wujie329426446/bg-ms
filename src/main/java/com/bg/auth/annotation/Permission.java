package com.bg.auth.annotation;

import java.lang.annotation.*;

/**
 * @author jiewus
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Permission {

  /**
   * 权限编码
   *
   * @return
   */
  String value();

  /**
   * 角色编码
   *
   * @return
   */
  String role() default "";

  /**
   * 角色编码数组
   *
   * @return
   */
  String[] roles() default {};

}
