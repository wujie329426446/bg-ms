package com.bg.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bg.system.entity.SysUser;
import com.bg.system.param.UserPageParam;
import com.bg.system.vo.SysUserVo;

/**
 * User Service 接口
 *
 * @author jiewus
 */
public interface ISysUserService extends IService<SysUser> {


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
  Page<SysUserVo> getUserPageList(UserPageParam pageParam);

}
