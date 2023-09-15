package com.bg.system.controller;

import com.alibaba.fastjson2.JSONObject;
import com.bg.system.entity.SysRole;
import com.bg.system.param.RolePageParam;
import com.bg.system.service.ISysRoleService;
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
 * @Date: 2023/9/14 12:20
 */
@SpringBootTest
@Slf4j
public class SysRoleControllerTest {

  private MockMvc mvc;
  @InjectMocks
  private SysRoleController baseController;
  @Autowired
  private ISysRoleService baseService;

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
    SysRole sysRole = new SysRole();
    sysRole.setRoleName("testName001").setRoleCode("testCode001");
    List<String> permissions = new ArrayList<>();
    permissions.add("80a091a0b9fe529cc1e8c41afb6f04f6");
    permissions.add("11814120ca56ccb80f637d2d410386de");
    permissions.add("8f428b20eeef046f45d19b13fdc81111");
    permissions.add("491545f117b9fdf8078acfe72ef5aecd");
    sysRole.setPermissions(permissions);

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .post("/role/add")
        .contentType(MediaType.APPLICATION_JSON)
        .content(JSONObject.toJSONString(sysRole));

    MvcResult mvcResult = mvc.perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print()).andReturn();
    log.info("Save test result: {}", mvcResult.getResponse().getContentAsString());
  }

  @Test
  public void updateById() throws Exception {
    SysRole sysRole = new SysRole();
    sysRole.setId("66f50c69de7ed0755ee9830cc47556b6");
    sysRole.setRoleName("testName001").setRoleCode("testCode001update1");
    List<String> permissions = new ArrayList<>();
    permissions.add("d48019c64b9011eea1093a31d6d59109");
    sysRole.setPermissions(permissions);

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .post("/role/update")
        .contentType(MediaType.APPLICATION_JSON)
        .content(JSONObject.toJSONString(sysRole));

    MvcResult mvcResult = mvc.perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print()).andReturn();
    log.info("updateById test result: {}", mvcResult.getResponse().getContentAsString());
  }

  @Test
  public void getRoleById() throws Exception {
    String id = "66f50c69de7ed0755ee9830cc47556b6";
    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .get("/role/detail")
        .contentType(MediaType.APPLICATION_JSON)
        .queryParam("id", id);

    MvcResult mvcResult = mvc.perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print()).andReturn();
    log.info("getDeptById test result: {}", mvcResult.getResponse().getContentAsString());

  }

  @Test
  public void deleteSysRole() throws Exception {
    String id = "66f50c69de7ed0755ee9830cc47556b6";
    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .delete(String.format("/role/delete/%s", id))
        .contentType(MediaType.APPLICATION_JSON);

    MvcResult mvcResult = mvc.perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print()).andReturn();
    log.info("getDeptById test result: {}", mvcResult.getResponse().getContentAsString());

  }

  @Test
  public void getRolePageList() throws Exception {
    RolePageParam pageParam = new RolePageParam();
//    pageParam.setDeptName("01");
    pageParam.setKeyword("1");

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .post("/role/getPageList")
        .contentType(MediaType.APPLICATION_JSON)
        .content(JSONObject.toJSONString(pageParam));

    MvcResult mvcResult = mvc.perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print()).andReturn();
    log.info("getDeptPageList test result: {}", mvcResult.getResponse().getContentAsString());

  }

  @Test
  public void getRoleList() throws Exception {
    RolePageParam pageParam = new RolePageParam();
//    pageParam.setDeptName("01");

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .post("/role/getList")
        .contentType(MediaType.APPLICATION_JSON)
        .content(JSONObject.toJSONString(pageParam));

    MvcResult mvcResult = mvc.perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print()).andReturn();
    log.info("getDeptList test result: {}", mvcResult.getResponse().getContentAsString());

  }

}