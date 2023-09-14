package com.bg.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bg.commons.api.ApiResult;
import com.bg.commons.constant.LoginConstant;
import com.bg.commons.controller.BaseController;
import com.bg.commons.log.annotation.OperationLog;
import com.bg.commons.log.enums.OperationLogType;
import com.bg.system.entity.SysMenu;
import com.bg.system.param.MenuPageParam;
import com.bg.system.service.ISysMenuService;
import com.bg.system.vo.RouteItemVO;
import com.bg.system.vo.SysPermissionTreeVo;
import com.bg.system.vo.SysPermissionVo;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/api/admin/sysPermission")
@Tag(name = "系统权限 API")
public class SysMenuController extends BaseController<SysMenu, ISysMenuService, SysMenu> {

  @PostMapping("/add")
  @PreAuthorize("@auth.hasPermission('sys:permission:add')")
  @OperationLog(name = "添加系统权限", type = OperationLogType.ADD)
  @Operation(summary = "添加系统权限")
  public ApiResult<Boolean> addSysPermission(@Validated @RequestBody SysMenu sysMenu) throws Exception {
    boolean flag = baseService.saveSysPermission(sysMenu);
    return ApiResult.result(flag);
  }

  @PostMapping("/update")
  @PreAuthorize("@auth.hasPermission('sys:permission:update')")
  @OperationLog(name = "添加系统权限", type = OperationLogType.UPDATE)
  @Operation(summary = "修改系统权限")
  public ApiResult<Boolean> updateSysPermission(@Validated @RequestBody SysMenu sysMenu) throws Exception {
    boolean flag = baseService.updateSysPermission(sysMenu);
    return ApiResult.result(flag);
  }

  @PostMapping("/delete/{id}")
  @PreAuthorize("@auth.hasPermission('sys:permission:delete')")
  @OperationLog(name = "删除系统权限", type = OperationLogType.DELETE)
  @Operation(summary = "删除系统权限")
  public ApiResult<Boolean> deleteSysPermission(@PathVariable("id") String id) {
    boolean flag = baseService.deleteSysPermission(id);
    return ApiResult.result(flag);
  }

  @GetMapping("/info/{id}")
  @PreAuthorize("@auth.hasPermission('sys:permission:info')")
  @OperationLog(name = "系统权限详情", type = OperationLogType.INFO)
  @Operation(summary = "系统权限详情")
  public ApiResult<SysPermissionVo> getSysPermission(@PathVariable("id") String id) throws Exception {
    SysPermissionVo sysPermissionVo = baseService.getSysPermissionById(id);
    return ApiResult.success(sysPermissionVo);
  }

  @PostMapping("/getPageList")
  @PreAuthorize("@auth.hasPermission('sys:permission:page')")
  @OperationLog(name = "系统权限分页列表", type = OperationLogType.PAGE)
  @Operation(summary = "系统权限分页列表")
  public ApiResult<Page<SysPermissionVo>> getSysPermissionPageList(@Validated @RequestBody MenuPageParam pageParam) throws Exception {
    Page<SysPermissionVo> page = baseService.getSysPermissionPageList(pageParam);
    return ApiResult.success(page);
  }

  @GetMapping("/getAllMenuList")
  @PreAuthorize("@auth.hasPermission('sys:permission:all:menu:list')")
  @OperationLog(name = "获取所有菜单列表", type = OperationLogType.LIST)
  @Operation(summary = "获取所有菜单列表")
  public ApiResult<List<SysMenu>> getAllMenuList() throws Exception {
    List<SysMenu> list = baseService.getAllMenuList();
    return ApiResult.success(list);
  }

  @GetMapping("/getAllMenuTree")
  @PreAuthorize("@auth.hasPermission('sys:permission:all:menu:tree')")
  @OperationLog(name = "获取获取菜单树形列表", type = OperationLogType.OTHER_QUERY)
  @Operation(summary = "获取获取菜单树形列表")
  public ApiResult<List<SysPermissionTreeVo>> getAllMenuTree() throws Exception {
    List<SysPermissionTreeVo> treeVos = baseService.getAllMenuTree();
    return ApiResult.success(treeVos);
  }

  @PostMapping("/getMenuListByUserId/{userId}")
  @PreAuthorize("@auth.hasPermission('sys:permission:menu:list')")
  @OperationLog(name = "根据用户id获取菜单列表", type = OperationLogType.OTHER_QUERY)
  @Operation(summary = "根据用户id获取菜单列表")
  public ApiResult<List<SysMenu>> getMenuListByUserId(@PathVariable("userId") String userId) throws Exception {
    List<SysMenu> list = baseService.getMenuListByUserId(userId);
    return ApiResult.success(list);
  }

  @PostMapping("/getMenuTreeByUserId/{userId}")
  @PreAuthorize("@auth.hasPermission('sys:permission:menu:tree')")
  @OperationLog(name = "根据用户id获取菜单树形列表", type = OperationLogType.OTHER_QUERY)
  @Operation(summary = "根据用户id获取菜单树形列表")
  public ApiResult<List<SysPermissionTreeVo>> getMenuTreeByUserId(@PathVariable("userId") String userId) throws Exception {
    List<SysPermissionTreeVo> treeVos = baseService.getMenuTreeByUserId(userId);
    return ApiResult.success(treeVos);
  }

  @GetMapping("/getPermissionCodesByUserId/{userId}")
  @PreAuthorize("@auth.hasPermission('sys:permission:codes')")
  @OperationLog(name = "根据用户id获取该用户所有权限编码", type = OperationLogType.OTHER_QUERY)
  @Operation(summary = "根据用户id获取该用户所有权限编码")
  public ApiResult<Set<String>> getPermissionCodesByUserId(@PathVariable("userId") String userId) throws Exception {
    Set<String> list = baseService.getPermissionCodesByUserId(userId);
    return ApiResult.success(list);
  }

  @GetMapping("/getThreeLevelPermissionIdsByRoleId/{roleId}")
  @PreAuthorize("@auth.hasPermission('sys:permission:three-ids-by-role-id')")
  @OperationLog(name = "根据角色id获取该对应的所有三级权限ID", type = OperationLogType.OTHER_QUERY)
  @Operation(summary = "根据角色id获取该对应的所有三级权限ID")
  public ApiResult<List<String>> getPermissionIdsByRoleId(@PathVariable("roleId") String roleId) {
    List<String> list = baseService.getThreeLevelPermissionIdsByRoleId(roleId);
    return ApiResult.success(list);
  }

  @PostMapping("/getNavMenuTree")
  @PreAuthorize("@auth.hasPermission('sys:permission:nav-menu')")
  @OperationLog(name = "获取所有导航菜单(一级/二级菜单)", type = OperationLogType.OTHER_QUERY)
  @Operation(summary = "获取所有导航菜单(一级/二级菜单)")
  public ApiResult<List<SysPermissionTreeVo>> getNavMenuTree() throws Exception {
    List<SysPermissionTreeVo> list = baseService.getNavMenuTree();
    return ApiResult.success(list);
  }

  @GetMapping("/listRoleMenus")
  @OperationLog(name = "查询角色关联的菜单", type = OperationLogType.LIST)
  @Operation(summary = "查询角色关联的菜单")
  public ApiResult<List<SysMenu>> listRoleMenus(@RequestParam String roleId) {
    return ApiResult.success(baseService.listRoleMenus(roleId));
  }

  @Hidden
  @GetMapping("/getMenuList")
  @Operation(
      summary = "获取当前用户菜单集合",
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

