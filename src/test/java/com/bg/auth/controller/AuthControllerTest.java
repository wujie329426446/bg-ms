package com.bg.auth.controller;

import com.alibaba.fastjson2.JSONObject;
import com.bg.auth.service.IAuthService;
import com.bg.commons.model.LoginParam;
import com.bg.system.service.ISysMenuService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * @ProjectName: bg-ms
 * @Author: issuser
 * @Description:
 * @Date: 2023/9/26 14:11
 */
@SpringBootTest
@Slf4j
class AuthControllerTest extends Assertions {

  private MockMvc mvc;
  @InjectMocks
  private AuthController baseController;
  @Autowired
  private IAuthService authService;
  @Autowired
  private ISysMenuService sysMenuService;

  @BeforeEach
  public void setUp() {
    //Í¨¹ýReflectionTestUtils×¢Èëservice
    ReflectionTestUtils.setField(baseController, "authService", authService);
    ReflectionTestUtils.setField(baseController, "sysMenuService", sysMenuService);

    mvc = MockMvcBuilders.standaloneSetup(baseController)
        .addFilter((request, response, chain) -> {
          response.setCharacterEncoding("UTF-8");
          chain.doFilter(request, response);
        }, "/*")
        .build();
  }

  @Test
  public void verify() throws Exception {
    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .get("/verify")
        .contentType(MediaType.APPLICATION_JSON);

    MvcResult mvcResult = mvc.perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print()).andReturn();
    log.info("verify test result: {}", mvcResult.getResponse().getContentAsString());
  }

  @Test
  public void emailVerify() throws Exception {
  }

  @Test
  public void phoneVerify() throws Exception {
  }

  @Test
  public void accountLogin() throws Exception {
    LoginParam loginParam = new LoginParam();
    loginParam.setUsername("bg");
    loginParam.setPassword("123456");
    loginParam.setVerifyCode("6mdhw");
    loginParam.setVerifyUUID("6bacdef9-5577-4736-8e1f-ef4f080f02bd");

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .post("/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(JSONObject.toJSONString(loginParam));

    MvcResult mvcResult = mvc.perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print()).andReturn();
    log.info("accountLogin test result: {}", mvcResult.getResponse().getContentAsString());
  }

  @Test
  public void emailLogin() throws Exception {
  }

  @Test
  public void phoneLogin() throws Exception {
  }

  @Test
  public void getSysUser() throws Exception {
  }

  @Test
  public void getPermCode() throws Exception {
  }
}