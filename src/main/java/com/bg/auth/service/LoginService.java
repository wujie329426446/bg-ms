package com.bg.auth.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.bg.auth.security.authentication.email.EmailVerificationCodeAuthenticationToken;
import com.bg.auth.security.authentication.username.UsernameAuthenticationToken;
import com.bg.auth.security.filter.JwtAuthenticationTokenHandler;
import com.bg.commons.api.ApiCode;
import com.bg.commons.constant.CommonConstant;
import com.bg.commons.enums.LoginTypeEnum;
import com.bg.commons.exception.BusinessException;
import com.bg.commons.model.DeptModel;
import com.bg.commons.model.LoginModel;
import com.bg.commons.model.RoleModel;
import com.bg.commons.model.SysUserModel;
import com.bg.commons.model.UserModel;
import com.bg.system.convert.SysUserConvertMapper;
import com.bg.system.entity.SysUser;
import com.bg.system.service.ISysLogLoginService;
import com.bg.system.service.ISysMenuService;
import com.bg.system.service.ISysRoleService;
import com.bg.system.service.ISysUserService;
import com.bg.system.vo.SysRoleVo;
import com.bg.system.vo.SysUserVo;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * 登陆服务
 *
 * @author jiewus
 */
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
public class LoginService {

  private final AuthenticationManager authenticationManager;
  private final JwtAuthenticationTokenHandler jwtAuthenticationTokenHandler;
  private final ISysLogLoginService logLoginService;
  private final ISysRoleService roleService;
  private final ISysMenuService menuService;
  private final ISysUserService sysUserService;
  private final SysUserConvertMapper sysUserConvertMapper;

  /**
   * 登陆
   *
   * @param loginModel 登陆用户信息
   * @return token
   */
  public String login(LoginModel loginModel) {
    AbstractAuthenticationToken authenticationToken;

    LoginTypeEnum loginType = loginModel.getLoginType();
    String account = "";

    Authentication authentication = switch (loginType) {
      case USER_NAME -> {
        String username = loginModel.getUsername();
        String password = loginModel.getPassword();
        account = username;
        authenticationToken = new UsernameAuthenticationToken(username, password);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        authentication = authenticationManager.authenticate(authenticationToken);
        yield authentication;
      }
      case EMAIL -> {
        String email = loginModel.getEmail();
        String emailCode = loginModel.getEmailCode();
        account = email;
        authenticationToken = new EmailVerificationCodeAuthenticationToken(email, emailCode);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        authentication = authenticationManager.authenticate(authenticationToken);
        yield authentication;
      }
      case PHONE -> {
        // TODO 手机登录
        throw BusinessException.build(ApiCode.LOGIN_EXCEPTION.getCode(), "暂未开放手机登录方式");
      }
    };

    UserModel userModel = (UserModel) authentication.getPrincipal();
    String token = jwtAuthenticationTokenHandler.createToken(userModel);

    logLoginService.recordLoginInfo(userModel.getUser().getUserId(), userModel, account, loginType, CommonConstant.SUCCESS_INT, "登陆成功");
    log.info("用户使用 {} 方式登录成功，登录账号：{}", loginType.getDesc(), account);

    return token;
  }

  public SysUserVo loadUserByUsername(String username) {
    SysUser one = sysUserService.getOne(Wrappers.lambdaQuery(SysUser.class)
        .eq(SysUser::getUsername, username));
    return sysUserConvertMapper.toDto(one);
  }

  public SysUserVo loadUserByEmail(String email) {
    SysUser one = sysUserService.getOne(Wrappers.lambdaQuery(SysUser.class)
        .eq(SysUser::getEmail, email));
    return sysUserConvertMapper.toDto(one);
  }

  public UserModel createUserModel(SysUserVo user, String password, String account, LoginTypeEnum loginType) {
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
    Set<String> permissions = menuService.getCodesByUser(user.getId());

    // 将信息放进登陆用户模型
    userModel.setLoginType(loginType);
    userModel.setUser(sysUserModel);
    userModel.setRoles(roleModelList);
    userModel.setPermissions(permissions);
    return userModel;
  }

}
