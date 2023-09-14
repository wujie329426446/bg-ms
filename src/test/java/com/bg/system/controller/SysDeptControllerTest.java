package com.bg.system.controller;

import com.alibaba.fastjson2.JSONObject;
import com.bg.system.entity.SysDept;
import com.bg.system.param.DeptPageParam;
import com.bg.system.service.ISysDeptService;
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
 * @Author: jiewus
 * @Description:
 * @Date: 2023/9/13 15:44
 */
@SpringBootTest
@Slf4j
public class SysDeptControllerTest extends Assertions {

  private MockMvc mvc;
  @InjectMocks
  private SysDeptController baseController;
  @Autowired
  private ISysDeptService baseService;

  @BeforeEach
  public void setup() {
    //Í¨¹ýReflectionTestUtils×¢Èëservice
    ReflectionTestUtils.setField(baseController, "baseService", baseService);

    mvc = MockMvcBuilders.standaloneSetup(baseController).build();
//    mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  @Test()
  public void save() throws Exception {
    SysDept sysDept = new SysDept();
    sysDept.setDeptName("testName041").setDeptCode("testCode041");
    sysDept.setRemark("test041");
    sysDept.setParentId("1f5d3d9bdfa4f3cf0aeac05f24417149");

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .post("/dept/add")
        .contentType(MediaType.APPLICATION_JSON)
        .content(JSONObject.toJSONString(sysDept))
        .characterEncoding("UTF-8");

    MvcResult mvcResult = mvc.perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print()).andReturn();
    log.info("Save test result: {}", mvcResult.getResponse().getContentAsString());
  }

  @Test
  public void updateById() throws Exception {
    SysDept sysDept = new SysDept();
    sysDept.setDeptName("testName02").setDeptCode("testCode002").setParentId("5b823d385b491b33c38c36b05c5abb08");
    sysDept.setId("f5763e4b26bce83ac15a330fa1d83eac");
    sysDept.setRemark("remark002");

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .post("/dept/update")
        .contentType(MediaType.APPLICATION_JSON)
        .content(JSONObject.toJSONString(sysDept))
        .characterEncoding("UTF-8");

    MvcResult mvcResult = mvc.perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print()).andReturn();
    log.info("updateById test result: {}", mvcResult.getResponse().getContentAsString());
  }

  @Test
  public void getDeptById() throws Exception {
    String id = "62e3e55fe284b4c40b88568a0d026036";
    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .get("/dept/detail")
        .contentType(MediaType.APPLICATION_JSON)
        .queryParam("id", id)
        .characterEncoding("UTF-8");

    MvcResult mvcResult = mvc.perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print()).andReturn();
    log.info("getDeptById test result: {}", mvcResult.getResponse().getContentAsString());
  }

  @Test
  public void deleteDept() throws Exception {
    String id = "1f5d3d9bdfa4f3cf0aeac05f24417149";
    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .delete(String.format("/dept/delete/%s", id))
        .contentType(MediaType.APPLICATION_JSON)
        .characterEncoding("UTF-8");

    MvcResult mvcResult = mvc.perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print()).andReturn();
    log.info("getDeptById test result: {}", mvcResult.getResponse().getContentAsString());
  }

  @Test
  public void getDeptPageList() throws Exception {
    DeptPageParam pageParam = new DeptPageParam();
    pageParam.setDeptName("01");
    pageParam.setKeyword("02");

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .post("/dept/getPageList")
        .contentType(MediaType.APPLICATION_JSON)
        .content(JSONObject.toJSONString(pageParam))
        .characterEncoding("UTF-8");

    MvcResult mvcResult = mvc.perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print()).andReturn();
    log.info("getDeptPageList test result: {}", mvcResult.getResponse().getContentAsString());
  }

  @Test
  public void getDeptList() throws Exception {
    DeptPageParam pageParam = new DeptPageParam();
//    pageParam.setDeptName("01");

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .post("/dept/getList")
        .contentType(MediaType.APPLICATION_JSON)
        .content(JSONObject.toJSONString(pageParam))
        .characterEncoding("UTF-8");

    MvcResult mvcResult = mvc.perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print()).andReturn();
    log.info("getDeptList test result: {}", mvcResult.getResponse().getContentAsString());

  }

  @Test
  public void getDeptTreeList() throws Exception {
    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .get("/dept/getTreeList")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE);

    String result = mvc.perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print()).andReturn().getResponse().getContentAsString();
    log.info("getDeptTreeList test result: {}", result);
  }
}