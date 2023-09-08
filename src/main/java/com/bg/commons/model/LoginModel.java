package com.bg.commons.model;

import com.bg.commons.enums.LoginTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

  @Schema(description = "用户名/邮箱/手机")
  @NotBlank(message = "账号不能为空")
  private String username;

  @Schema(description = "密码")
  @NotBlank(message = "密码不能为空")
  private String password;

  @Schema(description = "登陆方式")
  @NotNull(message = "登陆方式不能为空")
  private LoginTypeEnum loginType;

}
