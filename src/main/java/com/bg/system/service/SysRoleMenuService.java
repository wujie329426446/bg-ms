package com.bg.system.service;

import com.bg.commons.service.BaseService;
import com.bg.system.entity.SysRoleMenu;
import com.bg.system.entity.SysUserRole;
import org.apache.commons.collections4.SetUtils;

import java.util.List;
import java.util.Set;

/**
 * <pre>
 * 角色权限关系 服务类
 * </pre>
 *
 * @author jiewus
 */
public interface SysRoleMenuService extends BaseService<SysRoleMenu> {

  /**
   * 保存
   *
   * @param roleId
   * @param permissionIds
   * @return
   * @throws Exception
   */
  boolean saveSysRolePermission(String roleId, List<String> permissionIds) throws Exception;

  /**
   * 根据角色id获取权限id列表
   *
   * @param roleId
   * @return
   * @throws Exception
   */
  List<String> getPermissionIdsByRoleId(String roleId) throws Exception;

  /**
   * 根据角色id获取该对应的所有三级权限ID
   *
   * @param roleId
   * @return
   * @throws Exception
   */
  List<String> getThreeLevelPermissionIdsByRoleId(String roleId) throws Exception;

  /**
   * 批量保存角色权限关系
   *
   * @param roleId
   * @param addSet
   * @return
   * @throws Exception
   */
  boolean saveSysRolePermissionBatch(String roleId, SetUtils.SetView addSet) throws Exception;

  /**
   * 根据角色id删除关联的权限关系
   *
   * @param roleId
   * @return
   * @throws Exception
   */
  boolean deleteSysRolePermissionByRoleId(String roleId) throws Exception;

  /**
   * 根据角色id获取可用的权限编码
   *
   * @param sysUserRoleList
   * @return
   * @throws Exception
   */
  Set<String> getPermissionCodesByRoleId(List<SysUserRole> sysUserRoleList) throws Exception;

  /**
   * 通过角色id判断在角色权限表中是否有数据存在
   *
   * @param permissionId
   * @return
   * @throws Exception
   */
  boolean isExistsByPermissionId(String permissionId) throws Exception;

  /**
   * 角色下是否有权限
   *
   * @param roleId
   * @return
   * @throws Exception
   */
  boolean hasPermission(String roleId) throws Exception;

}
