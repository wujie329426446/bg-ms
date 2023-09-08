package com.bg.auth.service.authentication;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bg.auth.exception.LoginException;
import com.bg.auth.service.TokenService;
import com.bg.commons.constant.Status;
import com.bg.commons.enums.LoginTypeEnum;
import com.bg.commons.model.SysDeptModel;
import com.bg.commons.model.SysUserModel;
import com.bg.commons.model.UserModel;
import com.bg.commons.vo.RoleInfoVO;
import com.bg.commons.utils.SecurityUtil;
import com.bg.system.entity.SysRole;
import com.bg.system.entity.SysUserRole;
import com.bg.system.service.SysLogLoginService;
import com.bg.system.service.SysMenuService;
import com.bg.system.service.SysRoleService;
import com.bg.system.vo.SysUserSecurityVo;
import java.util.Objects;
import java.util.Set;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 用户权限
 *
 * @author jiewus
 */
@Component
public class AuthenticationService implements UserModelProvider {

  private final SysRoleService roleService;

  private final SysMenuService menuService;

  private final SysLogLoginService logLoginService;

  private final TokenService tokenService;

  public AuthenticationService(SysRoleService roleService, SysMenuService menuService, SysLogLoginService logLoginService, TokenService tokenService) {
    this.roleService = roleService;
    this.menuService = menuService;
    this.logLoginService = logLoginService;
    this.tokenService = tokenService;
  }

  /**
   * 创建用户模型
   *
   * @param user      用户信息
   * @param password  明文密码
   * @param loginType 登陆类型
   * @return 用户模型
   */
  public UserModel createUserModel(SysUserSecurityVo user, String password, String account, LoginTypeEnum loginType) {

    if (user == null) {
      throw new LoginException("用户不存在");
    }
    if (!SecurityUtil.matchesPassword(password, user.getPassword())) {
      throw new LoginException("密码错误");
    }
    if (user.getStatus().equals(Status.DISABLED)) {
      throw new LoginException("账号已停用");
    }
    if (user.getDeleted().equals(Status.DELETED)) {
      throw new LoginException("账号已删除");
    }

    UserModel userModel = new UserModel();
    // 用户对象
    SysUserModel sysUserModel = new SysUserModel();
    BeanUtils.copyProperties(user, sysUserModel);
    if (Objects.nonNull(user.getDept())) {
      SysDeptModel sysDeptModel = new SysDeptModel();
      BeanUtils.copyProperties(user.getDept(), sysDeptModel);
      sysUserModel.setDept(sysDeptModel);
    }
    sysUserModel.setRoleIds(roleService.getRoleIdsByUserId(user.getUserId()));
    // 角色集合
    Set<String> roles = roleService.getRolesByUserId(user.getUserId());

    // 获取当前用户角色
    List<SysUserRole> sysUserRoles = new SysUserRole().selectList(new QueryWrapper<SysUserRole>().lambda()
        .eq(SysUserRole::getUserId, user.getUserId()));

    List<RoleInfoVO> roleInfoVOList = sysUserRoles.stream().map(
        item -> {
          SysRole sysRole = roleService.getById(item.getRoleId());
//                    if (sysRole == null) {
//                        throw new AuthenticationException("角色不存在");
//                    }
//                    if (StateEnum.DISABLE.getCode().equals(sysRole.getStatus())) {
//                        throw new AuthenticationException("角色已禁用");
//                    }
          RoleInfoVO roleInfoVO = new RoleInfoVO();
          roleInfoVO.setRoleName(sysRole.getRoleName());
          roleInfoVO.setValue(sysRole.getRoleCode());
          roleInfoVO.setId(sysRole.getId());
          return roleInfoVO;
        }
    ).toList();

    // 权限集合
    Set<String> permissions = menuService.getPermissionCodesByUserId(user.getUserId());
    // TODO: 2023/6/9 管理员角色默认获得所有权限
//        if (roles.contains(SecurityUtils.ADMIN_ROLE_KEY)) {
//            permissions = Set.of(SecurityUtils.ALL_PERMISSIONS);
//        }
    // 将信息放进登陆用户模型
    userModel.setLoginType(loginType);
    userModel.setUser(sysUserModel);
    userModel.setRoles(roleInfoVOList);
    userModel.setPermissions(permissions);
    return userModel;
  }

}
