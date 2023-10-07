package com.bg.commons.exception;

import com.bg.auth.exception.LoginException;
import com.bg.commons.api.ApiCode;
import com.bg.commons.api.ApiResult;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @ClassName AcfaExceptionHandler
 * @Description acfa异常处理类
 * @Author Chen Zheng
 * @Date 2020/6/19 7:03 PM
 */
@Slf4j
@RestControllerAdvice
public class BusinessExceptionHandler {

  @ExceptionHandler(LoginException.class)
  public ApiResult<?> loginExceptionHandler(LoginException e) {
    log.error(e.getMessage(), e);
    return ApiResult.fail(e.getCode(), e.getMessage());
  }

  @ExceptionHandler(BusinessException.class)
  public ApiResult<?> businessExceptionHandler(BusinessException e) {
    log.error(e.getMessage(), e);
    return ApiResult.fail(e.getCode(), e.getMessage());
  }

  @ExceptionHandler(NullPointerException.class)
  public ApiResult<?> nullPointerExceptionHandler(NullPointerException e) {
    log.error(e.getMessage(), e);
    return ApiResult.fail(ApiCode.FAIL);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ApiResult<?> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
    log.error(e.getMessage(), e);
    BindingResult bindingResult = e.getBindingResult();
    List<ObjectError> allErrors = bindingResult.getAllErrors();
    StringBuilder builder = new StringBuilder();
    allErrors.forEach(x -> {
      builder.append(x.getDefaultMessage());
      builder.append("<br/>");
    });
    return ApiResult.fail(builder.toString());
  }

  @ExceptionHandler(BindException.class)
  public ApiResult<?> bindExceptionHandler(BindException e) {
    log.error(e.getMessage(), e);
    StringBuilder builder = new StringBuilder();
    e.getAllErrors().forEach(x -> {
      builder.append(x.getDefaultMessage());
      builder.append("<br/>");
    });
    return ApiResult.fail(builder.toString());
  }

  @ExceptionHandler(DataAccessException.class)
  public ApiResult<?> dataAccessException(DataAccessException e) {
    log.error(e.getMessage(), e);
    return ApiResult.fail("数据库访问异常:" + e.getMessage());
  }

  @ExceptionHandler(Exception.class)
  public ApiResult<?> exceptionHandler(Exception e) {
    log.error(e.getMessage(), e);
    return ApiResult.fail(e.getMessage());
  }

}
