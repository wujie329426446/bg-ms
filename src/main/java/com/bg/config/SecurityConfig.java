package com.bg.config;

import com.bg.auth.security.authentication.email.EmailVerificationCodeAuthenticationProvider;
import com.bg.auth.security.authentication.username.UsernameAuthenticationProvider;
import com.bg.auth.security.filter.JwtAuthenticationTokenFilter;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * Spring Security 配置
 *
 * @author jiewus
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(
      HttpSecurity httpSecurity, AuthenticationEntryPoint authenticationEntryPoint,
      JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter,
      LogoutSuccessHandler logoutSuccessHandler) throws Exception {
    return httpSecurity
        // 禁用 CSRF
        .csrf(AbstractHttpConfigurer::disable)
        // 启用 CORS
        .cors(Customizer.withDefaults())
        // 设置 session 管理器为无状态
        .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        // 设置未登陆过滤器
        .exceptionHandling(handling -> handling.authenticationEntryPoint(authenticationEntryPoint))
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers("/v1/api/admin/login").permitAll()
            .requestMatchers("/v1/api/admin/emailLogin").permitAll()
            .requestMatchers("/v1/api/admin/phoneLogin").permitAll()
            .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/doc.html", "/webjars/**").permitAll()
            .anyRequest().authenticated()
        )
        .logout(logout -> logout.logoutUrl("/v1/api/admin/logout").logoutSuccessHandler(logoutSuccessHandler))
        .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
  }

  @Bean
  public AuthenticationManager authenticationManager(UsernameAuthenticationProvider usernameAuthenticationProvider, EmailVerificationCodeAuthenticationProvider emailAuthenticationProvider) {
    List<AuthenticationProvider> providers = List.of(usernameAuthenticationProvider, emailAuthenticationProvider);
    return new ProviderManager(providers);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
