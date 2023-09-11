package com.bg.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bg.system.entity.SysMenu;
import com.bg.system.param.SysPermissionPageParam;
import com.bg.system.vo.SysPermissionVo;
import java.io.Serializable;
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
   * 根据ID获取查询对象
   *
   * @param id
   * @return
   */
  SysPermissionVo getSysPermissionById(Serializable id);

  /**
   * 获取分页对象
   *
   * @param page
   * @param sysPermissionPageParam
   * @return
   */
  Page<SysPermissionVo> getSysPermissionPageList(@Param("page") Page page, @Param("param") SysPermissionPageParam sysPermissionPageParam);

  /**
   * 根据用户id获取该用户所有权限编码
   *
   * @param userId
   * @return
   * @throws Exception
   */
  Set<String> getPermissionCodesByUserId(@Param("userId") String userId);

  /**
   * 根据用户id获取菜单列表
   *
   * @param userId
   * @return
   */
  List<SysMenu> getMenuListByUserId(@Param("userId") String userId);
}
