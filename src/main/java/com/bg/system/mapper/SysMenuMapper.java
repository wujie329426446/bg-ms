package com.bg.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bg.system.entity.SysMenu;
import java.util.List;
import java.util.Set;
import org.apache.ibatis.annotations.Param;

/**
 * <pre>
 * 系统权限 Mapper 接口
 * </pre>
 *
 * @author jiewus
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

  /**
   * 根据用户id获取该用户所有权限编码
   *
   * @param userId
   * @return
   * @throws Exception
   */
  Set<String> getCodesByUser(@Param("userId") String userId);

  /**
   * 根据用户id获取菜单列表
   *
   * @param userId
   * @return
   */
  List<SysMenu> getMenuListByUser(@Param("userId") String userId);

  /**
   * 根据角色id获取菜单列表
   *
   * @param roleId
   * @return
   */
  List<SysMenu> getMenuListByRole(@Param("roleId") String roleId);
}
