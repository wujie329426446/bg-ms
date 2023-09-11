package com.bg.commons.exception;

import com.bg.commons.api.ApiCode;
import com.bg.commons.api.ApiResult;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName ExceptionController
 * @Description 异常控制器
 * @Author Chen Zheng
 * @Date 2020/6/21 1:31 AM
 */
@Hidden
@RestController
@RequestMapping("error")
public class ExceptionController {

  @RequestMapping(value = "401", method = {RequestMethod.GET, RequestMethod.POST,
      RequestMethod.DELETE})
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public ApiResult<?> unauthorized() {
    return ApiResult.fail(ApiCode.TOKEN_EXCEPTION);
  }

  @RequestMapping(value = "404", method = {RequestMethod.GET, RequestMethod.POST,
      RequestMethod.DELETE})
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ApiResult<?> notFound() {
    return ApiResult.fail(ApiCode.NOT_FOUND);
  }

  @RequestMapping(value = "500", method = {RequestMethod.GET, RequestMethod.POST,
      RequestMethod.DELETE})
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ApiResult<?> internalServerError() {
    return ApiResult.fail(ApiCode.FAIL);
  }

}
