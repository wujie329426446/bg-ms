package com.bg.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bg.commons.enums.StateEnum;
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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
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
  public boolean saveSysDepartment(SysDept sysDept) {
    sysDept.setId(null);
    return super.save(sysDept);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public boolean updateSysDepartment(SysDept sysDept) {
    return super.updateById(sysDept);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public boolean deleteSysDepartment(String id) {
    return super.removeById(id);
  }

  @Override
  public SysDeptVo getSysDepartmentById(Serializable id) {
    SysDept sysDept = this.getById(id);
    SysDeptVo sysDeptVo = sysDeptConvertMapper.toDto(sysDept);
    return sysDeptVo;
  }

  @Override
  public Page<SysDeptVo> getSysDepartmentPageList(DeptPageParam pageParam) {
    pageParam.pageSortsHandle(OrderItem.desc("create_time"));
    Page<SysDeptVo> page = baseMapper.getSysDeptPageList(pageParam.getPage(), pageParam);
    return page;
  }

  @Override
  public List<SysDept> getAllDepartmentList() {
    SysDept sysDept = new SysDept();
    sysDept.setStatus(StateEnum.ENABLE.getCode());
    // 获取所有已启用的部门列表
    return baseMapper.selectList(new QueryWrapper(sysDept));
  }

  @Override
  public List<SysDeptTreeVo> getDepartmentTree() {
    List<SysDept> sysDeptList = getAllDepartmentList();
    if (CollectionUtils.isEmpty(sysDeptList)) {
      return null;
    }
    List<SysDeptTreeVo> list = sysDeptTreeConvertMapper.toDto(sysDeptList);
    List<SysDeptTreeVo> treeVos = new ArrayList<>();
    for (SysDeptTreeVo treeVo : list) {
      if (treeVo.getParentId() == null) {
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
  public SysDeptTreeVo findChildren(SysDeptTreeVo tree, List<SysDeptTreeVo> list) {
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
