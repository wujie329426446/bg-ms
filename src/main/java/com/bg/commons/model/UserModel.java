package com.bg.commons.model;

import com.bg.commons.enums.LoginTypeEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * 登录用户身份权限
 *
 * @author jiewus
 */
@Data
public class UserModel implements UserDetails {

  @Serial
  private static final long serialVersionUID = 1700642694582927208L;

  @Schema(description = "ip")
  private String ip;

  @Schema(description = "地址")
  private String location;

  @Schema(description = "是否为移动平台")
  private boolean isMobile;

  @Schema(description = "浏览器类型")
  private String browser;

  @Schema(description = "浏览器版本")
  private String version;

  @Schema(description = "平台类型")
  private String platform;

  @Schema(description = "系统类型")
  private String os;

  @Schema(description = "系统版本")
  private String osVersion;

  @Schema(description = "引擎类型")
  private String engine;

  @Schema(description = "引擎版本")
  private String engineVersion;

  @Schema(description = "登陆方式")
  private LoginTypeEnum loginType;

  @Schema(description = "登陆时间")
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime loginTime;

  @Schema(description = "过期时间")
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime expireTime;

  @Schema(description = "用户唯一标识")
  private String token;

  @Schema(description = "角色集合")
  private List<RoleModel> roles;

  @Schema(description = "权限集合")
  private Set<String> permissions;

  @Schema(description = "用户信息")
  private SysUserModel user;

  @JsonIgnore
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.emptyList();
  }

  @JsonIgnore
  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @JsonIgnore
  @Override
  public String getUsername() {
    return user.getUsername();
  }

  @JsonIgnore
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @JsonIgnore
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @JsonIgnore
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @JsonIgnore
  @Override
  public boolean isEnabled() {
    return true;
  }

}
