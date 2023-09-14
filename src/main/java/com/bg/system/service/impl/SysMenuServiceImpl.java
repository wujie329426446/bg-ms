package com.bg.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bg.commons.enums.StateEnum;
import com.bg.commons.exception.BusinessException;
import com.bg.commons.model.RoleModel;
import com.bg.commons.utils.SecurityUtil;
import com.bg.system.convert.SysMenuConvertMapper;
import com.bg.system.entity.SysMenu;
import com.bg.system.entity.SysRoleMenu;
import com.bg.system.enums.FrameEnum;
import com.bg.system.enums.KeepaliveEnum;
import com.bg.system.enums.LinkExternalEnum;
import com.bg.system.enums.MenuLevelEnum;
import com.bg.system.mapper.SysMenuMapper;
import com.bg.system.param.MenuPageParam;
import com.bg.system.service.ISysMenuService;
import com.bg.system.service.ISysRoleMenuService;
import com.bg.system.vo.RouteItemVO;
import com.bg.system.vo.RouteMetoVO;
import com.bg.system.vo.SysPermissionTreeVo;
import com.bg.system.vo.SysPermissionVo;
import com.google.common.collect.Lists;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * <pre>
 * 系统权限 服务实现类
 * </pre>
 *
 * @author jiewus
 */
@Slf4j
@Service
@Transactional(rollbackFor = Throwable.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

  private final ISysRoleMenuService ISysRoleMenuService;
  private final SysMenuConvertMapper sysMenuConvertMapper;

  @Transactional(rollbackFor = Exception.class)
  @Override
  public boolean saveSysPermission(SysMenu sysMenu) {
    sysMenu.setId(null);
    return super.save(sysMenu);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public boolean updateSysPermission(SysMenu sysMenu) {
    // 获取权限
    if (getById(sysMenu.getId()) == null) {
      throw BusinessException.build("权限不存在");
    }
//        sysPermission.setUpdateTime(new Date());
    return super.updateById(sysMenu);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public boolean deleteSysPermission(String id) {
    boolean isExists = ISysRoleMenuService.count(Wrappers.lambdaQuery(SysRoleMenu.class).eq(SysRoleMenu::getPermissionId, id)) > 0;
    if (isExists) {
      throw BusinessException.build("该权限存在角色关联关系，不能删除");
    }
    return super.removeById(id);
  }

  @Override
  public SysPermissionVo getSysPermissionById(Serializable id) {
    return baseMapper.getSysPermissionById(id);
  }

  @Override
  public Page<SysPermissionVo> getSysPermissionPageList(MenuPageParam pageParam) {
    pageParam.pageSortsHandle(OrderItem.desc("create_time"));
    Page<SysPermissionVo> page = baseMapper.getSysPermissionPageList(pageParam.getPage(), pageParam);
    return page;
  }

  @Override
  public boolean isExistsByPermissionIds(List<String> permissionIds) {
    if (CollectionUtils.isEmpty(permissionIds)) {
      return false;
    }
    Wrapper wrapper = lambdaQuery().in(SysMenu::getId, permissionIds).getWrapper();
    return baseMapper.selectCount(wrapper).intValue() == permissionIds.size();
  }

  @Override
  public List<SysMenu> getAllMenuList() {
    SysMenu sysMenu = new SysMenu();
    sysMenu.setStatus(StateEnum.ENABLE.getCode());
    // 获取所有已启用的权限列表
    return baseMapper.selectList(new QueryWrapper(sysMenu));
  }

  @Override
  public List<SysPermissionTreeVo> getAllMenuTree() {
    List<SysMenu> list = getAllMenuList();
    // 转换成树形菜单
    List<SysPermissionTreeVo> treeVos = convertSysPermissionTreeVoList(list);
    return treeVos;
  }

  @Override
  public List<SysPermissionTreeVo> convertSysPermissionTreeVoList(List<SysMenu> list) {
    if (CollectionUtils.isEmpty(list)) {
      throw new IllegalArgumentException("SysPermission列表不能为空");
    }
    // 按level分组获取map
    Map<Integer, List<SysMenu>> map = list.stream().collect(Collectors.groupingBy(SysMenu::getLevel));
    List<SysPermissionTreeVo> treeVos = new ArrayList<>();
    // 循环获取三级菜单树形集合
    for (SysMenu one : map.get(MenuLevelEnum.ONE.getCode())) {
      SysPermissionTreeVo oneVo = sysMenuConvertMapper.toDto(one);
      String oneParentId = oneVo.getParentId();
      if (oneParentId == null) {
        treeVos.add(oneVo);
      }
      List<SysMenu> twoList = map.get(MenuLevelEnum.TWO.getCode());
      if (CollectionUtils.isNotEmpty(twoList)) {
        for (SysMenu two : twoList) {
          SysPermissionTreeVo twoVo = sysMenuConvertMapper.toDto(two);
          if (two.getParentId().equals(one.getId())) {
            if (oneVo.getChildren() == null) {
              oneVo.setChildren(new ArrayList<>());
            }
            oneVo.getChildren().add(twoVo);
          }
          List<SysMenu> threeList = map.get(MenuLevelEnum.THREE.getCode());
          if (CollectionUtils.isNotEmpty(threeList)) {
            for (SysMenu three : threeList) {
              if (three.getParentId().equals(two.getId())) {
                SysPermissionTreeVo threeVo = sysMenuConvertMapper.toDto(three);
                if (twoVo.getChildren() == null) {
                  twoVo.setChildren(new ArrayList<>());
                }
                twoVo.getChildren().add(threeVo);
              }
            }
          }
        }
      }

    }
    return treeVos;
  }

  @Override
  public Set<String> getPermissionCodesByUserId(String userId) {
    return baseMapper.getPermissionCodesByUserId(userId);
  }

  @Override
  public List<SysMenu> getMenuListByUserId(String userId) {
    return baseMapper.getMenuListByUserId(userId);
  }

  @Override
  public List<SysPermissionTreeVo> getMenuTreeByUserId(String userId) {
    List<SysMenu> list = getMenuListByUserId(userId);
    // 转换成树形菜单
    List<SysPermissionTreeVo> treeVos = convertSysPermissionTreeVoList(list);
    return treeVos;
  }

  @Override
  public List<String> getPermissionIdsByRoleId(String roleId) {

    // 根据角色id获取该对应的所有三级权限ID

    return null;
  }

  @Override
  public List<String> getThreeLevelPermissionIdsByRoleId(String roleId) {
    return baseMapper.getThreeLevelPermissionIdsByRoleId(roleId);
  }

  @Override
  public List<SysPermissionTreeVo> getNavMenuTree() {
    List<Integer> levels = Arrays.asList(MenuLevelEnum.ONE.getCode(), MenuLevelEnum.TWO.getCode());
    Wrapper wrapper = lambdaQuery()
        .in(SysMenu::getLevel, levels)
        .eq(SysMenu::getStatus, StateEnum.ENABLE.getCode())
        .getWrapper();

    List<SysMenu> list = baseMapper.selectList(wrapper);

    return convertSysPermissionTreeVoList(list);

  }

  @Override
  public List<SysMenu> listRoleMenus(String roleId) {
    // TODO: 2023/9/1 优化查询，不要遍历查子集
    List<SysRoleMenu> sysRoleMenuList = ISysRoleMenuService.list(Wrappers.lambdaQuery(SysRoleMenu.class).eq(SysRoleMenu::getRoleId, roleId));
    return sysRoleMenuList.stream().map(item ->
        this.getById(item.getPermissionId())
    ).collect(Collectors.toList());
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
      sysMenus = this.list(Wrappers.lambdaQuery(SysMenu.class)
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
