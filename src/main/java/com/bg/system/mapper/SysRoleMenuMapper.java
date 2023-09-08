package com.bg.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bg.system.entity.SysRoleMenu;
import com.bg.system.param.sysrole.SysRolePermissionPageParam;
import com.bg.system.vo.SysRoleMenuVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * <pre>
 * 角色权限关系 Mapper 接口
 * </pre>
 *
 * @author jiewus
 */
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

  /**
   * 根据ID获取查询对象
   *
   * @param id
   * @return
   */
  SysRoleMenuVo getSysRolePermissionById(Serializable id);

  /**
   * 获取分页对象
   *
   * @param page
   * @param sysRolePermissionPageParam
   * @return
   */
  IPage<SysRoleMenuVo> getSysRolePermissionPageList(@Param("page") Page page, @Param("param") SysRolePermissionPageParam sysRolePermissionPageParam);

  /**
   * 根据角色id获取可用的权限编码
   *
   * @param roleIds
   * @return
   */
  Set<String> getPermissionCodesByRoleId(@Param("roleIds") List<String> roleIds);

  /**
   * 根据角色id获取该对应的所有三级权限ID
   *
   * @param roleId
   * @return
   */
  List<String> getThreeLevelPermissionIdsByRoleId(@Param("roleId") String roleId);
}
