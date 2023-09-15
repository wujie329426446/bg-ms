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
import com.bg.system.entity.SysMenu;
import com.bg.system.param.MenuPageParam;
import com.bg.system.service.ISysMenuService;
import com.bg.system.vo.SysMenuTreeVo;
import com.bg.system.vo.SysMenuVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Set;
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
@RequestMapping("/menu")
@Tag(name = "系统菜单API")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SysMenuController extends BaseController<SysMenu, ISysMenuService, MenuPageParam> {

  @PostMapping("/add")
  @PreAuthorize("@auth.hasPermission('sys:permission:add')")
  @OperationLog(name = "添加系统权限", type = OperationLogType.ADD)
  @Operation(
      summary = "添加系统权限",
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "请求体描述",
          required = true,
          content = {
              @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = SysMenu.class)
              )
          }
      ),
      security = {@SecurityRequirement(name = LoginConstant.BG_HEADER)}
  )
  public ApiResult<Boolean> save(@Validated(Add.class) @RequestBody SysMenu sysMenu) {
    boolean flag = baseService.save(sysMenu);
    return ApiResult.result(flag);
  }

  @PostMapping("/update")
  @PreAuthorize("@auth.hasPermission('sys:permission:update')")
  @OperationLog(name = "添加系统权限", type = OperationLogType.UPDATE)
  @Operation(
      summary = "修改系统权限",
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "请求体描述",
          required = true,
          content = {
              @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = SysMenu.class)
              )
          }
      ),
      security = {@SecurityRequirement(name = LoginConstant.BG_HEADER)}
  )
  public ApiResult<Boolean> updateById(@Validated(Update.class) @RequestBody SysMenu sysMenu) {
    boolean flag = baseService.updateById(sysMenu);
    return ApiResult.result(flag);
  }

  @GetMapping("/detail")
  @PreAuthorize("@auth.hasPermission('sys:permission:info')")
  @OperationLog(name = "系统权限详情", type = OperationLogType.INFO)
  @Operation(
      summary = "系统权限详情",
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
                      title = "ApiResult、SysMenuVo组合模型",
                      description = "返回实体，ApiResult内data为SysMenuVo类型的对象",
                      anyOf = {ApiResult.class, SysMenuVo.class}
                  )
              )
          )
      },
      security = {@SecurityRequirement(name = LoginConstant.BG_HEADER)}
  )
  public ApiResult<SysMenuVo> getMenuById(@RequestParam String id) {
    SysMenuVo sysMenuVo = baseService.getMenuById(id);
    return ApiResult.success(sysMenuVo);
  }

  @DeleteMapping("/delete/{id}")
  @PreAuthorize("@auth.hasPermission('sys:permission:delete')")
  @OperationLog(name = "删除系统权限", type = OperationLogType.DELETE)
  @Operation(
      summary = "删除系统权限",
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
  public ApiResult<Boolean> deleteSysMenu(@PathVariable("id") String id) {
    boolean flag = baseService.removeById(id);
    return ApiResult.result(flag);
  }

  @PostMapping("/getPageList")
  @PreAuthorize("@auth.hasPermission('sys:permission:page')")
  @OperationLog(name = "系统权限分页列表", type = OperationLogType.PAGE)
  @Operation(
      summary = "系统权限分页列表",
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "请求体描述",
          required = true,
          content = {
              @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = MenuPageParam.class)
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
                      anyOf = {ApiResult.class, Page.class, SysMenu.class}
                  )
              )
          )
      },
      security = {@SecurityRequirement(name = LoginConstant.BG_HEADER)}
  )
  public ApiResult<Page<SysMenu>> getMenuPageList(@Validated(Query.class) @RequestBody MenuPageParam pageParam) {
    Page<SysMenu> page = baseService.getMenuPageList(pageParam);
    return ApiResult.success(page);
  }

  @PostMapping("/getList")
  @PreAuthorize("@auth.hasPermission('sys:permission:list')")
  @OperationLog(name = "获取所有菜单列表(可根据用户查)", type = OperationLogType.LIST)
  @Operation(
      summary = "获取所有菜单列表(可根据用户查)",
      parameters = {
          @Parameter(
              description = "用户主键id",
              in = ParameterIn.QUERY,
              name = "userId"
          )
      },
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "Success",
              content = @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(
                      title = "ApiResult、SysMenuVo",
                      description = "返回实体，ApiResult内data为SysMenuVo集合",
                      anyOf = {ApiResult.class, SysMenuVo.class}
                  )
              )
          )
      },
      security = {@SecurityRequirement(name = LoginConstant.BG_HEADER)}
  )
  public ApiResult<List<SysMenuVo>> getMenuList(@RequestParam(required = false) String userId) {
    List<SysMenuVo> list = baseService.getMenuList(userId);
    return ApiResult.success(list);
  }

  @GetMapping("/getTree")
  @PreAuthorize("@auth.hasPermission('sys:permission:tree')")
  @OperationLog(name = "获取获取菜单树形列表(可根据用户查)", type = OperationLogType.OTHER_QUERY)
  @Operation(
      summary = "获取获取菜单树形列表(可根据用户查)",
      parameters = {
          @Parameter(
              description = "用户主键id",
              in = ParameterIn.QUERY,
              name = "userId"
          )
      },
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "Success",
              content = @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(
                      title = "ApiResult、SysMenuTreeVo",
                      description = "返回实体，ApiResult内data为SysMenuTreeVo集合",
                      anyOf = {ApiResult.class, SysMenuTreeVo.class}
                  )
              )
          )
      },
      security = {@SecurityRequirement(name = LoginConstant.BG_HEADER)}
  )
  public ApiResult<List<SysMenuTreeVo>> getMenuTree(@RequestParam(required = false) String userId) {
    List<SysMenuTreeVo> treeVos = baseService.getMenuTree(userId);
    return ApiResult.success(treeVos);
  }

  @GetMapping("/getCodesByUserId")
  @PreAuthorize("@auth.hasPermission('sys:permission:codes')")
  @OperationLog(name = "根据用户id获取该用户所有权限编码", type = OperationLogType.OTHER_QUERY)
  @Operation(
      summary = "根据用户id获取该用户所有权限编码",
      parameters = {
          @Parameter(
              description = "用户主键id",
              in = ParameterIn.QUERY,
              name = "userId",
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
                      title = "ApiResult、String",
                      description = "返回实体，ApiResult内data为菜单编码集合",
                      anyOf = {ApiResult.class, String.class}
                  )
              )
          )
      },
      security = {@SecurityRequirement(name = LoginConstant.BG_HEADER)}
  )
  public ApiResult<Set<String>> getCodesByUser(@RequestParam String userId) {
    Set<String> list = baseService.getCodesByUser(userId);
    return ApiResult.success(list);
  }

  @GetMapping("/listRoleMenus")
  @PreAuthorize("@auth.hasPermission('sys:permission:list')")
  @OperationLog(name = "查询角色关联的菜单", type = OperationLogType.LIST)
  @Operation(
      summary = "查询角色关联的菜单",
      parameters = {
          @Parameter(
              description = "用户主键id",
              in = ParameterIn.QUERY,
              name = "roleId",
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
                      title = "ApiResult、SysMenuVo",
                      description = "返回实体，ApiResult内data为SysMenuVo集合",
                      anyOf = {ApiResult.class, SysMenuVo.class}
                  )
              )
          )
      },
      security = {@SecurityRequirement(name = LoginConstant.BG_HEADER)}
  )
  public ApiResult<List<SysMenuVo>> getListByRole(@RequestParam String roleId) {
    List<SysMenuVo> menuList = baseService.getMenuListByRole(roleId);
    return ApiResult.success(menuList);
  }

}

