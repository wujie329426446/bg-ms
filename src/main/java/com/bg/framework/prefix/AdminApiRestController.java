package com.bg.framework.prefix;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: jiewus
 * @Description: 前缀配置Controller
 * @Date: 2023/4/4 12:04
 */
@RestController
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AdminApiRestController {

}
