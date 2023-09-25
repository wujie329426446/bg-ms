package com.bg.auth.security.authentication.email;

import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * @Author: jiewus
 * @Description: 邮箱登录token类
 * @Date: 2023/9/25 9:53
 */
public class EmailAuthenticationToken extends AbstractAuthenticationToken {

  @java.io.Serial
  private static final long serialVersionUID = 5746900743403374870L;

  private final transient Object principal;

  private final transient Object credentials;

  private final transient Object verifyCode;

  private final transient Object verifyCodeCacheKey;

  public EmailAuthenticationToken(Object principal, Object credentials) {
    super(null);
    this.principal = principal;
    this.credentials = credentials;
    this.verifyCode = null;
    this.verifyCodeCacheKey = null;
    setAuthenticated(false);
  }

  public EmailAuthenticationToken(Object principal, Object verifyCode, Object verifyCodeCacheKey) {
    super(null);
    this.principal = principal;
    this.credentials = null;
    this.verifyCode = verifyCode;
    this.verifyCodeCacheKey = verifyCodeCacheKey;
    setAuthenticated(false);
  }

  public EmailAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
    super(authorities);
    this.principal = principal;
    this.credentials = null;
    this.verifyCode = null;
    this.verifyCodeCacheKey = null;
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

  public Object getVerifyCode() {
    return this.verifyCode;
  }

  public Object getVerifyCodeCacheKey() {
    return this.verifyCodeCacheKey;
  }

}
