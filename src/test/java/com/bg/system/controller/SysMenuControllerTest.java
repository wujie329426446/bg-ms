package com.bg.system.controller;

import com.alibaba.fastjson2.JSONObject;
import com.bg.system.entity.SysMenu;
import com.bg.system.enums.MenuLevelEnum;
import com.bg.system.enums.MenuTypeEnum;
import com.bg.system.param.MenuPageParam;
import com.bg.system.service.ISysMenuService;
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
 * @Date: 2023/9/15 11:06
 */
@SpringBootTest
@Slf4j
public class SysMenuControllerTest {

  private MockMvc mvc;
  @InjectMocks
  private SysMenuController baseController;
  @Autowired
  private ISysMenuService baseService;

  @BeforeEach
  void setUp() {
    //Í¨¹ýReflectionTestUtils×¢Èëservice
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
    SysMenu sysMenu = new SysMenu();
    sysMenu.setMenuName("testName112").setCode("testCode112").setLevel(MenuLevelEnum.THREE).setType(MenuTypeEnum.THREE);
    sysMenu.setRemark("test112");
    sysMenu.setParentId("11814120ca56ccb80f637d2d410386de");

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .post("/menu/add")
        .contentType(MediaType.APPLICATION_JSON)
        .content(JSONObject.toJSONString(sysMenu));

    MvcResult mvcResult = mvc.perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print()).andReturn();
    log.info("Save test result: {}", mvcResult.getResponse().getContentAsString());
  }

  @Test
  public void updateById() throws Exception {
    SysMenu sysMenu = new SysMenu();
    sysMenu.setMenuName("testName11222").setCode("testCode112").setLevel(MenuLevelEnum.THREE).setType(MenuTypeEnum.THREE);
    sysMenu.setRemark("test11222");
    sysMenu.setParentId("11814120ca56ccb80f637d2d410386de");
    sysMenu.setId("491545f117b9fdf8078acfe72ef5aecd");

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .post("/menu/update")
        .contentType(MediaType.APPLICATION_JSON)
        .content(JSONObject.toJSONString(sysMenu));

    MvcResult mvcResult = mvc.perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print()).andReturn();
    log.info("updateById test result: {}", mvcResult.getResponse().getContentAsString());
  }

  @Test
  public void getMenuById() throws Exception {
    String id = "11814120ca56ccb80f637d2d410386de";
    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .get("/menu/detail")
        .contentType(MediaType.APPLICATION_JSON)
        .queryParam("id", id);

    MvcResult mvcResult = mvc.perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print()).andReturn();
    log.info("getDeptById test result: {}", mvcResult.getResponse().getContentAsString());
  }

  @Test
  public void deleteSysMenu() throws Exception {
    String id = "80a091a0b9fe529cc1e8c41afb6f04f6";
    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .delete(String.format("/menu/delete/%s", id))
        .contentType(MediaType.APPLICATION_JSON);

    MvcResult mvcResult = mvc.perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print()).andReturn();
    log.info("deleteSysMenu test result: {}", mvcResult.getResponse().getContentAsString());
  }

  @Test
  public void getMenuPageList() throws Exception {
    MenuPageParam pageParam = new MenuPageParam();
//    pageParam.setMenuName("01");
    pageParam.setKeyword("1");

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .post("/menu/getPageList")
        .contentType(MediaType.APPLICATION_JSON)
        .content(JSONObject.toJSONString(pageParam));

    MvcResult mvcResult = mvc.perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print()).andReturn();
    log.info("getMenuPageList test result: {}", mvcResult.getResponse().getContentAsString());
  }

  @Test
  public void getMenuList() throws Exception {
    String userId = "a0d35e0bf436fd37d2796caf333ac374";

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .post("/menu/getList")
        .contentType(MediaType.APPLICATION_JSON)
        .queryParam("userId", userId);

    MvcResult mvcResult = mvc.perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print()).andReturn();
    log.info("getMenuList test result: {}", mvcResult.getResponse().getContentAsString());
  }

  @Test
  public void getMenuTree() throws Exception {
    String userId = "a0d35e0bf436fd37d2796caf333ac374";
    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .get("/menu/getTree")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE);
//        .queryParam("userId", userId);

    String result = mvc.perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print()).andReturn().getResponse().getContentAsString();
    log.info("getMenuTree test result: {}", result);
  }


  @Test
  public void getCodesByUser() throws Exception {
    String userId = "a0d35e0bf436fd37d2796caf333ac374";
    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .get("/menu/getCodesByUserId")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
        .queryParam("userId", userId);

    String result = mvc.perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print()).andReturn().getResponse().getContentAsString();
    log.info("getCodesByUser test result: {}", result);
  }

  @Test
  public void getListByRole() throws Exception {
    String roleId = "f0980292de98d8c5a6d40f26581018b6";
    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .get("/menu/listRoleMenus")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
        .queryParam("roleId", roleId);

    String result = mvc.perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print()).andReturn().getResponse().getContentAsString();
    log.info("listRoleMenus test result: {}", result);
  }
}