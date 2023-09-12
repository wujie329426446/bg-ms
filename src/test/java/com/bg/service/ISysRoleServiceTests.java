package com.bg.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bg.system.entity.SysRole;
import com.bg.system.service.ISysRoleService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ISysRoleServiceTests {

  //注入service
  @Autowired
  private ISysRoleService ISysRoleService;

  //查询所有
  @Test
  public void findAll() {
    //service方法实现
    List<SysRole> list = ISysRoleService.list();
    System.out.println(list);
  }

  //添加
  @Test
  public void add() {
    SysRole sysRole = new SysRole();
    sysRole.setRoleName("角色管理员");
    sysRole.setRoleCode("role");
    sysRole.setRemark("角色管理员");
    ISysRoleService.save(sysRole);
  }

  //修改
  @Test
  public void update() {
    SysRole sysRole = ISysRoleService.getById(1);
    sysRole.setRemark("test");
    ISysRoleService.updateById(sysRole);
  }

  //删除
  @Test
  public void remove() {
    ISysRoleService.removeById(8);
  }

  //条件查询
  @Test
  public void select() {
    QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
    wrapper.eq("role_code", "SYSTEM");
    List<SysRole> list = ISysRoleService.list(wrapper);
    System.out.println(list);
  }
}