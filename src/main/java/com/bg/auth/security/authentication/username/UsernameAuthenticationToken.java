package com.bg.auth.security.authentication.username;

import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * 用户名密码身份令牌
 *
 * @author jiewus
 */
public class UsernameAuthenticationToken extends AbstractAuthenticationToken {

  @java.io.Serial
  private static final long serialVersionUID = 5746900743403374870L;

  private final transient Object principal;

  private final transient Object credentials;

  private final transient Object verifyCode;

  private final transient Object verifyCodeCacheKey;

  public UsernameAuthenticationToken(Object principal, Object credentials) {
    super(null);
    this.principal = principal;
    this.credentials = credentials;
    this.verifyCode = null;
    this.verifyCodeCacheKey = null;
    setAuthenticated(false);
  }

  public UsernameAuthenticationToken(Object principal, Object credentials, Object verifyCode, Object verifyCodeCacheKey) {
    super(null);
    this.principal = principal;
    this.credentials = credentials;
    this.verifyCode = verifyCode;
    this.verifyCodeCacheKey = verifyCodeCacheKey;
    setAuthenticated(false);
  }

  public UsernameAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
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
