package com.bg.system.service.impl;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bg.commons.api.ApiCode;
import com.bg.commons.api.ApiResult;
import com.bg.commons.exception.BusinessException;
import com.bg.system.convert.SysDeptConvertMapper;
import com.bg.system.convert.SysDeptTreeConvertMapper;
import com.bg.system.entity.SysDept;
import com.bg.system.mapper.SysDeptMapper;
import com.bg.system.param.DeptPageParam;
import com.bg.system.service.ISysDeptService;
import com.bg.system.vo.SysDeptTreeVo;
import com.bg.system.vo.SysDeptVo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * <pre>
 * 部门 服务实现类
 * </pre>
 *
 * @author jiewus
 */
@Slf4j
@Service
@Transactional(rollbackFor = Throwable.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements ISysDeptService {

  private final SysDeptTreeConvertMapper sysDeptTreeConvertMapper;
  private final SysDeptConvertMapper sysDeptConvertMapper;

  @Transactional(rollbackFor = Exception.class)
  @Override
  public boolean save(SysDept sysDept) {
    // 新增校验
    this.saveOrUpdateCheck(sysDept, FieldFill.INSERT);
    // 封装levelCode:部门编码|部门编码|部门编码
    this.levelCodeHandler(sysDept);
    // 封装level层级
    this.levelHandler(sysDept);

    sysDept.setId(null);
    return super.save(sysDept);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public boolean updateById(SysDept sysDept) {
    // 修改校验
    this.saveOrUpdateCheck(sysDept, FieldFill.UPDATE);
    // 封装levelCode:部门编码|部门编码|部门编码 ,部门编码不允许修改,否则会影响到子集的levelCode
//    this.levelCodeHandler(sysDept);
    // 封装level层级
    this.levelHandler(sysDept);

    return super.updateById(sysDept);
  }

  /**
   * 新增/修改校验
   *
   * @param sysDept sysDept
   * @param fill    fill
   */
  private void saveOrUpdateCheck(SysDept sysDept, FieldFill fill) {
    if (Objects.isNull(sysDept)) {
      throw BusinessException.build(ApiCode.BUSINESS_EXCEPTION.getCode(), "校验部门对象为空");
    }
    //部门编码不允许修改,否则会影响到子集的levelCode
    if (fill.equals(FieldFill.UPDATE)) {
      sysDept.setDeptCode(null);
    }
    // 校验部门名称唯一性(同级别下唯一)
    String parentId = sysDept.getParentId();
    LambdaQueryWrapper<SysDept> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(SysDept::getDeptName, sysDept.getDeptName());
    if (StringUtils.isEmpty(parentId)) {
      queryWrapper.isNull(SysDept::getParentId);
    } else {
      queryWrapper.eq(SysDept::getParentId, parentId);
    }

    queryWrapper.ne(fill.equals(FieldFill.UPDATE), SysDept::getId, sysDept.getId());

    Boolean isDeptNameExists = sysDept.selectCount(queryWrapper) > 0;
    if (isDeptNameExists) {
      throw BusinessException.build(ApiCode.BUSINESS_EXCEPTION.getCode(), "部门名称已存在");
    }
  }

  /**
   * 封装levelCode:部门编码|部门编码|部门编码
   *
   * @param sysDept sysDept
   */
  private void levelCodeHandler(SysDept sysDept) {
    String deptCode = sysDept.getDeptCode();
    String parentId = sysDept.getParentId();
    if (StringUtils.isBlank(parentId)) {
      sysDept.setLevelCode(deptCode);
    } else {
      String levelCode = super.getById(parentId).getLevelCode();
      sysDept.setLevelCode(String.format("%s|%s", levelCode, deptCode));
    }
  }

  /**
   * 封装level
   *
   * @param sysDept sysDept
   */
  private void levelHandler(SysDept sysDept) {
    String parentId = sysDept.getParentId();
    if (StringUtils.isEmpty(parentId)) {
      sysDept.setLevel(1);
    } else {
      SysDept parent = super.getById(parentId);
      sysDept.setLevel(parent.getLevel() + 1);
    }

  }

  @Override
  public SysDeptVo getDeptById(Serializable id) {
    SysDept sysDept = super.getById(id);
    SysDeptVo sysDeptVo = sysDeptConvertMapper.toDto(sysDept);
    return sysDeptVo;
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public ApiResult<String> deleteDept(Serializable id) {
    // 删除部门时判断部门及部门下有没有关联的用户
    SysDept sysDept = super.getById(id);
    String levelCode = sysDept.getLevelCode();
    List<String> deptNameList = baseMapper.getExitsUserDeptName(levelCode);
    if (deptNameList.isEmpty()) {
      // 删除部门及子集部门
      super.remove(Wrappers.lambdaQuery(SysDept.class).likeRight(SysDept::getLevelCode, levelCode));
      return ApiResult.success();
    } else {
      return ApiResult.fail(String.format("%s部门下有关联用户,无法删除", deptNameList));
    }
  }


  @Override
  public Page<SysDept> getDeptPageList(DeptPageParam pageParam) {
    LambdaQueryWrapper<SysDept> queryWrapper = Wrappers.lambdaQuery(SysDept.class);
    String keyword = pageParam.getKeyword();
    String deptName = pageParam.getDeptName();
    String parentId = pageParam.getParentId();
    Integer status = pageParam.getStatus();

    // 条件查询
    queryWrapper
        .and(StringUtils.isNotBlank(keyword),
            i -> i.like(SysDept::getDeptName, keyword)
        )
        .like(StringUtils.isNotBlank(deptName), SysDept::getDeptName, deptName)
        .like(StringUtils.isNotBlank(parentId), SysDept::getParentId, parentId)
        .like(Objects.nonNull(status), SysDept::getStatus, status)
    ;

    Page<SysDept> page = super.page(pageParam.getPage(), queryWrapper);
    return page;
  }

  @Override
  public List<SysDeptVo> getDeptList(DeptPageParam pageParam) {
    LambdaQueryWrapper<SysDept> queryWrapper = Wrappers.lambdaQuery(SysDept.class);
    String keyword = pageParam.getKeyword();
    String deptName = pageParam.getDeptName();
    String parentId = pageParam.getParentId();
    Integer status = pageParam.getStatus();

    // 条件查询
    queryWrapper
        .and(StringUtils.isNotBlank(keyword),
            i -> i.like(SysDept::getDeptName, deptName)
        )
        .like(StringUtils.isNotBlank(deptName), SysDept::getDeptName, deptName)
        .like(StringUtils.isNotBlank(parentId), SysDept::getParentId, parentId)
        .like(Objects.nonNull(status), SysDept::getStatus, status)
    ;
    List<SysDept> list = super.list(queryWrapper);
    return sysDeptConvertMapper.toDto(list);
  }

  @Override
  public List<SysDeptTreeVo> getDeptTreeList() {
    List<SysDept> sysDeptList = super.list(Wrappers.lambdaQuery(SysDept.class));
    List<SysDeptTreeVo> treeVos = new ArrayList<>();
    if (sysDeptList.isEmpty()) {
      return treeVos;
    }
    List<SysDeptTreeVo> list = sysDeptTreeConvertMapper.toDto(sysDeptList);
    for (SysDeptTreeVo treeVo : list) {
      if (StringUtils.isBlank(treeVo.getParentId())) {
        treeVos.add(findChildren(treeVo, list));
      }
    }
    return treeVos;
  }

  /**
   * 递归获取树形结果列表
   *
   * @param tree
   * @param list
   * @return
   */
  private SysDeptTreeVo findChildren(SysDeptTreeVo tree, List<SysDeptTreeVo> list) {
    for (SysDeptTreeVo vo : list) {
      if (tree.getId().equals(vo.getParentId())) {
        if (tree.getChildren() == null) {
          tree.setChildren(new ArrayList<>());
        }
        tree.getChildren().add(findChildren(vo, list));
      }
    }
    return tree;
  }

}
