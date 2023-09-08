package com.bg.auth.security.filter;

import com.bg.auth.security.authentication.username.UsernameAuthenticationToken;
import com.bg.auth.service.TokenService;
import com.bg.commons.model.UserModel;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

/**
 * token 过滤器
 *
 * @author jiewus
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

  private final TokenService tokenService;

  public JwtAuthenticationTokenFilter(TokenService tokenService) {
    this.tokenService = tokenService;
  }

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
    UserModel userModel = tokenService.get(request);

    if (userModel != null) {
      tokenService.verifyToken(userModel);

      AbstractAuthenticationToken authenticationToken;
      authenticationToken = new UsernameAuthenticationToken(userModel, Collections.emptyList());

      authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    filterChain.doFilter(request, response);
  }

}
