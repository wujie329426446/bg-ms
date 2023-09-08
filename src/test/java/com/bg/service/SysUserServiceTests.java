package com.bg.service;

import com.bg.system.service.SysUserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SysUserServiceTests {

  @Autowired
  private SysUserService sysUserService;

  @Test
  public void resetUser() {
    sysUserService.resetUser("bg", "bg", "329426446@qq.com");
  }

  @Test
  public void selectUserByUsername() {
    System.out.println(sysUserService.selectUserByUsername("bg").toString());
  }
}
