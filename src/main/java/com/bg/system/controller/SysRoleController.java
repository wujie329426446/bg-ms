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
import com.bg.system.entity.SysRole;
import com.bg.system.param.RolePageParam;
import com.bg.system.service.ISysRoleService;
import com.bg.system.vo.SysRoleVo;
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
@RequestMapping("/role")
@Tag(name = "系统角色API")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SysRoleController extends BaseController<SysRole, ISysRoleService, RolePageParam> {

  @PostMapping("/add")
  @PreAuthorize("@auth.hasPermission('sys:role:add')")
  @OperationLog(name = "添加系统角色", type = OperationLogType.ADD)
  @Operation(
      summary = "添加系统角色",
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "请求体描述",
          required = true,
          content = {
              @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = SysRole.class)
              )
          }
      ),
      security = {@SecurityRequirement(name = LoginConstant.BG_HEADER)}
  )
  public ApiResult<Boolean> save(@Validated(Add.class) @RequestBody SysRole sysRole) {
    Boolean flag = baseService.save(sysRole);
    return ApiResult.result(flag);
  }

  @PostMapping("/update")
  @PreAuthorize("@auth.hasPermission('sys:role:update')")
  @OperationLog(name = "修改系统角色", type = OperationLogType.UPDATE)
  @Operation(
      summary = "修改系统角色",
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "请求体描述",
          required = true,
          content = {
              @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = SysRole.class)
              )
          }
      ),
      security = {@SecurityRequirement(name = LoginConstant.BG_HEADER)}
  )
  public ApiResult<Boolean> updateById(@Validated(Update.class) @RequestBody SysRole sysRole) {
    boolean flag = baseService.updateById(sysRole);
    return ApiResult.result(flag);
  }

  @GetMapping("/detail")
  @PreAuthorize("@auth.hasPermission('sys:role:info')")
  @OperationLog(name = "系统角色详情页面查看", type = OperationLogType.INFO)
  @Operation(
      summary = "系统角色详情页面查看",
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
                      anyOf = {ApiResult.class, SysRoleVo.class}
                  )
              )
          )
      },
      security = {@SecurityRequirement(name = LoginConstant.BG_HEADER)}
  )
  public ApiResult<SysRoleVo> getRoleById(@RequestParam String id) {
    SysRoleVo sysRoleVo = baseService.getRoleById(id);
    return ApiResult.success(sysRoleVo);
  }

  @DeleteMapping("/delete/{id}")
  @PreAuthorize("@auth.hasPermission('sys:role:delete')")
  @OperationLog(name = "删除系统角色", type = OperationLogType.DELETE)
  @Operation(
      summary = "删除系统角色",
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
  public ApiResult<Boolean> deleteSysRole(@PathVariable("id") String id) {
    boolean flag = baseService.removeById(id);
    return ApiResult.result(flag);
  }

  @PostMapping("/getPageList")
  @PreAuthorize("@auth.hasPermission('sys:role:page')")
  @OperationLog(name = "系统角色分页列表", type = OperationLogType.PAGE)
  @Operation(
      summary = "系统角色分页列表",
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "请求体描述",
          required = true,
          content = {
              @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = RolePageParam.class)
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
                      title = "ApiResult、Page、SysRoleVo",
                      description = "返回实体，ApiResult内data为SysRoleVo类型的Page对象",
                      anyOf = {ApiResult.class, Page.class, SysRole.class}
                  )
              )
          )
      },
      security = {@SecurityRequirement(name = LoginConstant.BG_HEADER)}
  )
  public ApiResult<Page<SysRole>> getRolePageList(@Validated(Query.class) @RequestBody RolePageParam pageParam) {
    Page<SysRole> page = baseService.getRolePageList(pageParam);
    return ApiResult.success(page);
  }

  @PostMapping("/getList")
  @PreAuthorize("@auth.hasPermission('sys:role:list')")
  @OperationLog(name = "系统角色列表", type = OperationLogType.LIST)
  @Operation(
      summary = "系统角色列表",
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "请求体描述",
          required = true,
          content = {
              @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = RolePageParam.class)
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
                      title = "ApiResult、SysRoleVo",
                      description = "返回实体，ApiResult内data为SysRoleVo集合",
                      anyOf = {ApiResult.class, SysRoleVo.class}
                  )
              )
          )
      },
      security = {@SecurityRequirement(name = LoginConstant.BG_HEADER)}
  )
  public ApiResult<List<SysRoleVo>> getRoleList(@Validated(Query.class) @RequestBody RolePageParam pageParam) {
    List<SysRoleVo> roleList = baseService.getRoleList(pageParam);
    return ApiResult.success(roleList);
  }

}

