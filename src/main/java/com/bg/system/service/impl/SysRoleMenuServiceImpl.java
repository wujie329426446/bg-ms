package com.bg.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bg.commons.service.impl.BaseServiceImpl;
import com.bg.system.entity.SysRoleMenu;
import com.bg.system.entity.SysUserRole;
import com.bg.commons.enums.StateEnum;
import com.bg.system.mapper.SysRoleMapper;
import com.bg.system.mapper.SysRoleMenuMapper;
import com.bg.system.service.SysRoleMenuService;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.SetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * <pre>
 * 角色权限关系 服务实现类
 * </pre>
 *
 * @author jiewus
 * @since 2019-10-25
 */
@Slf4j
@Service
public class SysRoleMenuServiceImpl extends BaseServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {

  @Autowired
  private SysRoleMenuMapper sysRoleMenuMapper;

  @Autowired
  private SysRoleMapper sysRoleMapper;

  @Transactional(rollbackFor = Exception.class)
  @Override
  public boolean saveSysRolePermission(String roleId, List<String> permissionIds) throws Exception {
    List<SysRoleMenu> list = new ArrayList<>();
    permissionIds.forEach(permissionId -> {
      SysRoleMenu sysRoleMenu = new SysRoleMenu();
      sysRoleMenu.setRoleId(roleId);
      sysRoleMenu.setPermissionId(permissionId);
      sysRoleMenu.setStatus(StateEnum.ENABLE.getCode());
      list.add(sysRoleMenu);
    });
    // 批量保存角色权限中间表
    return saveBatch(list, 20);
  }

  @Override
  public List<String> getPermissionIdsByRoleId(String roleId) throws Exception {
    Wrapper wrapper = lambdaQuery()
        .select(SysRoleMenu::getPermissionId)
        .eq(SysRoleMenu::getRoleId, roleId)
        .eq(SysRoleMenu::getStatus, StateEnum.ENABLE.getCode())
        .getWrapper();
    return sysRoleMenuMapper.selectObjs(wrapper);
  }

  @Override
  public List<String> getThreeLevelPermissionIdsByRoleId(String roleId) throws Exception {
    return sysRoleMenuMapper.getThreeLevelPermissionIdsByRoleId(roleId);
  }

  @Override
  public boolean saveSysRolePermissionBatch(String roleId, SetUtils.SetView addSet) {
    List<SysRoleMenu> list = new ArrayList<>();
    addSet.forEach(id -> {
      SysRoleMenu sysRoleMenu = new SysRoleMenu();
      String permissionId = (String) id;
      sysRoleMenu
          .setRoleId(roleId)
          .setPermissionId(permissionId)
          .setStatus(StateEnum.ENABLE.getCode());
      list.add(sysRoleMenu);
    });
    return saveBatch(list, 20);
  }

  @Override
  public boolean deleteSysRolePermissionByRoleId(String roleId) throws Exception {
    SysRoleMenu sysRoleMenu = new SysRoleMenu()
        .setRoleId(roleId);
    return remove(new QueryWrapper<>(sysRoleMenu));
  }

  @Override
  public Set<String> getPermissionCodesByRoleId(List<SysUserRole> sysUserRoleList) throws Exception {
    List<String> roleIds = sysUserRoleList.stream().map(SysUserRole::getRoleId).collect(Collectors.toList());
    return sysRoleMenuMapper.getPermissionCodesByRoleId(roleIds);
  }

  @Override
  public boolean isExistsByPermissionId(String permissionId) throws Exception {
    // 判断角色权限表是否有关联存在，如果存在，则不能删除
    SysRoleMenu sysRoleMenu = new SysRoleMenu()
        .setPermissionId(permissionId);
    return count(new QueryWrapper<SysRoleMenu>(sysRoleMenu)) > 0;
  }

  @Override
  public boolean hasPermission(String roleId) throws Exception {
    SysRoleMenu sysRoleMenu = new SysRoleMenu()
        .setRoleId(roleId);
    return count(new QueryWrapper<SysRoleMenu>(sysRoleMenu)) > 0;
  }

}
