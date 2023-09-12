package com.bg.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bg.system.entity.SysRole;
import com.bg.system.param.RolePageParam;
import com.bg.system.vo.SysRoleVo;
import java.util.List;

/**
 * <pre>
 * 系统角色 服务类
 * </pre>
 *
 * @author jiewus
 */
public interface ISysRoleService extends IService<SysRole> {

  /**
   * 根据id查询角色对象
   *
   * @param id
   * @return 角色对象
   */
  SysRoleVo getRoleById(String id);

  /**
   * 获取分页对象
   *
   * @param pageParam
   * @return
   * @throws Exception
   */
  Page<SysRole> getRolePageList(RolePageParam pageParam);

  /**
   * 获取集合对象
   *
   * @param pageParam
   * @return
   * @throws Exception
   */
  List<SysRoleVo> getRoleList(RolePageParam pageParam);


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
  List<SysRoleVo> getRolesByUserId(String userId);
}
