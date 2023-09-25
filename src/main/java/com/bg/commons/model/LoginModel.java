package com.bg.commons.model;

import com.bg.commons.core.validator.groups.login.EmailLogin;
import com.bg.commons.core.validator.groups.login.PhoneLogin;
import com.bg.commons.core.validator.groups.login.UsernamePasswordLogin;
import com.bg.commons.enums.LoginTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import java.io.Serial;
import java.io.Serializable;
import lombok.Data;

/**
 * 登陆模型
 *
 * @author Tang
 */
@Data
public class LoginModel implements Serializable {

  @Serial
  private static final long serialVersionUID = -5428882047565357006L;

  @Schema(description = "用户名")
  @NotBlank(message = "账号不能为空", groups = UsernamePasswordLogin.class)
  private String username;

  @Schema(description = "密码")
  @NotBlank(message = "密码不能为空", groups = UsernamePasswordLogin.class)
  private String password;

  @Schema(description = "邮箱")
  @NotBlank(message = "邮箱不能为空", groups = EmailLogin.class)
  private String email;

  @Schema(description = "手机")
  @NotBlank(message = "手机不能为空", groups = PhoneLogin.class)
  private String phone;

  @Schema(description = "验证码")
  @NotBlank(message = "验证码不能为空", groups = {EmailLogin.class, PhoneLogin.class})
  private String verifyCode;

  @Schema(description = "验证码id")
  @NotBlank(message = "验证码id不能为空", groups = {EmailLogin.class, PhoneLogin.class})
  private String verifyUUID;

  @Schema(description = "登陆方式", hidden = true)
  private LoginTypeEnum loginType;

}
