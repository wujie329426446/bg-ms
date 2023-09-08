package com.bg.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bg.commons.pagination.Paging;
import com.bg.system.dto.SysUserDto;
import com.bg.system.entity.SysUser;
import com.bg.system.param.UserPageParam;
import com.bg.system.vo.RouteItemVO;
import com.bg.system.vo.SysUserQueryVo;
import com.bg.system.vo.SysUserSecurityVo;

import java.util.List;
import java.util.Set;

/**
 * User Service 接口
 *
 * @author jiewus
 */
public interface SysUserService extends IService<SysUser> {

  void addUser(SysUserDto sysUserDto) throws Exception;

  /**
   * 修改
   *
   * @return
   * @throws Exception
   */
  boolean updateUser(SysUserDto sysUserDto) throws Exception;

  /**
   * 通过主键查询单条数据
   *
   * @param id 主键
   * @return 用户对象
   */
  SysUser selectUserById(Integer id);

  /**
   * 根据用户名查询单条数据
   *
   * @param username 用户名
   * @return 用户对象
   */
  SysUserSecurityVo selectUserByUsername(String username);

  /**
   * 修改用户信息
   *
   * @param oldUsername 原用户名
   * @param newUsername 新用户名
   * @param email       邮箱
   * @return boolean
   */
  boolean resetUser(String oldUsername, String newUsername, String email);

  /**
   * 获取用户列表
   *
   * @param userPageParam
   * @return
   */
  Paging<SysUserQueryVo> getUserPageList(UserPageParam userPageParam);

  /**
   * 检验部门和角色是否存在并且已启用
   *
   * @param departmentId
   * @param roleId
   * @throws Exception
   */
  void checkDepartmentAndRole(String departmentId, String roleId) throws Exception;

  /**
   * 获取菜单列表
   *
   * @return
   */
  List<RouteItemVO> getMenuList() throws Exception;

  /**
   * 获取权限标识
   *
   * @return
   */
  Set<String> getPermCode() throws Exception;

  /**
   * 删除
   *
   * @param id
   * @return
   * @throws Exception
   */
  boolean deleteSysUser(String id) throws Exception;

  /**
   * 修改用户头像
   *
   * @param userId     用户主键
   * @param avatarPath 头像路径
   * @return 影响行数
   */
  int updateAvatarByUserId(String userId, String avatarPath);
}
