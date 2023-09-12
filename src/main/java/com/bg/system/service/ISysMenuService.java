package com.bg.system.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bg.system.entity.SysMenu;
import com.bg.system.param.MenuPageParam;
import com.bg.system.vo.SysPermissionTreeVo;
import com.bg.system.vo.SysPermissionVo;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * <pre>
 * 系统权限 服务类
 * </pre>
 *
 * @author jiewus
 */
public interface ISysMenuService extends IService<SysMenu> {

  /**
   * 保存
   *
   * @param sysMenu
   * @return
   * @throws Exception
   */
  boolean saveSysPermission(SysMenu sysMenu) throws Exception;

  /**
   * 修改
   *
   * @param sysMenu
   * @return
   * @throws Exception
   */
  boolean updateSysPermission(SysMenu sysMenu) throws Exception;

  /**
   * 删除
   *
   * @param id
   * @return
   * @throws Exception
   */
  boolean deleteSysPermission(String id);

  /**
   * 根据ID获取查询对象
   *
   * @param id
   * @return
   * @throws Exception
   */
  SysPermissionVo getSysPermissionById(Serializable id) throws Exception;

  /**
   * 获取分页对象
   *
   * @param menuPageParam
   * @return
   * @throws Exception
   */
  Page<SysPermissionVo> getSysPermissionPageList(MenuPageParam menuPageParam) throws Exception;

  /**
   * 判断权限id是否存在
   *
   * @param permissionIds
   * @return
   * @throws Exception
   */
  boolean isExistsByPermissionIds(List<String> permissionIds) throws Exception;

  /**
   * 获取所有菜单列表
   *
   * @return
   * @throws Exception
   */
  List<SysMenu> getAllMenuList() throws Exception;

  /**
   * 转换权限列表为树形菜单
   *
   * @param sysMenus
   * @return
   * @throws Exception
   */
  List<SysPermissionTreeVo> convertSysPermissionTreeVoList(List<SysMenu> sysMenus) throws Exception;

  /**
   * 获取获取菜单树形列表
   *
   * @return
   * @throws Exception
   */
  List<SysPermissionTreeVo> getAllMenuTree() throws Exception;

  /**
   * 根据用户id获取该用户所有权限编码
   *
   * @param userId
   * @return
   * @throws Exception
   */
  Set<String> getPermissionCodesByUserId(String userId);

  /**
   * 根据用户id获取菜单列表
   *
   * @param userId
   * @return
   * @throws Exception
   */
  List<SysMenu> getMenuListByUserId(String userId) throws Exception;

  /**
   * 根据用户id获取菜单树形列表
   *
   * @param userId
   * @return
   * @throws Exception
   */
  List<SysPermissionTreeVo> getMenuTreeByUserId(String userId) throws Exception;

  /**
   * 根据角色id获取该对应的所有三级权限ID
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
  List<String> getThreeLevelPermissionIdsByRoleId(String roleId);

  /**
   * 获取所有导航菜单(一级/二级菜单)
   *
   * @return
   * @throws Exception
   */
  List<SysPermissionTreeVo> getNavMenuTree() throws Exception;


  /**
   * 查询角色关联的菜单
   *
   * @return
   */
  List<SysMenu> listRoleMenus(String roleId);

}
