package com.bg.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bg.system.entity.SysRole;
import java.util.List;

/**
 * <pre>
 * 系统角色 Mapper 接口
 * </pre>
 *
 * @author jiewus
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

  /**
   * 根据用户主键获取角色列表
   *
   * @param userId 用户主键
   * @return 角色列表
   */
  List<SysRole> selectRoleListByUserId(String userId);

}
