package com.bg.auth.controller;

import com.bg.auth.service.impl.AuthServiceImpl;
import com.bg.commons.api.ApiResult;
import com.bg.commons.constant.LoginConstant;
import com.bg.commons.core.validator.groups.login.EmailLogin;
import com.bg.commons.core.validator.groups.login.PhoneLogin;
import com.bg.commons.core.validator.groups.login.UsernamePasswordLogin;
import com.bg.commons.enums.LoginTypeEnum;
import com.bg.commons.model.LoginModel;
import com.bg.commons.model.UserModel;
import com.bg.commons.utils.SecurityUtil;
import com.bg.framework.prefix.AdminApiRestController;
import com.bg.system.service.ISysMenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 登录控制器
 *
 * @author jiewus
 */
@Slf4j
@AdminApiRestController
@Tag(name = "系统登录API")
@RequestMapping()
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AuthController {

  private final ISysMenuService sysMenuService;
  private final AuthServiceImpl authServiceImpl;


  @GetMapping("/verify")
  @Operation(
      summary = "账号登录-获取验证码",
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "Success",
              content = @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(
                      title = "ApiResult、Map组合模型",
                      description = "返回实体，ApiResult内data为Map对象",
                      anyOf = {ApiResult.class, Map.class}
                  )
              )
          )
      }
  )
  public ApiResult<Map<String, String>> verify() throws IOException {
    Map<String, String> result = authServiceImpl.verify();
    return ApiResult.success(result);
  }

  @GetMapping("/emailVerify")
  @Operation(
      summary = "邮箱登录-获取验证码",
      parameters = {
          @Parameter(
              description = "邮箱账号",
              in = ParameterIn.QUERY,
              name = "email",
              required = true
          )
      },
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "Success",
              content = @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(
                      title = "ApiResult、Map组合模型",
                      description = "返回实体，ApiResult内data为Map对象",
                      anyOf = {ApiResult.class, Map.class}
                  )
              )
          )
      }
  )
  public ApiResult<Map<String, String>> emailVerify(@RequestParam String email) {
    Map<String, String> result = authServiceImpl.emailVerify(email);
    return ApiResult.success(result);
  }

  @GetMapping("/phoneVerify")
  @Operation(
      summary = "手机登录-获取验证码",
      parameters = {
          @Parameter(
              description = "手机账号",
              in = ParameterIn.QUERY,
              name = "phone",
              required = true
          )
      },
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "Success",
              content = @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(
                      title = "ApiResult、Map组合模型",
                      description = "返回实体，ApiResult内data为Map对象",
                      anyOf = {ApiResult.class, Map.class}
                  )
              )
          )
      }
  )
  public ApiResult<Map<String, String>> phoneVerify(@RequestParam String phone) {
    Map<String, String> result = authServiceImpl.phoneVerify(phone);
    return ApiResult.success(result);
  }

  @PostMapping("/login")
  @Operation(
      summary = "账号登录-登录",
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
  public ApiResult<String> accountLogin(@Validated(UsernamePasswordLogin.class) @RequestBody LoginModel loginModel) {
    loginModel.setLoginType(LoginTypeEnum.USER_NAME);
    String token = authServiceImpl.login(loginModel);
    return ApiResult.success(Map.of("token", token));
  }

  @PostMapping("/emailLogin")
  @Operation(
      summary = "邮箱登录-登录",
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
  public ApiResult<String> emailLogin(@Validated(EmailLogin.class) @RequestBody LoginModel loginModel) {
    loginModel.setLoginType(LoginTypeEnum.EMAIL);
    String token = authServiceImpl.login(loginModel);
    return ApiResult.success(Map.of("token", token));
  }

  @PostMapping("/phoneLogin")
  @Operation(
      summary = "手机登录-登录",
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
  public ApiResult<String> phoneLogin(@Validated(PhoneLogin.class) @RequestBody LoginModel loginModel) {
    loginModel.setLoginType(LoginTypeEnum.PHONE);
    String token = authServiceImpl.login(loginModel);
    return ApiResult.success(Map.of("token", token));
  }

  @GetMapping("/getUserInfo")
  @Operation(
      summary = "获取系统登录用户信息",
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "Success",
              content = @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(
                      title = "ApiResult、UserModel组合模型",
                      description = "返回实体，ApiResult内data为UserModel对象",
                      anyOf = {ApiResult.class, UserModel.class}
                  )
              )
          )
      },
      security = {@SecurityRequirement(name = LoginConstant.BG_HEADER)}
  )
  public ApiResult<UserModel> getSysUser() {
    UserModel userModel = SecurityUtil.getUserModel();
    return ApiResult.success(userModel);
  }

  @GetMapping("/getPermCode")
  @Operation(
      summary = "获取当前用户权限信息",
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "Success",
              content = @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(
                      title = "ApiResult、String集合组合模型",
                      description = "返回实体，ApiResult内data为String集合",
                      anyOf = {ApiResult.class, String.class}
                  )
              )
          )
      },
      security = {@SecurityRequirement(name = LoginConstant.BG_HEADER)}
  )
  public ApiResult<Set<String>> getPermCode() {
    return ApiResult.success(sysMenuService.getCodesByUser(SecurityUtil.getUser().getUserId()));
  }

}
