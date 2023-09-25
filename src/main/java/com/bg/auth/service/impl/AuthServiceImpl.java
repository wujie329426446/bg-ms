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
import com.bg.commons.model.RoleModel;
import com.bg.commons.model.SysUserModel;
import com.bg.commons.model.UserModel;
import com.bg.commons.utils.RedisUtil;
import com.bg.commons.utils.VerificationCode;
import com.bg.system.convert.SysUserConvertMapper;
import com.bg.system.entity.SysUser;
import com.bg.system.service.ISysLogLoginService;
import com.bg.system.service.ISysMenuService;
import com.bg.system.service.ISysRoleService;
import com.bg.system.service.ISysUserService;
import com.bg.system.vo.SysRoleVo;
import com.bg.system.vo.SysUserVo;
import com.google.common.collect.Maps;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
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
  private final ISysUserService sysUserService;
  private final SysUserConvertMapper sysUserConvertMapper;
  private final RedisUtil redisUtil;

  @Override
  public Map<String, String> verify() throws IOException {
    VerificationCode code = new VerificationCode();
    // 生成图片验证码
    BufferedImage image = code.getImage();
    ByteArrayOutputStream out = new ByteArrayOutputStream();//字节数组输出流
    ImageIO.write(image, "png", out);//png 为要保存的图片格式
    String base64 = "data:image/png;base64," + Base64.getEncoder().encodeToString(out.toByteArray());

    // 将验证码存储到缓存中，1分钟有效期
    String codeText = code.getText();
    String uuid = UUID.randomUUID().toString();
    String usernamePasswordCode = VerificationCode.getUsernamePasswordCode(uuid);
    redisUtil.set(usernamePasswordCode, codeText, 1, TimeUnit.MINUTES);

    HashMap<String, String> map = Maps.newHashMap();
    map.put("verifyUUID", uuid);
    map.put("verifyCodeImg", base64);
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
   * @param loginModel 登陆用户信息
   * @return token
   */
  @Override
  public String login(LoginModel loginModel) {
    AbstractAuthenticationToken authenticationToken;

    LoginTypeEnum loginType = loginModel.getLoginType();
    String account = "";

    Authentication authentication = switch (loginType) {
      case USER_NAME -> {
        String username = loginModel.getUsername();
        String password = loginModel.getPassword();
        String verifyCode = loginModel.getVerifyCode();
        String verifyUUID = loginModel.getVerifyUUID();

        account = username;
        authenticationToken = new UsernameAuthenticationToken(username, password, verifyCode, verifyUUID);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        authentication = authenticationManager.authenticate(authenticationToken);
        yield authentication;
      }
      case EMAIL -> {
        String email = loginModel.getEmail();
        String verifyCode = loginModel.getVerifyCode();
        String verifyUUID = loginModel.getVerifyUUID();
        account = email;
        authenticationToken = new EmailAuthenticationToken(email, verifyCode, verifyUUID);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        authentication = authenticationManager.authenticate(authenticationToken);
        yield authentication;
      }
      case PHONE -> {
        String phone = loginModel.getPhone();
        String verifyCode = loginModel.getVerifyCode();
        String verifyUUID = loginModel.getVerifyUUID();
        account = phone;
        authenticationToken = new PhoneAuthenticationToken(phone, verifyCode, verifyUUID);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        authentication = authenticationManager.authenticate(authenticationToken);
        yield authentication;
      }
    };

    UserModel userModel = (UserModel) authentication.getPrincipal();
    String token = jwtAuthenticationTokenHandler.createToken(userModel);

    logLoginService.recordLoginInfo(userModel.getUser().getUserId(), userModel, account, loginType, CommonConstant.SUCCESS_INT, "登陆成功");
    log.info("用户使用 {} 方式登录成功，登录账号：{}", loginType.getDesc(), account);

    return token;
  }

  @Override
  public SysUserVo loadUserByUsername(String username) {
    SysUser one = sysUserService.getOne(Wrappers.lambdaQuery(SysUser.class)
        .eq(SysUser::getUsername, username));
    return sysUserConvertMapper.toDto(one);
  }

  @Override
  public SysUserVo loadUserByEmail(String email) {
    SysUser one = sysUserService.getOne(Wrappers.lambdaQuery(SysUser.class)
        .eq(SysUser::getEmail, email));
    return sysUserConvertMapper.toDto(one);
  }

  @Override
  public SysUserVo loadUserByPhone(String phone) {
    SysUser one = sysUserService.getOne(Wrappers.lambdaQuery(SysUser.class)
        .eq(SysUser::getPhone, phone));
    return sysUserConvertMapper.toDto(one);
  }

  @Override
  public UserModel createUserModel(SysUserVo user, String account, LoginTypeEnum loginType) {
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
