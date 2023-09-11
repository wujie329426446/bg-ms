package com.bg.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bg.commons.api.ApiResult;
import com.bg.commons.controller.BaseController;
import com.bg.commons.core.validator.groups.Add;
import com.bg.commons.core.validator.groups.Update;
import com.bg.commons.log.annotation.OperationLog;
import com.bg.commons.log.enums.OperationLogType;
import com.bg.system.entity.SysMenu;
import com.bg.system.entity.SysRole;
import com.bg.system.param.RolePageParam;
import com.bg.system.param.sysrole.SysRolePageParam;
import com.bg.system.param.sysrole.UpdateSysRolePermissionParam;
import com.bg.system.service.SysRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 * 系统角色 前端控制器
 * </pre>
 *
 * @author jiewus
 */
@Slf4j
@RestController
@RequestMapping("/v1/api/admin/sysRole")
@Tag(name = "系统角色API")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SysRoleController extends BaseController<SysRole, SysRoleService, RolePageParam> {

  @PostMapping("/add")
  @PreAuthorize("@auth.hasPermission('sys:role:add')")
  @OperationLog(name = "添加系统角色", type = OperationLogType.ADD)
  @Operation(
      summary = "添加系统角色"
  )
  public ApiResult<Boolean> addSysRole(@Validated(Add.class) @RequestBody SysRole sysRole) {
    Boolean flag = baseService.save(sysRole);
    return ApiResult.result(flag);
  }

  /**
   * 修改系统角色
   */
  @PostMapping("/update")
  @PreAuthorize("@auth.hasPermission('sys:role:update')")
  @OperationLog(name = "修改系统角色", type = OperationLogType.UPDATE)
  @Operation(summary = "修改系统角色")
  public ApiResult<Boolean> updateSysRole(@Validated(Update.class) @RequestBody SysRole sysRole) throws Exception {
    boolean flag = baseService.updateSysRole(sysRole);
    return ApiResult.result(flag);
  }

  /**
   * 删除系统角色
   */
  @PostMapping("/delete/{id}")
  @PreAuthorize("@auth.hasPermission('sys:role:delete')")
  @OperationLog(name = "删除系统角色", type = OperationLogType.DELETE)
  @Operation(summary = "删除系统角色")
  public ApiResult<Boolean> deleteSysRole(@PathVariable("id") String id) throws Exception {
    boolean flag = baseService.deleteSysRole(id);
    return ApiResult.result(flag);
  }

  /**
   * 获取系统角色
   */
  @GetMapping("/info/{id}")
  @PreAuthorize("@auth.hasPermission('sys:role:info')")
  @OperationLog(name = "系统角色详情", type = OperationLogType.INFO)
  @Operation(summary = "系统角色详情")
  public ApiResult<SysRole> getSysRole(@PathVariable("id") String id) throws Exception {
    SysRole sysRole = baseService.getById(id);
    return ApiResult.success(sysRole);
  }

  /**
   * 系统角色分页列表
   */
  @PostMapping("/getPageList")
  @PreAuthorize("@auth.hasPermission('sys:role:page')")
  @OperationLog(name = "系统角色分页列表", type = OperationLogType.PAGE)
  @Operation(summary = "系统角色分页列表")
  public ApiResult<Page<SysRole>> getSysRolePageList(@Validated SysRolePageParam pageParam) throws Exception {
    Page<SysRole> page = baseService.getSysRolePageList(pageParam);
    return ApiResult.success(page);
  }

  /**
   * 获取系统角色列表
   *
   * @return
   */
  @GetMapping("/getList")
  @PreAuthorize("@auth.hasPermission('sys:role:list')")
  @OperationLog(name = "系统角色列表", type = OperationLogType.LIST)
  @Operation(summary = "系统角色列表")
  public ApiResult<List<SysRole>> getRoleList() {
    return ApiResult.success(baseService.list());
  }

  /**
   * 修改系统角色权限
   */
  @PostMapping("/updateSysRolePermission")
  @PreAuthorize("@auth.hasPermission('sys:role-permission:update')")
  @OperationLog(name = "修改系统角色权限", type = OperationLogType.UPDATE)
  @Operation(summary = "修改系统角色权限")
  public ApiResult<Boolean> updateSysRolePermission(@Validated @RequestBody UpdateSysRolePermissionParam param) throws Exception {
    // TODO: 2023/6/26 只更新了权限，角色本身信息没更新
    boolean flag = baseService.updateSysRolePermission(param);
    return ApiResult.result(flag);
  }

  /**
   * 查询角色关联的菜单
   */
  @GetMapping("/listRoleMenus")
  public ApiResult<List<SysMenu>> listRoleMenus(@RequestParam String roleId) {
    return ApiResult.success(baseService.listRoleMenus(roleId));
  }

  // 批量删除
  // 多个id值 [1,2,3]
  // json数组格式 --- java的list集合
  //    @PreAuthorize("hasAuthority('bnt.sysRole.remove')")
  @Operation(summary = "批量删除")
  @DeleteMapping("batchRemove")
  public ApiResult batchRemove(@RequestBody List<String> ids) {
    baseService.removeByIds(ids);
    return ApiResult.success();
  }

}

