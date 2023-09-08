package com.bg.system.service;

import com.bg.commons.pagination.Paging;
import com.bg.commons.service.BaseService;
import com.bg.system.entity.SysMenu;
import com.bg.system.entity.SysRole;
import com.bg.system.param.sysrole.SysRolePageParam;
import com.bg.system.param.sysrole.UpdateSysRolePermissionParam;
import com.bg.system.vo.SysRoleVo;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * <pre>
 * 系统角色 服务类
 * </pre>
 *
 * @author jiewus
 */
public interface SysRoleService extends BaseService<SysRole> {

  /**
   * 保存
   *
   * @param sysRole
   * @return
   * @throws Exception
   */
  boolean saveSysRole(SysRole sysRole) throws Exception;

  /**
   * 修改
   *
   * @param sysRole
   * @return
   * @throws Exception
   */
  boolean updateSysRole(SysRole sysRole) throws Exception;

  /**
   * 删除
   *
   * @param id
   * @return
   * @throws Exception
   */
  boolean deleteSysRole(String id) throws Exception;

  /**
   * 根据ID获取查询对象
   *
   * @param id
   * @return
   * @throws Exception
   */
  SysRoleVo getSysRoleById(Serializable id) throws Exception;

  /**
   * 获取分页对象
   *
   * @param sysRolePageParam
   * @return
   * @throws Exception
   */
  Paging<SysRole> getSysRolePageList(SysRolePageParam sysRolePageParam) throws Exception;

  /**
   * 根据id校验角色是否存在并且已启用
   *
   * @param id
   * @return
   * @throws Exception
   */
  boolean isEnableSysRole(String id) throws Exception;

  /**
   * 判断角色编码是否存在
   *
   * @param code
   * @return
   * @throws Exception
   */
  boolean isExistsByCode(String code) throws Exception;

  /**
   * 修改系统角色权限配置
   *
   * @param param
   * @return
   * @throws Exception
   */
  boolean updateSysRolePermission(UpdateSysRolePermissionParam param) throws Exception;

  /**
   * 查询角色关联的菜单
   *
   * @return
   */
  List<SysMenu> listRoleMenus(String roleId);

  /**
   * 根据用户主键获取角色主键集合
   *
   * @param userId 用户主键
   * @return 角色主键集合
   */
  List<String> getRoleIdsByUserId(String userId);

  /**
   * 根据用户主键获取角色集合
   *
   * @param userId 用户主键
   * @return 角色集合
   */
  Set<String> getRolesByUserId(String userId);
}
