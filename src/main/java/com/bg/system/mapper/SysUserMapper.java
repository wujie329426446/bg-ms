package com.bg.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bg.system.entity.SysUser;
import com.bg.system.param.UserPageParam;
import com.bg.system.param.QueryParam;
import com.bg.system.vo.SysUserQueryVo;
import com.bg.system.vo.SysUserSecurityVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * User Mapper
 *
 * @author jiewus
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

  IPage<SysUser> queryByParam(@Param("pg") Page<SysUser> page, @Param("queryParam") QueryParam queryParam);


  /**
   * 获取分页对象
   *
   * @param page
   * @param sysUserPageParam
   * @return
   */
  IPage<SysUserQueryVo> getSysUserPageList(@Param("page") Page page, @Param("param") UserPageParam sysUserPageParam);

  @Insert("INSERT INTO USER(username, password) VALUES(#{username,jdbcType=VARCHAR}, #{password,jdbcType=VARCHAR})")
  int insertByMap(Map<String, Object> map);

  @Insert("INSERT INTO USER(username, password) VALUES(#{username}, #{password})")
  int insertByObject(SysUser sysUser);

  /**
   * 根据用户名查询单条数据
   *
   * @param username 用户名
   * @return 用户对象
   */
  SysUserSecurityVo selectUserByUsername(String username);

  /**
   * 修改用户头像
   *
   * @param userId     用户主键
   * @param avatarPath 头像路径
   * @return 影响行数
   */
  int updateAvatarByUserId(@Param("userId") String userId, @Param("avatarPath") String avatarPath);
}
