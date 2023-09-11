package com.bg.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bg.commons.api.ApiResult;
import com.bg.commons.controller.BaseController;
import com.bg.commons.log.annotation.OperationLog;
import com.bg.commons.log.enums.OperationLogType;
import com.bg.system.entity.SysMenu;
import com.bg.system.param.SysPermissionPageParam;
import com.bg.system.service.SysMenuService;
import com.bg.system.service.SysRoleMenuService;
import com.bg.system.vo.SysPermissionTreeVo;
import com.bg.system.vo.SysPermissionVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 * 系统权限 前端控制器
 * </pre>
 *
 * @author jiewus
 */
@Slf4j
@RestController
@RequestMapping("/v1/api/admin/sysPermission")
@Tag(name = "系统权限 API")
public class SysPermissionController extends BaseController<SysMenu, SysMenuService, SysMenu> {

  @Autowired
  private SysRoleMenuService sysRoleMenuService;

  /**
   * 添加系统权限
   */
  @PostMapping("/add")
  @PreAuthorize("@auth.hasPermission('sys:permission:add')")
  @OperationLog(name = "添加系统权限", type = OperationLogType.ADD)
  @Operation(summary = "添加系统权限")
  public ApiResult<Boolean> addSysPermission(@Validated @RequestBody SysMenu sysMenu) throws Exception {
    boolean flag = baseService.saveSysPermission(sysMenu);
    return ApiResult.result(flag);
  }

  /**
   * 修改系统权限
   */
  @PostMapping("/update")
  @PreAuthorize("@auth.hasPermission('sys:permission:update')")
  @OperationLog(name = "添加系统权限", type = OperationLogType.UPDATE)
  @Operation(summary = "修改系统权限")
  public ApiResult<Boolean> updateSysPermission(@Validated @RequestBody SysMenu sysMenu) throws Exception {
    boolean flag = baseService.updateSysPermission(sysMenu);
    return ApiResult.result(flag);
  }

  /**
   * 删除系统权限
   */
  @PostMapping("/delete/{id}")
  @PreAuthorize("@auth.hasPermission('sys:permission:delete')")
  @OperationLog(name = "删除系统权限", type = OperationLogType.DELETE)
  @Operation(summary = "删除系统权限")
  public ApiResult<Boolean> deleteSysPermission(@PathVariable("id") String id) throws Exception {
    boolean flag = baseService.deleteSysPermission(id);
    return ApiResult.result(flag);
  }

  /**
   * 系统权限详情
   */
  @GetMapping("/info/{id}")
  @PreAuthorize("@auth.hasPermission('sys:permission:info')")
  @OperationLog(name = "系统权限详情", type = OperationLogType.INFO)
  @Operation(summary = "系统权限详情")
  public ApiResult<SysPermissionVo> getSysPermission(@PathVariable("id") String id) throws Exception {
    SysPermissionVo sysPermissionVo = baseService.getSysPermissionById(id);
    return ApiResult.success(sysPermissionVo);
  }

  /**
   * 系统权限分页列表
   */
  @PostMapping("/getPageList")
  @PreAuthorize("@auth.hasPermission('sys:permission:page')")
  @OperationLog(name = "系统权限分页列表", type = OperationLogType.PAGE)
  @Operation(summary = "系统权限分页列表")
  public ApiResult<Page<SysPermissionVo>> getSysPermissionPageList(@Validated @RequestBody SysPermissionPageParam pageParam) throws Exception {
    Page<SysPermissionVo> page = baseService.getSysPermissionPageList(pageParam);
    return ApiResult.success(page);
  }

  /**
   * 获取所有菜单列表
   *
   * @return
   */
  @GetMapping("/getAllMenuList")
  @PreAuthorize("@auth.hasPermission('sys:permission:all:menu:list')")
  @OperationLog(name = "获取所有菜单列表", type = OperationLogType.LIST)
  @Operation(summary = "获取所有菜单列表")
  public ApiResult<List<SysMenu>> getAllMenuList() throws Exception {
    List<SysMenu> list = baseService.getAllMenuList();
    return ApiResult.success(list);
  }

  /**
   * 获取获取菜单树形列表
   *
   * @return
   */
  @GetMapping("/getAllMenuTree")
  @PreAuthorize("@auth.hasPermission('sys:permission:all:menu:tree')")
  @OperationLog(name = "获取获取菜单树形列表", type = OperationLogType.OTHER_QUERY)
  @Operation(summary = "获取获取菜单树形列表")
  public ApiResult<List<SysPermissionTreeVo>> getAllMenuTree() throws Exception {
    List<SysPermissionTreeVo> treeVos = baseService.getAllMenuTree();
    return ApiResult.success(treeVos);
  }


  /**
   * 根据用户id获取菜单列表
   *
   * @return
   */
  @PostMapping("/getMenuListByUserId/{userId}")
  @PreAuthorize("@auth.hasPermission('sys:permission:menu:list')")
  @OperationLog(name = "根据用户id获取菜单列表", type = OperationLogType.OTHER_QUERY)
  @Operation(summary = "根据用户id获取菜单列表")
  public ApiResult<List<SysMenu>> getMenuListByUserId(@PathVariable("userId") String userId) throws Exception {
    List<SysMenu> list = baseService.getMenuListByUserId(userId);
    return ApiResult.success(list);
  }

  /**
   * 根据用户id获取菜单树形列表
   *
   * @return
   */
  @PostMapping("/getMenuTreeByUserId/{userId}")
  @PreAuthorize("@auth.hasPermission('sys:permission:menu:tree')")
  @OperationLog(name = "根据用户id获取菜单树形列表", type = OperationLogType.OTHER_QUERY)
  @Operation(summary = "根据用户id获取菜单树形列表")
  public ApiResult<List<SysPermissionTreeVo>> getMenuTreeByUserId(@PathVariable("userId") String userId) throws Exception {
    List<SysPermissionTreeVo> treeVos = baseService.getMenuTreeByUserId(userId);
    return ApiResult.success(treeVos);
  }

  /**
   * 根据用户id获取该用户所有权限编码
   *
   * @return
   */
  @GetMapping("/getPermissionCodesByUserId/{userId}")
  @PreAuthorize("@auth.hasPermission('sys:permission:codes')")
  @OperationLog(name = "根据用户id获取该用户所有权限编码", type = OperationLogType.OTHER_QUERY)
  @Operation(summary = "根据用户id获取该用户所有权限编码")
  public ApiResult<Set<String>> getPermissionCodesByUserId(@PathVariable("userId") String userId) throws Exception {
    Set<String> list = baseService.getPermissionCodesByUserId(userId);
    return ApiResult.success(list);
  }

  /**
   * 根据角色id获取该对应的所有三级权限ID
   *
   * @return
   */
  @GetMapping("/getThreeLevelPermissionIdsByRoleId/{roleId}")
  @PreAuthorize("@auth.hasPermission('sys:permission:three-ids-by-role-id')")
  @OperationLog(name = "根据角色id获取该对应的所有三级权限ID", type = OperationLogType.OTHER_QUERY)
  @Operation(summary = "根据角色id获取该对应的所有三级权限ID")
  public ApiResult<List<String>> getPermissionIdsByRoleId(@PathVariable("roleId") String roleId) throws Exception {
    List<String> list = sysRoleMenuService.getThreeLevelPermissionIdsByRoleId(roleId);
    return ApiResult.success(list);
  }

  /**
   * 获取所有导航树形菜单(一级/二级菜单)
   *
   * @return
   */
  @PostMapping("/getNavMenuTree")
  @PreAuthorize("@auth.hasPermission('sys:permission:nav-menu')")
  @OperationLog(name = "获取所有导航菜单(一级/二级菜单)", type = OperationLogType.OTHER_QUERY)
  @Operation(summary = "获取所有导航菜单(一级/二级菜单)")
  public ApiResult<List<SysPermissionTreeVo>> getNavMenuTree() throws Exception {
    List<SysPermissionTreeVo> list = baseService.getNavMenuTree();
    return ApiResult.success(list);
  }

}

