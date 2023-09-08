package com.bg.auth.controller;

import com.bg.auth.service.LoginService;
import com.bg.commons.api.ApiResult;
import com.bg.commons.constant.LoginConstant;
import com.bg.commons.model.LoginModel;
import com.bg.commons.utils.SecurityUtil;
import com.bg.commons.vo.RoleInfoVO;
import com.bg.system.service.SysUserService;
import com.bg.system.vo.RouteItemVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/v1/api/admin/auth")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class LoginController {

  private final LoginService loginService;
  private final SysUserService sysUserService;

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
  public ApiResult login(@Valid @RequestBody LoginModel loginModel) {
    String token = loginService.login(loginModel);
    return ApiResult.success(Map.of("token", token));
  }

  @GetMapping("/getMenuList")
  @Operation(
      summary = "获取菜单集合",
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "Success",
              content = @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(
                      title = "ApiResult和RouteItemVO组合模型",
                      description = "返回实体，ApiResult内data为RouteItemVO集合",
                      implementation = RouteItemVO.class
                  )
              )
          )
      },
      security = {@SecurityRequirement(name = LoginConstant.BG_HEADER)}
  )
  public ApiResult<List<RouteItemVO>> getMenuList() throws Exception {
    return ApiResult.success(sysUserService.getMenuList());
  }

  @GetMapping("/getPermCode")
  @Operation(
      summary = "获取角色集合",
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "Success",
              content = @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(
                      title = "ApiResult模型",
                      description = "返回实体，ApiResult内data为角色集合",
                      implementation = String.class
                  )
              )
          )
      },
      security = {@SecurityRequirement(name = LoginConstant.BG_HEADER)}
  )
  public ApiResult<Set<String>> getPermCode() throws Exception {
    return ApiResult.success(sysUserService.getPermCode());
  }

  @GetMapping("/getSysUserInfo")
  @Operation(
      summary = "根据token获取系统登录用户信息",
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "Success",
              content = @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(
                      title = "ApiResult模型",
                      description = "返回实体，ApiResult内data为角色-权限的map集合",
                      implementation = Map.class
                  )
              )
          )
      },
      security = {@SecurityRequirement(name = LoginConstant.BG_HEADER)}
  )
  public ApiResult<Map<String, Object>> getSysUser() throws Exception {
    List<RoleInfoVO> roles = SecurityUtil.getRoles();
    return ApiResult.okMap("roles", roles);
  }

}
