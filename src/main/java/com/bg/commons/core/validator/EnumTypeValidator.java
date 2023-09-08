package com.bg.commons.core.validator;

import com.bg.commons.core.validator.constraints.EnumType;
import com.bg.commons.enums.BaseEnum;
import com.bg.commons.exception.BusinessException;
import com.bg.commons.utils.BaseEnumUtil;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * 自定义系统内的枚举验证注解实现类
 */
public class EnumTypeValidator implements ConstraintValidator<EnumType, Integer> {

  private Class<? extends BaseEnum> baseEnum;

  @Override
  public void initialize(EnumType parameters) {
    baseEnum = parameters.type();
    if (baseEnum == null) {
      throw new BusinessException("请传入枚举类型类");
    }
  }

  @Override
  public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
    if (value == null) {
      return true;
    }
    return BaseEnumUtil.exists(baseEnum, value);
  }
}
