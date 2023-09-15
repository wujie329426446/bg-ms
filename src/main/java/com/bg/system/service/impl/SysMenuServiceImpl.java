package com.bg.system.service.impl;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bg.commons.api.ApiCode;
import com.bg.commons.exception.BusinessException;
import com.bg.system.convert.SysMenuConvertMapper;
import com.bg.system.convert.SysMenuTreeConvertMapper;
import com.bg.system.entity.SysMenu;
import com.bg.system.entity.SysRoleMenu;
import com.bg.system.enums.MenuLevelEnum;
import com.bg.system.mapper.SysMenuMapper;
import com.bg.system.param.MenuPageParam;
import com.bg.system.service.ISysMenuService;
import com.bg.system.service.ISysRoleMenuService;
import com.bg.system.vo.SysMenuTreeVo;
import com.bg.system.vo.SysMenuVo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

  private final ISysRoleMenuService sysRoleMenuService;
  private final SysMenuTreeConvertMapper sysMenuTreeConvertMapper;
  private final SysMenuConvertMapper sysMenuConvertMapper;

  @Transactional(rollbackFor = Exception.class)
  @Override
  public boolean save(SysMenu sysMenu) {
    // 新增菜单校验
    this.saveOrUpdateCheck(sysMenu, FieldFill.INSERT);
    sysMenu.setId(null);
    return super.save(sysMenu);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public boolean updateById(SysMenu sysMenu) {
    // 修改菜单校验
    this.saveOrUpdateCheck(sysMenu, FieldFill.UPDATE);

    return super.updateById(sysMenu);
  }

  private void saveOrUpdateCheck(SysMenu sysMenu, FieldFill fill) {
    if (Objects.isNull(sysMenu)) {
      throw BusinessException.build(ApiCode.BUSINESS_EXCEPTION.getCode(), "校验菜单对象为空");
    }

    // 校验角色name唯一性
    Boolean isMenuNameExists = sysMenu.selectCount(
        new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getMenuName, sysMenu.getMenuName()).ne(fill.equals(FieldFill.UPDATE), SysMenu::getId, sysMenu.getId())
    ) > 0;
    if (isMenuNameExists) {
      throw BusinessException.build(ApiCode.BUSINESS_EXCEPTION.getCode(), "菜单名称已存在");
    }

    if (fill.equals(FieldFill.UPDATE)) {
      //菜单编码不允许修改,否则会影响到用户关联的权限
      sysMenu.setCode(null);
    } else {
      // 校验角色code唯一性
      Boolean isCodeExists = sysMenu.selectCount(
          new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getCode, sysMenu.getCode())
      ) > 0;
      if (isCodeExists) {
        throw BusinessException.build(ApiCode.BUSINESS_EXCEPTION.getCode(), "菜单编码已存在");
      }
    }
  }

  @Override
  public SysMenuVo getMenuById(String id) {
    SysMenu sysMenu = super.getById(id);
    SysMenuVo sysMenuVo = sysMenuConvertMapper.toDto(sysMenu);
    return sysMenuVo;
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public boolean removeById(Serializable id) {
    // 删除与角色关联关系
    sysRoleMenuService.remove(Wrappers.lambdaQuery(SysRoleMenu.class).eq(SysRoleMenu::getPermissionId, id));
    // 删除子menu
    List<SysMenu> menuList = super.list(Wrappers.lambdaQuery(SysMenu.class).eq(SysMenu::getParentId, id));
    List<String> ids = menuList.stream().map(i -> i.getId()).collect(Collectors.toList());
    this.removeChildren(ids);
    // 删除当前menu
    return super.removeById(id);
  }

  @Transactional(rollbackFor = Exception.class)
  void removeChildren(List<String> ids) {
    if (CollectionUtils.isNotEmpty(ids)) {
      // 删除与角色关联关系
      sysRoleMenuService.remove(Wrappers.lambdaQuery(SysRoleMenu.class).in(SysRoleMenu::getPermissionId, ids));
      // 删除子menu
      List<SysMenu> menuList = super.list(Wrappers.lambdaQuery(SysMenu.class).in(SysMenu::getParentId, ids));
      List<String> collect = menuList.stream().map(i -> i.getId()).collect(Collectors.toList());
      this.removeChildren(collect);
      // 删除当前menu
      super.removeByIds(ids);
    }
  }

  @Override
  public Page<SysMenu> getMenuPageList(MenuPageParam pageParam) {
    LambdaQueryWrapper<SysMenu> queryWrapper = Wrappers.lambdaQuery(SysMenu.class);
    String keyword = pageParam.getKeyword();
    String menuName = pageParam.getMenuName();
    String code = pageParam.getCode();

    // 条件查询
    queryWrapper
        .and(StringUtils.isNotBlank(keyword),
            i -> i.like(SysMenu::getMenuName, keyword)
                .or().like(SysMenu::getCode, keyword)
        )
        .like(StringUtils.isNotBlank(menuName), SysMenu::getMenuName, menuName)
        .like(StringUtils.isNotBlank(code), SysMenu::getCode, code)
        .orderByDesc(SysMenu::getCreateTime);

    Page<SysMenu> page = super.page(pageParam.getPage(), queryWrapper);
    return page;
  }

  @Override
  public List<SysMenuVo> getMenuList(String userId) {
    List<SysMenu> menuList;
    if (StringUtils.isNotBlank(userId)) {
      menuList = baseMapper.getMenuListByUser(userId);
    } else {
      menuList = super.list(Wrappers.lambdaQuery(SysMenu.class).orderByDesc(SysMenu::getCreateTime));
    }

    return sysMenuConvertMapper.toDto(menuList);
  }

  @Override
  public List<SysMenuTreeVo> getMenuTree(String userId) {
    List<SysMenu> menuList;
    if (StringUtils.isNotBlank(userId)) {
      menuList = baseMapper.getMenuListByUser(userId);
    } else {
      menuList = super.list(Wrappers.lambdaQuery(SysMenu.class).orderByDesc(SysMenu::getCreateTime));
    }
    // 转换成树形菜单
    List<SysMenuTreeVo> menuTreeList = this.convertToTree(menuList);
    return menuTreeList;
  }

  @Override
  public Set<String> getCodesByUser(String userId) {
    return baseMapper.getCodesByUser(userId);
  }

  @Override
  public List<SysMenuVo> getMenuListByRole(String roleId) {
    List<SysMenu> menuList = baseMapper.getMenuListByRole(roleId);
    List<SysMenuVo> sysMenuVoList = sysMenuConvertMapper.toDto(menuList);
    return sysMenuVoList;
  }

  private List<SysMenuTreeVo> convertToTree(List<SysMenu> list) {
    if (CollectionUtils.isEmpty(list)) {
      return new ArrayList<>();
    }
    // 按level分组获取map
    Map<MenuLevelEnum, List<SysMenu>> map = list.stream().collect(Collectors.groupingBy(SysMenu::getLevel));
    List<SysMenuTreeVo> treeVos = new ArrayList<>();
    // 循环获取三级菜单树形集合
    for (SysMenu one : map.get(MenuLevelEnum.ONE)) {
      SysMenuTreeVo oneVo = sysMenuTreeConvertMapper.toDto(one);
      String oneParentId = oneVo.getParentId();
      if (oneParentId == null) {
        treeVos.add(oneVo);
      }
      List<SysMenu> twoList = map.get(MenuLevelEnum.TWO);
      if (CollectionUtils.isNotEmpty(twoList)) {
        for (SysMenu two : twoList) {
          SysMenuTreeVo twoVo = sysMenuTreeConvertMapper.toDto(two);
          if (two.getParentId().equals(one.getId())) {
            if (oneVo.getChildren() == null) {
              oneVo.setChildren(new ArrayList<>());
            }
            oneVo.getChildren().add(twoVo);
          }
          List<SysMenu> threeList = map.get(MenuLevelEnum.THREE);
          if (CollectionUtils.isNotEmpty(threeList)) {
            for (SysMenu three : threeList) {
              if (three.getParentId().equals(two.getId())) {
                SysMenuTreeVo threeVo = sysMenuTreeConvertMapper.toDto(three);
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


}
