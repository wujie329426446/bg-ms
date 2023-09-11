package com.bg.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bg.commons.api.ApiCode;
import com.bg.commons.enums.StateEnum;
import com.bg.commons.exception.BusinessException;
import com.bg.commons.service.impl.BaseServiceImpl;
import com.bg.system.convert.SysRoleConvertMapper;
import com.bg.system.entity.SysMenu;
import com.bg.system.entity.SysRole;
import com.bg.system.entity.SysRoleMenu;
import com.bg.system.mapper.SysRoleMapper;
import com.bg.system.param.sysrole.SysRolePageParam;
import com.bg.system.param.sysrole.UpdateSysRolePermissionParam;
import com.bg.system.service.SysMenuService;
import com.bg.system.service.SysRoleMenuService;
import com.bg.system.service.SysRoleService;
import com.bg.system.vo.SysRoleVo;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.SetUtils;
import org.apache.commons.collections4.SetUtils.SetView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * <pre>
 * 系统角色 服务实现类
 * </pre>
 *
 * @author jiewus
 */
@Slf4j
@Service
@Transactional(rollbackFor = Throwable.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SysRoleServiceImpl extends BaseServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

  private final SysRoleMapper sysRoleMapper;

  private final SysMenuService sysMenuService;

  private final SysRoleMenuService sysRoleMenuService;

  private final SysRoleConvertMapper sysRoleConvertMapper;


  @Transactional(rollbackFor = Exception.class)
  @Override
  public boolean save(SysRole sysRole) {
    String code = sysRole.getRoleCode();
    // 校验角色标识code唯一性
    if (this.isExistsByCode(code)) {
      throw BusinessException.build(ApiCode.BUSINESS_EXCEPTION.getCode(), "角色编码已存在");
    }
    // 保存角色
    boolean saveRoleResult = super.save(sysRole);
    if (!saveRoleResult) {
      throw BusinessException.build("保存角色失败");
    }
    return true;
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public boolean updateSysRole(SysRole sysRole) throws Exception {
    String roleId = sysRole.getId();
    // 校验角色是否存在
    if (getById(roleId) == null) {
      throw BusinessException.build("该角色不存在");
    }
    // 修改角色
//        sysRole.setUpdateTime(new Date());
    boolean updateResult = updateById(sysRole);
    if (!updateResult) {
      throw BusinessException.build("修改系统角色失败");
    }
    return true;
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public boolean deleteSysRole(String id) throws Exception {

    // TODO: 2023/6/26 判断该角色下是否有可用用户，如果有，则不能删除
    //        boolean isExistsUser = sysUserService.isExistsSysUserByRoleId(id);
    //        if (isExistsUser) {
    //            throw new BusinessException.build("该角色下还存在可用用户，不能删除");
    //        }
    // 角色真实删除
    boolean deleteRoleResult = removeById(id);
    if (!deleteRoleResult) {
      throw BusinessException.build("删除角色失败");
    }

    // 判断角色是否有权限，如果有，则删除
    boolean hasPermission = sysRoleMenuService.hasPermission(id);
    if (hasPermission) {
      // 角色权限关系真实删除
      boolean deletePermissionResult = sysRoleMenuService.deleteSysRolePermissionByRoleId(id);
      if (!deletePermissionResult) {
        throw BusinessException.build("删除角色权限关系失败");
      }
    }
    return true;
  }

  @Override
  public SysRoleVo getSysRoleById(Serializable id) throws Exception {
    SysRoleVo sysRoleVo = sysRoleMapper.getSysRoleById(id);
    if (sysRoleVo == null) {
      throw BusinessException.build("角色不存在");
    }
    List<String> permissionIds = sysRoleMenuService.getPermissionIdsByRoleId((String) id);
    sysRoleVo.setPermissions(new HashSet<>(permissionIds));
    return sysRoleVo;
  }

  @Override
  public Page<SysRole> getSysRolePageList(SysRolePageParam pageParam) throws Exception {
    pageParam.pageSortsHandle(OrderItem.desc("create_time"));
    // 此处演示单表，使用mybatisplus自带方法进行分页
    LambdaQueryWrapper<SysRole> lambdaQueryWrapper = new LambdaQueryWrapper<>();
    String keyword = pageParam.getKeyword();
    String name = pageParam.getName();
    String code = pageParam.getCode();
    Integer state = pageParam.getState();
    if (StringUtils.isNotBlank(keyword)) {
      lambdaQueryWrapper
          .like(SysRole::getRoleName, keyword)
          .or()
          .like(SysRole::getRoleCode, keyword);
    }
    if (StringUtils.isNotBlank(name)) {
      lambdaQueryWrapper.like(SysRole::getRoleName, name);
    }
    if (StringUtils.isNotBlank(code)) {
      lambdaQueryWrapper.like(SysRole::getRoleCode, code);
    }
    if (state != null) {
      lambdaQueryWrapper.eq(SysRole::getStatus, state);
    }
    Page<SysRole> page = sysRoleMapper.selectPage(pageParam.getPage(), lambdaQueryWrapper);
    return page;
  }

  @Override
  public boolean isEnableSysRole(String id) throws Exception {
    SysRole sysRole = new SysRole();
    sysRole.setId(id);
    sysRole.setStatus(StateEnum.ENABLE.getCode());

    Long count = sysRoleMapper.selectCount(new QueryWrapper<>(sysRole));
    return count > 0;
  }

  @Override
  public boolean isExistsByCode(String code) {
    SysRole sysRole = new SysRole().setRoleCode(code);
    return sysRoleMapper.selectCount(new QueryWrapper<>(sysRole)) > 0;
  }

  @Override
  public boolean updateSysRolePermission(UpdateSysRolePermissionParam param) throws Exception {
    String roleId = param.getRoleId();
    List<String> permissionIds = param.getPermissionIds();
    // 校验角色是否存在
    SysRole sysRole = getById(roleId);
    if (sysRole == null) {
      throw BusinessException.build("该角色不存在");
    }
    if (CollectionUtils.isNotEmpty(permissionIds)) {
      // 校验权限列表是否存在
      if (!sysMenuService.isExistsByPermissionIds(permissionIds)) {
        throw BusinessException.build("权限列表id匹配失败");
      }
    }
    // 获取之前的权限id集合
    List<String> beforeList = sysRoleMenuService.getPermissionIdsByRoleId(roleId);
    // 差集计算
    // before：1,2,3,4,5,6
    // after： 1,2,3,4,7,8
    // 删除5,6 新增7,8
    // 此处真实删除，去掉deleted字段的@TableLogic注解
    Set<String> beforeSet = new HashSet<>(beforeList);
    Set<String> afterSet = new HashSet<>(permissionIds);
    SetView<String> deleteSet = SetUtils.difference(beforeSet, afterSet);
    SetView<String> addSet = SetUtils.difference(afterSet, beforeSet);
    log.debug("deleteSet = " + deleteSet);
    log.debug("addSet = " + addSet);

    if (CollectionUtils.isNotEmpty(deleteSet)) {
      // 删除权限关联
      LambdaUpdateWrapper<SysRoleMenu> updateWrapper = new LambdaUpdateWrapper<>();
      updateWrapper.eq(SysRoleMenu::getRoleId, roleId);
      updateWrapper.in(SysRoleMenu::getPermissionId, deleteSet);
      boolean deleteResult = sysRoleMenuService.remove(updateWrapper);
      if (!deleteResult) {
        throw BusinessException.build("删除角色权限关系失败");
      }
    }

    if (CollectionUtils.isNotEmpty(addSet)) {
      // 新增权限关联
      boolean addResult = sysRoleMenuService.saveSysRolePermissionBatch(roleId, addSet);
      if (!addResult) {
        throw BusinessException.build("新增角色权限关系失败");
      }
    }

    return true;
  }

  @Override
  public List<SysMenu> listRoleMenus(String roleId) {
    // TODO: 2023/9/1 优化查询，不要遍历查子集
    List<SysRoleMenu> sysRoleMenuList = sysRoleMenuService.list(Wrappers.lambdaQuery(SysRoleMenu.class).eq(SysRoleMenu::getRoleId, roleId));
    return sysRoleMenuList.stream().map(item ->
        sysMenuService.getById(item.getPermissionId())
    ).collect(Collectors.toList());
  }

  @Override
  public List<String> getRoleIdsByUserId(String userId) {
    List<SysRole> roleList = sysRoleMapper.selectRoleListByUserId(userId);
    return roleList.stream().map(SysRole::getId).collect(Collectors.toList());
  }

  @Override
  public List<SysRoleVo> getRolesByUserId(String userId) {
    List<SysRole> roleList = sysRoleMapper.selectRoleListByUserId(userId);
    List<SysRoleVo> roleVoList = sysRoleConvertMapper.toDto(roleList);
    return roleVoList;
  }


}