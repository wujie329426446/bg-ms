package com.bg.auth.service.authentication;

import com.bg.auth.exception.LoginException;
import com.bg.commons.constant.Status;
import com.bg.commons.enums.LoginTypeEnum;
import com.bg.commons.model.DeptModel;
import com.bg.commons.model.RoleModel;
import com.bg.commons.model.SysUserModel;
import com.bg.commons.model.UserModel;
import com.bg.commons.utils.SecurityUtil;
import com.bg.system.service.ISysMenuService;
import com.bg.system.service.ISysRoleService;
import com.bg.system.vo.SysRoleVo;
import com.bg.system.vo.SysUserVo;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/**
 * 用户权限
 *
 * @author jiewus
 */
@Component
public class AuthenticationService implements UserModelProvider {

  private final ISysRoleService roleService;

  private final ISysMenuService menuService;

  public AuthenticationService(ISysRoleService roleService, ISysMenuService menuService) {
    this.roleService = roleService;
    this.menuService = menuService;
  }

  /**
   * 创建用户模型
   *
   * @param user      用户信息
   * @param password  明文密码
   * @param loginType 登陆类型
   * @return 用户模型
   */
  public UserModel createUserModel(SysUserVo user, String password, String account, LoginTypeEnum loginType) {

    if (user == null) {
      throw new LoginException("用户不存在");
    }
    if (!SecurityUtil.matchesPassword(password, user.getPassword())) {
      throw new LoginException("密码错误");
    }
    if (user.getStatus().equals(Status.DISABLED)) {
      throw new LoginException("账号已停用");
    }

    UserModel userModel = new UserModel();
    // 用户对象
    SysUserModel sysUserModel = new SysUserModel();
    BeanUtils.copyProperties(user, sysUserModel);
    if (Objects.nonNull(user.getDept())) {
      DeptModel deptModel = new DeptModel();
      BeanUtils.copyProperties(user.getDept(), deptModel);
      sysUserModel.setDept(deptModel);
    }
    sysUserModel.setRoleIds(roleService.getRoleIdsByUserId(user.getId()));

    // 获取当前用户角色
    List<SysRoleVo> roleList = roleService.getRolesByUserId(user.getId());

    List<RoleModel> roleModelList = roleList.stream().map(
        item -> {
          RoleModel roleModel = new RoleModel();
          roleModel.setRoleName(item.getRoleName());
          roleModel.setValue(item.getRoleCode());
          roleModel.setId(item.getId());
          return roleModel;
        }
    ).toList();

    // 权限集合
    Set<String> permissions = menuService.getPermissionCodesByUserId(user.getId());

    // 将信息放进登陆用户模型
    userModel.setLoginType(loginType);
    userModel.setUser(sysUserModel);
    userModel.setRoles(roleModelList);
    userModel.setPermissions(permissions);
    return userModel;
  }

}
