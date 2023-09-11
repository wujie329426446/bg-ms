package com.bg.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bg.system.entity.SysUser;
import com.bg.system.param.UserPageParam;
import com.bg.system.vo.SysUserVo;
import org.apache.ibatis.annotations.Param;

/**
 * User Mapper
 *
 * @author jiewus
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

  /**
   * 获取分页对象
   *
   * @param page
   * @param sysUserPageParam
   * @return
   */
  Page<SysUserVo> getSysUserPageList(@Param("page") Page page, @Param("param") UserPageParam sysUserPageParam);

  /**
   * 根据用户名查询单条数据
   *
   * @param username 用户名
   * @return 用户对象
   */
  SysUserVo selectUserByUsername(String username);

  /**
   * 修改用户头像
   *
   * @param userId     用户主键
   * @param avatarPath 头像路径
   * @return 影响行数
   */
  int updateAvatarByUserId(@Param("userId") String userId, @Param("avatarPath") String avatarPath);

}
