package com.bg.system.service.impl;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bg.commons.api.ApiCode;
import com.bg.commons.enums.StateEnum;
import com.bg.commons.exception.BusinessException;
import com.bg.system.convert.SysRoleConvertMapper;
import com.bg.system.entity.SysRole;
import com.bg.system.entity.SysRoleMenu;
import com.bg.system.entity.SysUserRole;
import com.bg.system.mapper.SysRoleMapper;
import com.bg.system.param.RolePageParam;
import com.bg.system.service.ISysRoleMenuService;
import com.bg.system.service.ISysRoleService;
import com.bg.system.service.ISysUserRoleService;
import com.bg.system.vo.SysRoleVo;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
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
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

  private final ISysRoleMenuService ISysRoleMenuService;
  private final ISysUserRoleService ISysUserRoleService;
  private final SysRoleConvertMapper sysRoleConvertMapper;


  @Transactional(rollbackFor = Exception.class)
  @Override
  public boolean save(SysRole sysRole) {
    // 角色新增校验
    this.saveOrUpdateCheck(sysRole, FieldFill.INSERT);
    // 保存角色
    boolean save = super.save(sysRole);
    if (save) {
      // 新增角色与菜单权限关联关系
      List<String> permissions = sysRole.getPermissions();
      if (CollectionUtils.isNotEmpty(permissions)) {
        permissions.stream().forEach(itme -> new SysRoleMenu().setRoleId(sysRole.getId()).setPermissionId(itme).insert());
      }
    }

    return save;
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public boolean updateById(SysRole sysRole) {
    String roleId = sysRole.getId();
    // 角色修改校验
    this.saveOrUpdateCheck(sysRole, FieldFill.UPDATE);

    // 删除角色与权限关联关系
    ISysRoleMenuService.remove(Wrappers.lambdaQuery(SysRoleMenu.class).eq(SysRoleMenu::getRoleId, roleId));

    // 新增角色与权限关联关系
    List<String> permissions = sysRole.getPermissions();
    if (CollectionUtils.isNotEmpty(permissions)) {
      permissions.stream().forEach(itme -> new SysRoleMenu().setRoleId(roleId).setPermissionId(itme).insert());
    }

    return super.updateById(sysRole);
  }

  private void saveOrUpdateCheck(SysRole sysRole, FieldFill fill) {
    if (Objects.isNull(sysRole)) {
      throw BusinessException.build(ApiCode.BUSINESS_EXCEPTION.getCode(), "校验角色对象为空");
    }
    // 校验角色name唯一性
    Boolean isRoleNameExists = sysRole.selectCount(
        new LambdaQueryWrapper<SysRole>().eq(SysRole::getRoleName, sysRole.getRoleName()).ne(fill.equals(FieldFill.UPDATE), SysRole::getId, sysRole.getId())
    ) > 0;
    if (isRoleNameExists) {
      throw BusinessException.build(ApiCode.BUSINESS_EXCEPTION.getCode(), "角色名称已存在");
    }

    // 校验角色code唯一性
    Boolean isRoleCodeExists = sysRole.selectCount(
        new LambdaQueryWrapper<SysRole>().eq(SysRole::getRoleCode, sysRole.getRoleCode()).ne(fill.equals(FieldFill.UPDATE), SysRole::getId, sysRole.getId())
    ) > 0;
    if (isRoleCodeExists) {
      throw BusinessException.build(ApiCode.BUSINESS_EXCEPTION.getCode(), "角色编码已存在");
    }
  }

  @Override
  public SysRoleVo getRoleById(String id) {
    SysRole sysRole = super.getById(id);
    SysRoleVo sysRoleVo = sysRoleConvertMapper.toDto(sysRole);
    // 获取关联菜单集合
    List<String> permissionIds = ISysRoleMenuService.listObjs(Wrappers.lambdaQuery(SysRoleMenu.class)
        .select(SysRoleMenu::getPermissionId)
        .eq(SysRoleMenu::getRoleId, id)
        .eq(SysRoleMenu::getStatus, StateEnum.ENABLE.getCode()), Object::toString);
    sysRoleVo.setPermissions(permissionIds);
    return sysRoleVo;
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public boolean removeById(Serializable id) {
    //判断该角色下是否有关联用户，如果有，则不能删除
    boolean isExistsUser = ISysUserRoleService.count(Wrappers.lambdaQuery(SysUserRole.class).eq(SysUserRole::getRoleId, id)) > 0;
    if (isExistsUser) {
      throw BusinessException.build(ApiCode.BUSINESS_EXCEPTION.getCode(), "该角色下还存在可用用户，不能删除");
    }

    // 删除角色权限关联关系
    ISysRoleMenuService.remove(Wrappers.lambdaQuery(SysRoleMenu.class).eq(SysRoleMenu::getRoleId, id));

    return super.removeById(id);
  }

  @Override
  public Page<SysRole> getRolePageList(RolePageParam pageParam) {
    LambdaQueryWrapper<SysRole> queryWrapper = Wrappers.lambdaQuery(SysRole.class);
    String keyword = pageParam.getKeyword();
    String name = pageParam.getRoleName();
    String code = pageParam.getRoleCode();
    Integer state = pageParam.getState();

    // 条件查询
    queryWrapper
        .and(StringUtils.isNotBlank(keyword),
            i -> i.like(SysRole::getRoleName, keyword)
                .or().like(SysRole::getRoleCode, keyword)
        )
        .like(StringUtils.isNotBlank(name), SysRole::getRoleName, name)
        .like(StringUtils.isNotBlank(code), SysRole::getRoleCode, code)
        .like(Objects.nonNull(state), SysRole::getStatus, state)
        .orderByDesc(SysRole::getCreateTime);

    Page<SysRole> page = super.page(pageParam.getPage(), queryWrapper);
    return page;
  }

  @Override
  public List<SysRoleVo> getRoleList(RolePageParam pageParam) {
    LambdaQueryWrapper<SysRole> queryWrapper = Wrappers.lambdaQuery(SysRole.class);
    String keyword = pageParam.getKeyword();
    String name = pageParam.getRoleName();
    String code = pageParam.getRoleCode();
    Integer state = pageParam.getState();

    // 条件查询
    queryWrapper
        .and(StringUtils.isNotBlank(keyword),
            i -> i.like(SysRole::getRoleName, keyword)
                .or().like(SysRole::getRoleCode, keyword)
        )
        .like(StringUtils.isNotBlank(name), SysRole::getRoleName, name)
        .like(StringUtils.isNotBlank(code), SysRole::getRoleCode, code)
        .like(Objects.nonNull(state), SysRole::getStatus, state)
        .orderByDesc(SysRole::getCreateTime);

    List<SysRole> list = super.list(queryWrapper);
    return sysRoleConvertMapper.toDto(list);
  }

  @Override
  public List<String> getRoleIdsByUserId(String userId) {
    List<SysRole> roleList = baseMapper.selectRoleListByUserId(userId);
    return roleList.stream().map(SysRole::getId).collect(Collectors.toList());
  }

  @Override
  public List<SysRoleVo> getRolesByUserId(String userId) {
    List<SysRole> roleList = baseMapper.selectRoleListByUserId(userId);
    List<SysRoleVo> roleVoList = sysRoleConvertMapper.toDto(roleList);
    return roleVoList;
  }


}