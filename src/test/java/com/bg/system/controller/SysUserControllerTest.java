package com.bg.system.controller;

import com.alibaba.fastjson2.JSONObject;
import com.bg.commons.enums.GenderEnum;
import com.bg.system.entity.SysUser;
import com.bg.system.param.UserPageParam;
import com.bg.system.service.ISysUserService;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
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
 * @Author: jiewus
 * @Description:
 * @Date: 2023/9/14 14:58
 */
@SpringBootTest
@Slf4j
public class SysUserControllerTest {

  private MockMvc mvc;
  @InjectMocks
  private SysUserController baseController;
  @Autowired
  private ISysUserService baseService;

  @BeforeEach
  public void setUp() {
    //通过ReflectionTestUtils注入service
    ReflectionTestUtils.setField(baseController, "baseService", baseService);

    mvc = MockMvcBuilders.standaloneSetup(baseController)
        .addFilter((request, response, chain) -> {
          response.setCharacterEncoding("UTF-8");
          chain.doFilter(request, response);
        }, "/*")
        .build();
  }

  @Test
  public void save() throws Exception {
    SysUser sysUser = new SysUser();
    sysUser.setUsername("testName001")
        .setDeptId("80d53ab2edabe878062ee5025359371e")
        .setAvatar("www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png")
        .setEmail("testEmail002")
        .setPhone("1234567")
        .setGender(GenderEnum.MALE)
        .setNickname("北落师门02")
        .setRealname("憨憨02")
        .setRemark("测试账号02");
    List<String> roleList = new ArrayList<>();
    roleList.add("db3b2e464b8f11eea1093a31d6d59109");
    sysUser.setRoleIds(roleList);

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .post("/user/add")
        .contentType(MediaType.APPLICATION_JSON)
        .content(JSONObject.toJSONString(sysUser));

    MvcResult mvcResult = mvc.perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print()).andReturn();
    log.info("Save test result: {}", mvcResult.getResponse().getContentAsString());
  }

  @Test
  public void updateById() throws Exception {
    SysUser sysUser = new SysUser();
    sysUser.setId("a0d35e0bf436fd37d2796caf333ac374");
    sysUser.setUsername("testName001")
        .setDeptId("80d53ab2edabe878062ee5025359371e")
        .setAvatar("www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png")
        .setEmail("testEmail001")
        .setPhone("123456")
        .setGender(GenderEnum.MALE)
        .setNickname("北落师门01")
        .setRealname("憨憨01")
        .setRemark("测试账号01");
    List<String> roleList = new ArrayList<>();
    roleList.add("f0980292de98d8c5a6d40f26581018b6");
    sysUser.setRoleIds(roleList);

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .post("/user/update")
        .contentType(MediaType.APPLICATION_JSON)
        .content(JSONObject.toJSONString(sysUser));

    MvcResult mvcResult = mvc.perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print()).andReturn();
    log.info("updateById test result: {}", mvcResult.getResponse().getContentAsString());
  }

  @Test
  public void getUserById() throws Exception {
    String id = "a0d35e0bf436fd37d2796caf333ac374";
    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .get("/user/detail")
        .contentType(MediaType.APPLICATION_JSON)
        .queryParam("id", id);

    MvcResult mvcResult = mvc.perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print()).andReturn();
    log.info("getDeptById test result: {}", mvcResult.getResponse().getContentAsString());
  }

  @Test
  public void deleteSysUser() throws Exception {
    String id = "433388b14121e4502094d1f417b75137";
    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .delete(String.format("/user/delete/%s", id))
        .contentType(MediaType.APPLICATION_JSON);

    MvcResult mvcResult = mvc.perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print()).andReturn();
    log.info("getDeptById test result: {}", mvcResult.getResponse().getContentAsString());
  }

  @Test
  public void getUserPageList() throws Exception {
    UserPageParam pageParam = new UserPageParam();
//    pageParam.setDeptName("01");
    pageParam.setKeyword("test");

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .post("/user/getPageList")
        .contentType(MediaType.APPLICATION_JSON)
        .content(JSONObject.toJSONString(pageParam));

    MvcResult mvcResult = mvc.perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print()).andReturn();
    log.info("getDeptPageList test result: {}", mvcResult.getResponse().getContentAsString());
  }
}