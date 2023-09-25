package com.bg.auth.security.filter;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.bg.commons.constant.CachePrefix;
import com.bg.commons.model.UserModel;
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
  public UserModel get(@NonNull HttpServletRequest request) {
    // 获取请求携带的令牌
    String token = getToken(request);
    if (StringUtils.isNotEmpty(token)) {
      Claims claims = parseToken(token);
      // 解析对应的权限以及登陆用户信息
      String uuid = (String) claims.get(LOGIN_USER_KEY);
      String userKey = getTokenKey(uuid);
      UserModel userModel = (UserModel) redisUtil.get(userKey);
      return userModel;
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
   * @param userModel 登陆用户信息
   * @return 令牌
   */
  public String createToken(@NonNull UserModel userModel) {
    String token = String.valueOf(IdUtil.snowflake());
    userModel.setToken(token);
    setUserAgent(userModel);
    set(userModel);
    HashMap<String, Object> claims = new HashMap<String, Object>(16);
    claims.put(LOGIN_USER_KEY, token);
    return createToken(claims);
  }

  /**
   * 设置登陆用户身份信息
   *
   * @param userModel 登陆用户信息
   */
  public void set(@NonNull UserModel userModel) {
    if (StringUtils.isNotEmpty(userModel.getToken())) {
      refreshToken(userModel);
    }
  }

  /**
   * 验证令牌有效期
   *
   * @param userModel 登陆用户信息
   */
  public void verifyToken(@NonNull UserModel userModel) {
    refreshToken(userModel);
  }

  /**
   * 刷新令牌有效期
   *
   * @param userModel 登陆用户信息
   */
  public void refreshToken(@NonNull UserModel userModel) {
    userModel.setExpireTime(LocalDateTime.now().plus(Duration.ofMillis(tokenProperties.getExpireTimeLong())));
    // 缓存登陆信息
    String userKey = getTokenKey(userModel.getToken());
    redisUtil.set(userKey, userModel, tokenProperties.getExpireTime(), tokenProperties.getTimeUnit());
  }

  /**
   * 设置登陆用户代理信息
   *
   * @param userModel 登陆用户信息
   */
  public void setUserAgent(@NonNull UserModel userModel) {
    UserAgent userAgent = UserAgentUtil.parse(ServletUtil.getRequest().getHeader("User-Agent"));
    userModel.setIp(IpUtil.getRequestIp(ServletUtil.getRequest()));
    userModel.setLocation(IpRegionUtil.getIpRegion(userModel.getIp()).getCity());
    userModel.setMobile(userAgent.isMobile());
    userModel.setBrowser(userAgent.getBrowser().getName());
    userModel.setVersion(userAgent.getVersion());
    userModel.setPlatform(userAgent.getPlatform().getName());
    userModel.setOs(userAgent.getOs().getName());
    userModel.setOsVersion(userAgent.getOsVersion());
    userModel.setEngine(userAgent.getEngine().getName());
    userModel.setEngineVersion(userAgent.getEngineVersion());
    userModel.setLoginTime(LocalDateTime.now());
    updateUserLoginInfo(userModel);
  }

  /**
   * 获取登陆用户代理信息
   *
   * @return 登陆用户信息
   */
  public UserModel getUserAgent() {
    UserModel userModel = new UserModel();
    UserAgent userAgent = UserAgentUtil.parse(ServletUtil.getRequest().getHeader("User-Agent"));
    userModel.setIp(IpUtil.getRequestIp(ServletUtil.getRequest()));
    userModel.setLocation(IpRegionUtil.getIpRegion((userModel.getIp())).getCity());
    userModel.setMobile(userAgent.isMobile());
    userModel.setBrowser(userAgent.getBrowser().getName());
    userModel.setVersion(userAgent.getVersion());
    userModel.setPlatform(userAgent.getPlatform().getName());
    userModel.setOs(userAgent.getOs().getName());
    userModel.setOsVersion(userAgent.getOsVersion());
    userModel.setEngine(userAgent.getEngine().getName());
    userModel.setEngineVersion(userAgent.getEngineVersion());
    userModel.setLoginTime(LocalDateTime.now());
    return userModel;
  }

  /**
   * 更新用户登陆信息
   *
   * @param userModel 登陆用户信息
   */
  private void updateUserLoginInfo(@NonNull UserModel userModel) {
    SysUser user = new SysUser();
    user.setId(userModel.getUser().getUserId());
    user.setLoginIp(userModel.getIp());
    user.setLoginDate(userModel.getLoginTime());
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
