package com.bg.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bg.commons.api.ApiResult;
import com.bg.commons.constant.LoginConstant;
import com.bg.commons.controller.BaseController;
import com.bg.commons.core.validator.groups.Add;
import com.bg.commons.core.validator.groups.Query;
import com.bg.commons.core.validator.groups.Update;
import com.bg.commons.log.annotation.OperationLog;
import com.bg.commons.log.enums.OperationLogType;
import com.bg.framework.prefix.AdminApiRestController;
import com.bg.system.entity.SysUser;
import com.bg.system.param.UserPageParam;
import com.bg.system.service.ISysUserService;
import com.bg.system.vo.RouteItemVO;
import com.bg.system.vo.SysUserVo;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@AdminApiRestController
@Tag(name = "系统用户API")
@RequestMapping("/user")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SysUserController extends BaseController<SysUser, ISysUserService, UserPageParam> {

  @PostMapping("/add")
  @PreAuthorize("@auth.hasPermission('sys:user:add')")
  @OperationLog(name = "添加系统用户", type = OperationLogType.ADD)
  @Operation(
      summary = "添加系统用户",
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "请求体描述",
          required = true,
          content = {
              @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = SysUser.class)
              )
          }
      ),
      security = {@SecurityRequirement(name = LoginConstant.BG_HEADER)}
  )
  public ApiResult<Boolean> save(@Validated(Add.class) @RequestBody SysUser sysUser) {
    Boolean flag = baseService.save(sysUser);
    return ApiResult.success(flag);
  }

  @PostMapping("/update")
  @PreAuthorize("@auth.hasPermission('sys:user:update')")
  @OperationLog(name = "修改系统用户", type = OperationLogType.UPDATE)
  @Operation(
      summary = "修改系统用户",
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "请求体描述",
          required = true,
          content = {
              @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = SysUser.class)
              )
          }
      ),
      security = {@SecurityRequirement(name = LoginConstant.BG_HEADER)}
  )
  public ApiResult<Boolean> updateById(@Validated(Update.class) @RequestBody SysUser sysUser) {
    boolean flag = baseService.updateById(sysUser);
    return ApiResult.result(flag);
  }

  @GetMapping("/detail")
  @PreAuthorize("@auth.hasPermission('sys:user:info')")
  @OperationLog(name = "系统用户详情查看", type = OperationLogType.INFO)
  @Operation(
      summary = "系统用户详情查看",
      parameters = {
          @Parameter(
              description = "主键id",
              in = ParameterIn.QUERY,
              name = "id",
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
                      title = "ApiResult、SysUserVo组合模型",
                      description = "返回实体，ApiResult内data为SysUserVo类型的对象",
                      anyOf = {ApiResult.class, SysUserVo.class}
                  )
              )
          )
      },
      security = {@SecurityRequirement(name = LoginConstant.BG_HEADER)}
  )
  public ApiResult<SysUserVo> getUserById(@RequestParam String id) {
    SysUserVo sysUserVo = baseService.getUserById(id);
    return ApiResult.success(sysUserVo);
  }

  @DeleteMapping("/delete/{id}")
  @PreAuthorize("@auth.hasPermission('sys:user:delete')")
  @OperationLog(name = "删除系统用户", type = OperationLogType.DELETE)
  @Operation(
      summary = "删除系统用户",
      parameters = {
          @Parameter(
              description = "主键id",
              in = ParameterIn.PATH,
              name = "id",
              required = true
          )
      },
      security = {@SecurityRequirement(name = LoginConstant.BG_HEADER)}
  )
  public ApiResult<Boolean> deleteSysUser(@PathVariable("id") String id) {
    boolean flag = baseService.removeById(id);
    return ApiResult.result(flag);
  }

  @PostMapping("/getPageList")
  @PreAuthorize("@auth.hasPermission('sys:user:page')")
  @OperationLog(name = "系统用户分页列表", type = OperationLogType.PAGE)
  @Operation(
      summary = "系统用户分页列表",
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "请求体描述",
          required = true,
          content = {
              @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = UserPageParam.class)
              )
          }
      ),
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "Success",
              content = @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(
                      title = "ApiResult、Page、SysUserVo组合模型",
                      description = "返回实体，ApiResult内data为SysUserVo类型的Page对象",
                      anyOf = {ApiResult.class, Page.class, SysUserVo.class}
                  )
              )
          )
      },
      security = {@SecurityRequirement(name = LoginConstant.BG_HEADER)}
  )
  public ApiResult<Page<SysUserVo>> getUserPageList(@Validated(Query.class) @RequestBody UserPageParam pageParam) {
    Page<SysUserVo> page = baseService.getUserPageList(pageParam);
    return ApiResult.success(page);
  }

  @Hidden
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
    return ApiResult.success(baseService.getMenuList());
  }

}
