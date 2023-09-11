package com.bg.service;

import com.bg.system.mapper.SysUserMapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class SysUserMapperTests {

  @Autowired
  private SysUserMapper sysUserMapper;

  @Transactional
  @Test
  @Rollback
  public void selectUserByUsername() throws Exception {
    sysUserMapper.selectUserByUsername("map");
  }

}
