package com.bg.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bg.system.entity.SysUser;
import com.bg.system.param.UserPageParam;
import com.bg.system.vo.RouteItemVO;
import com.bg.system.vo.SysUserVo;
import java.util.List;

/**
 * User Service 接口
 *
 * @author jiewus
 */
public interface SysUserService extends IService<SysUser> {

  /**
   * 根据用户名查询单条数据
   *
   * @param username 用户名
   * @return 用户对象
   */
  SysUserVo selectUserByUsername(String username);

  /**
   * 根据id查询用户对象
   *
   * @param id
   * @return 用户对象
   */
  SysUserVo getUserById(String id);

  /**
   * 获取用户列表
   *
   * @param pageParam
   * @return
   */
  Page<SysUserVo> getPageList(UserPageParam pageParam);

  /**
   * 获取菜单列表
   *
   * @return
   */
  List<RouteItemVO> getMenuList();

}
