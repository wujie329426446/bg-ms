package com.bg.service;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bg.system.param.UserPageParam;
import com.bg.system.service.ISysUserService;
import com.bg.system.vo.SysUserVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class ISysUserServiceTests {

  @Autowired
  private ISysUserService ISysUserService;

  @Test
  public void selectUserByUsername() {
    String bg = ISysUserService.selectUserByUsername("bg").toString();
    log.info("bg:{}", bg);
  }

  @Test
  public void getUserPageList() {
    UserPageParam userPageParam = new UserPageParam();
    Page<SysUserVo> userPageList = ISysUserService.getUserPageList(userPageParam);
    log.info("userPageList: {}", JSON.toJSONString(userPageList));
  }

}
