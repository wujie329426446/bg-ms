package com.bg.system.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bg.system.entity.SysMenu;
import com.bg.system.param.MenuPageParam;
import com.bg.system.vo.SysMenuTreeVo;
import com.bg.system.vo.SysMenuVo;
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
   * 根据ID获取查询对象
   *
   * @param id
   * @return
   * @throws Exception
   */
  SysMenuVo getMenuById(String id);

  /**
   * 获取分页对象
   *
   * @param menuPageParam
   * @return
   * @throws Exception
   */
  Page<SysMenu> getMenuPageList(MenuPageParam menuPageParam);

  /**
   * 获取所有菜单列表
   *
   * @return
   * @throws Exception
   */
  List<SysMenuVo> getMenuList(String userId);

  /**
   * 获取获取菜单树形列表
   *
   * @return
   * @throws Exception
   */
  List<SysMenuTreeVo> getMenuTree(String userId);

  /**
   * 根据用户id获取该用户所有权限编码
   *
   * @param userId
   * @return
   * @throws Exception
   */
  Set<String> getCodesByUser(String userId);

  /**
   * 查询角色关联的菜单
   *
   * @return
   */
  List<SysMenuVo> getMenuListByRole(String roleId);

}
