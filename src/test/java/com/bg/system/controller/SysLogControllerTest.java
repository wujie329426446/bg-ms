package com.bg.system.controller;

import com.alibaba.fastjson2.JSONObject;
import com.bg.commons.enums.LogTypeEnum;
import com.bg.system.param.LogPageParam;
import com.bg.system.service.ISysLogService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
 * @Author: issuser
 * @Description:
 * @Date: 2023/9/15 16:33
 */
@SpringBootTest
@Slf4j
class SysLogControllerTest {

  private MockMvc mvc;
  @InjectMocks
  private SysLogController baseController;
  @Autowired
  private ISysLogService baseService;

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
  public void getLogById() throws Exception {
    String id = "1fcc5e96d6597b26f2e833f581a54d28";
    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .get("/log/detail")
        .contentType(MediaType.APPLICATION_JSON)
        .queryParam("id", id);

    MvcResult mvcResult = mvc.perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print()).andReturn();
    log.info("getLogById test result: {}", mvcResult.getResponse().getContentAsString());
  }

  @Test
  public void getLogPageList() throws Exception {
    LogPageParam pageParam = new LogPageParam();
//    pageParam.setKeyword("1");
    pageParam.setCreateStartTime(LocalDateTime.parse("2023-09-11 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    pageParam.setCreateEndTime(LocalDateTime.parse("2023-09-15 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
//    pageParam.setLogType(LogTypeEnum.OTHER);

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .post("/log/getPageList")
        .contentType(MediaType.APPLICATION_JSON)
        .content(JSONObject.toJSONString(pageParam));

    MvcResult mvcResult = mvc.perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print()).andReturn();
    log.info("getLogPageList test result: {}", mvcResult.getResponse().getContentAsString());
  }

}