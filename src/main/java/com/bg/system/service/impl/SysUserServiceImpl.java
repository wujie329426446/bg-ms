package com.bg.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bg.commons.api.ApiCode;
import com.bg.commons.exception.BusinessException;
import com.bg.commons.model.RoleModel;
import com.bg.commons.utils.PhoneUtil;
import com.bg.commons.utils.SecurityUtil;
import com.bg.config.properties.BgProperties;
import com.bg.system.convert.SysUserConvertMapper;
import com.bg.system.entity.SysMenu;
import com.bg.system.entity.SysRoleMenu;
import com.bg.system.entity.SysUser;
import com.bg.system.entity.SysUserRole;
import com.bg.system.enums.FrameEnum;
import com.bg.system.enums.KeepaliveEnum;
import com.bg.system.enums.LinkExternalEnum;
import com.bg.system.enums.MenuLevelEnum;
import com.bg.system.mapper.SysUserMapper;
import com.bg.system.param.UserPageParam;
import com.bg.system.service.ISysMenuService;
import com.bg.system.service.ISysRoleService;
import com.bg.system.service.ISysUserRoleService;
import com.bg.system.service.ISysUserService;
import com.bg.system.vo.RouteItemVO;
import com.bg.system.vo.RouteMetoVO;
import com.bg.system.vo.SysRoleVo;
import com.bg.system.vo.SysUserVo;
import com.google.common.collect.Lists;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * User Service 层实现类
 *
 * @author jiewus
 */
@Service
@Slf4j
@Transactional(rollbackFor = Throwable.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

  private final BgProperties bgProperties;
  private final ISysUserRoleService ISysUserRoleService;
  private final ISysRoleService ISysRoleService;
  private final ISysMenuService ISysMenuService;
  private final SysUserConvertMapper sysUserConvertMapper;

  @Transactional(rollbackFor = Exception.class)
  @Override
  public boolean save(SysUser sysUser) {
    // 用户新增校验
    this.saveOrUpdateCheck(sysUser, FieldFill.INSERT);

    sysUser.setId(null);
    String password = sysUser.getPassword();
    // 如果密码为空，则设置默认密码
    if (StringUtils.isBlank(password)) {
      password = bgProperties.getLoginInitPassword();
    }
    // 密码加密
    sysUser.setPassword(SecurityUtil.encryptPassword(password));

    // 如果头像为空，则设置默认头像
    if (StringUtils.isBlank(sysUser.getAvatar())) {
      sysUser.setAvatar(bgProperties.getLoginInitHead());
    }

    boolean insert = sysUser.insert();
    if (insert) {
      // 新增用户与角色关联
      List<String> roleIds = sysUser.getRoleIds();
      if (CollectionUtils.isNotEmpty(roleIds)) {
        roleIds.stream().forEach(item -> new SysUserRole().setUserId(sysUser.getId()).setRoleId(item).insert());
      }
    }

    return insert;
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public boolean updateById(SysUser sysUser) {
    String userId = sysUser.getId();
    // 用户修改校验
    this.saveOrUpdateCheck(sysUser, FieldFill.UPDATE);

    // 删除用户与角色关联
    ISysUserRoleService.remove(Wrappers.lambdaQuery(SysUserRole.class).eq(SysUserRole::getUserId, userId));

    // 新增用户与角色关联
    List<String> roleIds = sysUser.getRoleIds();
    if (CollectionUtils.isNotEmpty(roleIds)) {
      roleIds.stream().forEach(item -> new SysUserRole().setUserId(userId).setRoleId(item).insert());
    }

    return super.updateById(sysUser);
  }

  private void saveOrUpdateCheck(SysUser sysUser, FieldFill fill) {
    if (Objects.isNull(sysUser)) {
      throw BusinessException.build(ApiCode.BUSINESS_EXCEPTION.getCode(), "校验用户对象为空");
    }
    // 校验用户名是否存在
    Boolean isUserNameExists = sysUser.selectCount(
        new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, sysUser.getUsername()).ne(fill.equals(FieldFill.UPDATE), SysUser::getId, sysUser.getId())
    ) > 0;
    if (isUserNameExists) {
      throw BusinessException.build(ApiCode.BUSINESS_EXCEPTION.getCode(), "用户名已存在");
    }
    // 校验邮箱是否存在
    Boolean isEmailExists = sysUser.selectCount(
        new LambdaQueryWrapper<SysUser>().eq(SysUser::getEmail, sysUser.getEmail()).ne(fill.equals(FieldFill.UPDATE), SysUser::getId, sysUser.getId())
    ) > 0;
    if (isEmailExists) {
      throw BusinessException.build(ApiCode.BUSINESS_EXCEPTION.getCode(), "用户邮箱已存在");
    }
    // 校验手机号是否存在
    Boolean isPhoneExists = sysUser.selectCount(
        new LambdaQueryWrapper<SysUser>().eq(SysUser::getPhone, sysUser.getPhone()).ne(fill.equals(FieldFill.UPDATE), SysUser::getId, sysUser.getId())
    ) > 0;
    if (isPhoneExists) {
      throw BusinessException.build(ApiCode.BUSINESS_EXCEPTION.getCode(), "用户手机号已存在");
    }

  }

  @Override
  public SysUserVo getUserById(String id) {
    SysUser sysUser = this.getById(id);
    SysUserVo sysUserVo = sysUserConvertMapper.toDto(sysUser);
    // 手机号码脱敏处理
    sysUserVo.setPhone(PhoneUtil.desensitize(sysUserVo.getPhone()));
    // 获取关联角色集合
    List<SysRoleVo> roleList = ISysRoleService.getRolesByUserId(id);
    sysUserVo.setRoles(roleList.stream().map(SysRoleVo::getRoleCode).collect(Collectors.toSet()));
    sysUserVo.setRoleList(roleList);
    return sysUserVo;
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public boolean removeById(Serializable id) {
    // 删除用户与角色关联
    ISysUserRoleService.remove(Wrappers.lambdaQuery(SysUserRole.class).eq(SysUserRole::getUserId, id));
    return super.removeById(id);
  }

  @Override
  public Page<SysUserVo> getUserPageList(UserPageParam pageParam) {
    pageParam.pageSortsHandle(OrderItem.desc("create_time"));
    Page<SysUserVo> page = baseMapper.getSysUserPageList(pageParam.getPage(), pageParam);

    if (Objects.nonNull(page) && CollectionUtils.isNotEmpty(page.getRecords())) {
      // 手机号码脱敏处理
      page.getRecords().forEach(vo -> vo.setPhone(PhoneUtil.desensitize(vo.getPhone())));

      // 获取角色
      page.getRecords().forEach(item -> {
        List<SysRoleVo> roleList = ISysRoleService.getRolesByUserId(item.getId());
        item.setRoles(roleList.stream().map(SysRoleVo::getRoleCode).collect(Collectors.toSet()));
        item.setRoleList(roleList);
      });
    }
    return page;
  }

  @Override
  public SysUserVo selectUserByUsername(String username) {
    return baseMapper.selectUserByUsername(username);
  }

  @Override
  public List<RouteItemVO> getMenuList() {
    List<SysMenu> sysMenus;
    // 查询菜单
    List<String> roleIdList = SecurityUtil.getRoles().stream().map(RoleModel::getId).collect(Collectors.toList());
    List<SysRoleMenu> sysRoleMenuList = new SysRoleMenu().selectList(
        new QueryWrapper<SysRoleMenu>().lambda().in(SysRoleMenu::getRoleId, roleIdList));
    if (sysRoleMenuList.isEmpty()) {
      sysMenus = Lists.newArrayList();
    } else {
      Set<String> menuIds = sysRoleMenuList.stream().map(SysRoleMenu::getPermissionId).collect(Collectors.toSet());
      sysMenus = ISysMenuService.list(Wrappers.lambdaQuery(SysMenu.class)
          .in(SysMenu::getLevel, MenuLevelEnum.ONE.getCode(), MenuLevelEnum.TWO.getCode())
          .in(SysMenu::getId, menuIds)
          .orderByAsc(SysMenu::getSort)
      );
    }

    List<RouteItemVO> routeItemVOList = sysMenus.stream().filter(item -> item.getParentId() == null).map(item -> {
      RouteItemVO node = new RouteItemVO();
      node.setRoutePath(item.getLevel().equals(MenuLevelEnum.ONE.getCode()) ? "/" + item.getRoutePath() : item.getRoutePath());

      node.setComponent(item.getLevel().equals(MenuLevelEnum.ONE.getCode()) && item.getParentId() == null ? "LAYOUT" : item.getComponent());

      node.setName(StrUtil.upperFirst(item.getRoutePath()));
      node.setMeta(new RouteMetoVO());

      RouteMetoVO routeMetoVO = new RouteMetoVO();
      routeMetoVO.setTitle(item.getMenuName());
      routeMetoVO.setIcon(item.getIcon());
      if (item.getLevel().equals(MenuLevelEnum.TWO.getCode())) {
        routeMetoVO.setIgnoreKeepAlive(item.getKeepAlive().equals(KeepaliveEnum.YES.getCode()));
        if (item.getIsExt().equals(LinkExternalEnum.YES.getCode())) {
          if (item.getFrame().equals(FrameEnum.YES.getCode())) {
            routeMetoVO.setFrameSrc(item.getComponent());
          }
          if (item.getFrame().equals(FrameEnum.NO.getCode())) {
            node.setRoutePath(item.getComponent());
          }
        }
      }
      node.setMeta(routeMetoVO);
      node.setChildren(getChildrenList(item, sysMenus));
      return node;
    }).collect(Collectors.toList());
    return routeItemVOList;
  }

  private List<RouteItemVO> getChildrenList(SysMenu root, List<SysMenu> list) {
    List<RouteItemVO> childrenList = list.stream().filter(item -> item.getParentId() != null && item.getParentId().equals(root.getId())).map(item -> {
      RouteItemVO node = new RouteItemVO();
      node.setRoutePath(item.getLevel().equals(MenuLevelEnum.ONE.getCode()) ? "/" + item.getRoutePath() : item.getRoutePath());
      node.setComponent(item.getLevel().equals(MenuLevelEnum.ONE.getCode()) && item.getParentId() == null ? "LAYOUT" : item.getComponent());
      node.setName(StrUtil.upperFirst(item.getRoutePath()));
      node.setMeta(new RouteMetoVO());

      RouteMetoVO routeMetoVO = new RouteMetoVO();
      routeMetoVO.setTitle(item.getMenuName());
      routeMetoVO.setIcon(item.getIcon());
      routeMetoVO.setHideMenu(item.getIsShow() == 0);
      if (item.getLevel().equals(MenuLevelEnum.TWO.getCode())) {
        routeMetoVO.setIgnoreKeepAlive(!item.getKeepAlive().equals(KeepaliveEnum.YES.getCode()));
        if (item.getIsExt().equals(LinkExternalEnum.YES.getCode())) {
          if (item.getFrame().equals(FrameEnum.YES.getCode())) {
            routeMetoVO.setFrameSrc(item.getComponent());
          }
          if (item.getFrame().equals(FrameEnum.NO.getCode())) {
            node.setRoutePath(item.getComponent());
          }
        }
      }
      node.setMeta(routeMetoVO);
      node.setChildren(getChildrenList(item, list));
      return node;
    }).collect(Collectors.toList());
    return childrenList;
  }

}
