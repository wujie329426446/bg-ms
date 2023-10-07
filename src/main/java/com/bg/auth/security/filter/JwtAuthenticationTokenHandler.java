package com.bg.auth.security.filter;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.bg.commons.constant.CachePrefix;
import com.bg.commons.model.LoginModel;
import com.bg.commons.utils.IdUtil;
import com.bg.commons.utils.IpRegionUtil;
import com.bg.commons.utils.IpUtil;
import com.bg.commons.utils.RedisUtil;
import com.bg.commons.utils.ServletUtil;
import com.bg.config.properties.TokenProperties;
import com.bg.system.entity.SysUser;
import com.bg.system.mapper.SysUserMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * token 验证处理
 *
 * @author jiewus
 */
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class JwtAuthenticationTokenHandler {

  private static final String LOGIN_USER_KEY = "login_user_key";

  private static final String TOKEN_PREFIX = "Bearer ";

  private final RedisUtil redisUtil;

  private final TokenProperties tokenProperties;

  private final SysUserMapper userMapper;

  /**
   * 获取登陆用户身份信息
   *
   * @param request request
   * @return 登陆用户信息
   */
  public LoginModel get(@NonNull HttpServletRequest request) {
    // 获取请求携带的令牌
    String token = getToken(request);
    if (StringUtils.isNotEmpty(token)) {
      Claims claims = parseToken(token);
      // 解析对应的权限以及登陆用户信息
      String uuid = (String) claims.get(LOGIN_USER_KEY);
      String userKey = getTokenKey(uuid);
      LoginModel loginModel = (LoginModel) redisUtil.get(userKey);
      return loginModel;
    }
    return null;
  }

  /**
   * 删除登陆用户身份信息
   *
   * @param token 令牌
   */
  public void delete(String token) {
    if (StringUtils.isNotEmpty(token)) {
      String userKey = getTokenKey(token);
      redisUtil.delete(userKey);
    }
  }

  /**
   * 创建令牌
   *
   * @param loginModel 登陆用户信息
   * @return 令牌
   */
  public String createToken(@NonNull LoginModel loginModel) {
    String token = String.valueOf(IdUtil.snowflake());
    loginModel.setToken(token);
    setUserAgent(loginModel);
    set(loginModel);
    HashMap<String, Object> claims = new HashMap<String, Object>(16);
    claims.put(LOGIN_USER_KEY, token);
    return createToken(claims);
  }

  /**
   * 设置登陆用户身份信息
   *
   * @param loginModel 登陆用户信息
   */
  public void set(@NonNull LoginModel loginModel) {
    if (StringUtils.isNotEmpty(loginModel.getToken())) {
      refreshToken(loginModel);
    }
  }

  /**
   * 验证令牌有效期
   *
   * @param loginModel 登陆用户信息
   */
  public void verifyToken(@NonNull LoginModel loginModel) {
    refreshToken(loginModel);
  }

  /**
   * 刷新令牌有效期
   *
   * @param loginModel 登陆用户信息
   */
  public void refreshToken(@NonNull LoginModel loginModel) {
    loginModel.setExpireTime(LocalDateTime.now().plus(Duration.ofMillis(tokenProperties.getExpireTimeLong())));
    // 缓存登陆信息
    String userKey = getTokenKey(loginModel.getToken());
    redisUtil.set(userKey, loginModel, tokenProperties.getExpireTime(), tokenProperties.getTimeUnit());
  }

  /**
   * 设置登陆用户代理信息
   *
   * @param loginModel 登陆用户信息
   */
  public void setUserAgent(@NonNull LoginModel loginModel) {
    UserAgent userAgent = UserAgentUtil.parse(ServletUtil.getRequest().getHeader("User-Agent"));
    loginModel.setIp(IpUtil.getRequestIp(ServletUtil.getRequest()));
    loginModel.setLocation(IpRegionUtil.getIpRegion(loginModel.getIp()).getCity());
    loginModel.setMobile(userAgent.isMobile());
    loginModel.setBrowser(userAgent.getBrowser().getName());
    loginModel.setVersion(userAgent.getVersion());
    loginModel.setPlatform(userAgent.getPlatform().getName());
    loginModel.setOs(userAgent.getOs().getName());
    loginModel.setOsVersion(userAgent.getOsVersion());
    loginModel.setEngine(userAgent.getEngine().getName());
    loginModel.setEngineVersion(userAgent.getEngineVersion());
    loginModel.setLoginTime(LocalDateTime.now());
    updateUserLoginInfo(loginModel);
  }

  /**
   * 获取登陆用户代理信息
   *
   * @return 登陆用户信息
   */
  public LoginModel getUserAgent() {
    LoginModel loginModel = new LoginModel();
    UserAgent userAgent = UserAgentUtil.parse(ServletUtil.getRequest().getHeader("User-Agent"));
    loginModel.setIp(IpUtil.getRequestIp(ServletUtil.getRequest()));
    loginModel.setLocation(IpRegionUtil.getIpRegion((loginModel.getIp())).getCity());
    loginModel.setMobile(userAgent.isMobile());
    loginModel.setBrowser(userAgent.getBrowser().getName());
    loginModel.setVersion(userAgent.getVersion());
    loginModel.setPlatform(userAgent.getPlatform().getName());
    loginModel.setOs(userAgent.getOs().getName());
    loginModel.setOsVersion(userAgent.getOsVersion());
    loginModel.setEngine(userAgent.getEngine().getName());
    loginModel.setEngineVersion(userAgent.getEngineVersion());
    loginModel.setLoginTime(LocalDateTime.now());
    return loginModel;
  }

  /**
   * 更新用户登陆信息
   *
   * @param loginModel 登陆用户信息
   */
  private void updateUserLoginInfo(@NonNull LoginModel loginModel) {
    SysUser user = new SysUser();
    user.setId(loginModel.getUser().getId());
    user.setLoginIp(loginModel.getIp());
    user.setLoginDate(loginModel.getLoginTime());
    userMapper.updateById(user);
  }

  /**
   * 从数据声明生成令牌
   *
   * @param claims 数据
   * @return 令牌
   */
  private String createToken(Map<String, Object> claims) {
    return Jwts.builder()
        .setClaims(claims)
        .signWith(getSecretKey())
        .compact();
  }

  /**
   * 从令牌中获取数据声明
   *
   * @param token 令牌
   * @return 数据声明
   */
  public Claims parseToken(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(getSecretKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  /**
   * 生成密钥
   *
   * @return 密钥
   */
  private SecretKey getSecretKey() {
    byte[] secretBytes = Decoders.BASE64.decode(tokenProperties.getSecret());
    return Keys.hmacShaKeyFor(secretBytes);
  }

  /**
   * 获取请求token
   *
   * @param request request
   * @return 令牌
   */
  private String getToken(@NonNull HttpServletRequest request) {
    String token = request.getHeader(tokenProperties.getHeader());
    if (StringUtils.isNotEmpty(token) && token.startsWith(TOKEN_PREFIX)) {
      token = token.replace(TOKEN_PREFIX, "");
    }
    return token;
  }

  /**
   * 获取 redis 键
   *
   * @param uuid 唯一标识
   * @return 键
   */
  private String getTokenKey(String uuid) {
    return CachePrefix.LOGIN_TOKENS + uuid;
  }

}
