package com.bg.auth.controller;

import com.bg.auth.service.LoginService;
import com.bg.commons.api.ApiResult;
import com.bg.commons.model.LoginModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录控制器
 *
 * @author jiewus
 */
@Slf4j
@RestController
@Tag(name = "系统登录API")
@RequestMapping("/v1/api/admin")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class LoginController {

  private final LoginService loginService;

  @PostMapping("/login")
  @Operation(
      summary = "登录",
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "请求体描述",
          required = true,
          content = {
              @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = LoginModel.class)
              )
          }
      )
  )
  public ApiResult login(@Validated @RequestBody LoginModel loginModel) {
    String token = loginService.login(loginModel);
    return ApiResult.success(Map.of("token", token));
  }

}
