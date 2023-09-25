package com.bg.auth.security.authentication.email;

import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * @Author: issuser
 * @Description: 邮箱登录token类
 * @Date: 2023/9/25 9:53
 */
public class EmailVerificationCodeAuthenticationToken extends AbstractAuthenticationToken {

  @java.io.Serial
  private static final long serialVersionUID = 5746900743403374870L;

  private final transient Object principal;

  private final transient Object credentials;

  public EmailVerificationCodeAuthenticationToken(Object principal, Object credentials) {
    super(null);
    this.principal = principal;
    this.credentials = credentials;
    setAuthenticated(false);
  }

  public EmailVerificationCodeAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
    super(authorities);
    this.principal = principal;
    this.credentials = null;
    super.setAuthenticated(true);
  }

  @Override
  public Object getCredentials() {
    return this.credentials;
  }

  @Override
  public Object getPrincipal() {
    return this.principal;
  }
}
