package com.bg.auth.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.bg.auth.security.authentication.email.EmailAuthenticationToken;
import com.bg.auth.security.authentication.phone.PhoneAuthenticationToken;
import com.bg.auth.security.authentication.username.UsernameAuthenticationToken;
import com.bg.auth.security.filter.JwtAuthenticationTokenHandler;
import com.bg.auth.service.IAuthService;
import com.bg.commons.constant.CommonConstant;
import com.bg.commons.enums.LoginTypeEnum;
import com.bg.commons.model.DeptModel;
import com.bg.commons.model.LoginModel;
import com.bg.commons.model.LoginParam;
import com.bg.commons.model.RoleModel;
import com.bg.commons.model.UserModel;
import com.bg.commons.utils.RedisUtil;
import com.bg.commons.utils.VerifyCodeUtil;
import com.bg.system.convert.SysUserConvertMapper;
import com.bg.system.entity.SysDept;
import com.bg.system.entity.SysUser;
import com.bg.system.service.ISysDeptService;
import com.bg.system.service.ISysLogLoginService;
import com.bg.system.service.ISysMenuService;
import com.bg.system.service.ISysRoleService;
import com.bg.system.service.ISysUserService;
import com.bg.system.vo.SysRoleVo;
import com.google.common.collect.Maps;
import com.wf.captcha.SpecCaptcha;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
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
public class AuthServiceImpl implements IAuthService {

  private final AuthenticationManager authenticationManager;
  private final JwtAuthenticationTokenHandler jwtAuthenticationTokenHandler;
  private final ISysLogLoginService logLoginService;
  private final ISysRoleService roleService;
  private final ISysMenuService menuService;
  private final ISysDeptService deptService;
  private final ISysUserService sysUserService;
  private final SysUserConvertMapper sysUserConvertMapper;
  private final RedisUtil redisUtil;

  @Override
  public Map<String, String> verify() {
    SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
    // 生成图片验证码
    String verCode = specCaptcha.text().toLowerCase();
    // 将验证码存储到缓存中，1分钟有效期
    String uuid = UUID.randomUUID().toString();
    String usernamePasswordCode = VerifyCodeUtil.getUsernamePasswordCode(uuid);
    redisUtil.set(usernamePasswordCode, verCode, 5, TimeUnit.MINUTES);

    HashMap<String, String> map = Maps.newHashMap();
    map.put("verifyUUID", uuid);
    map.put("verifyCodeImg", specCaptcha.toBase64());
    return map;
  }

  @Override
  public Map<String, String> emailVerify(String email) {
    //TODO 生成验证码并发送邮箱
    return null;
  }

  @Override
  public Map<String, String> phoneVerify(String phone) {
    //TODO 生成验证码并发送手机短信
    return null;
  }

  /**
   * 登陆
   *
   * @param loginParam 登陆用户信息
   * @return token
   */
  @Override
  public String login(LoginParam loginParam) {
    AbstractAuthenticationToken authenticationToken;

    LoginTypeEnum loginType = loginParam.getLoginType();
    String account = "";

    Authentication authentication = switch (loginType) {
      case USER_NAME -> {
        String username = loginParam.getUsername();
        String password = loginParam.getPassword();
        String verifyCode = loginParam.getVerifyCode();
        String verifyUUID = loginParam.getVerifyUUID();

        account = username;
        authenticationToken = new UsernameAuthenticationToken(username, password, verifyCode, verifyUUID);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        authentication = authenticationManager.authenticate(authenticationToken);
        yield authentication;
      }
      case EMAIL -> {
        String email = loginParam.getEmail();
        String verifyCode = loginParam.getVerifyCode();
        String verifyUUID = loginParam.getVerifyUUID();
        account = email;
        authenticationToken = new EmailAuthenticationToken(email, verifyCode, verifyUUID);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        authentication = authenticationManager.authenticate(authenticationToken);
        yield authentication;
      }
      case PHONE -> {
        String phone = loginParam.getPhone();
        String verifyCode = loginParam.getVerifyCode();
        String verifyUUID = loginParam.getVerifyUUID();
        account = phone;
        authenticationToken = new PhoneAuthenticationToken(phone, verifyCode, verifyUUID);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        authentication = authenticationManager.authenticate(authenticationToken);
        yield authentication;
      }
    };

    LoginModel loginModel = (LoginModel) authentication.getPrincipal();
    String token = jwtAuthenticationTokenHandler.createToken(loginModel);

    logLoginService.recordLoginInfo(loginModel.getUser().getId(), loginModel, account, loginType, CommonConstant.SUCCESS_INT, "登陆成功");
    log.info("用户使用 {} 方式登录成功，登录账号：{}", loginType.getDesc(), account);

    return token;
  }

  @Override
  public SysUser loadUserByUsername(String username) {
    SysUser one = sysUserService.getOne(Wrappers.lambdaQuery(SysUser.class)
        .eq(SysUser::getUsername, username));
    return one;
  }

  @Override
  public SysUser loadUserByEmail(String email) {
    SysUser one = sysUserService.getOne(Wrappers.lambdaQuery(SysUser.class)
        .eq(SysUser::getEmail, email));
    return one;
  }

  @Override
  public SysUser loadUserByPhone(String phone) {
    SysUser one = sysUserService.getOne(Wrappers.lambdaQuery(SysUser.class)
        .eq(SysUser::getPhone, phone));
    return one;
  }

  @Override
  public LoginModel createUserModel(SysUser user, String account, LoginTypeEnum loginType) {
    LoginModel loginModel = jwtAuthenticationTokenHandler.getUserAgent();
    // 用户对象
    UserModel userModel = new UserModel();
    BeanUtils.copyProperties(user, userModel);
    // 获取部门信息
    SysDept sysDept = deptService.getById(user.getDeptId());
    DeptModel deptModel = new DeptModel();
    BeanUtils.copyProperties(sysDept, deptModel);
    userModel.setDept(deptModel);

//    if (Objects.nonNull(user.getDept())) {
//      DeptModel deptModel = new DeptModel();
//      BeanUtils.copyProperties(user.getDept(), deptModel);
//      userModel.setDept(deptModel);
//    }
    userModel.setRoleIds(roleService.getRoleIdsByUserId(user.getId()));

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
    loginModel.setLoginType(loginType);
    loginModel.setUser(userModel);
    loginModel.setRoles(roleModelList);
    loginModel.setPermissions(permissions);
    return loginModel;
  }

}
