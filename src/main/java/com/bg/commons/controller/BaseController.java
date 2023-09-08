package com.bg.commons.controller;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bg.commons.entity.BaseEntity;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Controller父类
 *
 * @author haseochen
 */

@Slf4j
public class BaseController<T extends BaseEntity, S extends IService<T>, R> {

  @Autowired
  protected S baseService;
  private Class<T> entityClazz;
  private Class<R> requestClazz;

  public BaseController() {
    try {
      Type genType = this.getClass().getGenericSuperclass();
      Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
      this.entityClazz = (Class) params[0];
      this.requestClazz = (Class) params[2];
    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }


}