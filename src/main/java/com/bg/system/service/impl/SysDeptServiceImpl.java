package com.bg.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bg.commons.enums.StateEnum;
import com.bg.commons.pagination.PageInfo;
import com.bg.commons.pagination.Paging;
import com.bg.commons.service.impl.BaseServiceImpl;
import com.bg.system.convert.SysDepartmentConvertMapper;
import com.bg.system.entity.SysDept;
import com.bg.system.mapper.SysDeptMapper;
import com.bg.system.param.SysDepartmentPageParam;
import com.bg.system.service.SysDeptService;
import com.bg.system.vo.SysDepartmentTreeVo;
import com.bg.system.vo.SysDepartmentVo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
public class SysDeptServiceImpl extends BaseServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

  @Autowired
  private SysDeptMapper sysDeptMapper;

  @Autowired
  private SysDepartmentConvertMapper sysDepartmentConvertMapper;

  @Transactional(rollbackFor = Exception.class)
  @Override
  public boolean saveSysDepartment(SysDept sysDept) throws Exception {
    sysDept.setId(null);
    return super.save(sysDept);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public boolean updateSysDepartment(SysDept sysDept) throws Exception {
    return super.updateById(sysDept);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public boolean deleteSysDepartment(String id) throws Exception {
    return super.removeById(id);
  }

  @Override
  public SysDepartmentVo getSysDepartmentById(Serializable id) throws Exception {
    return sysDeptMapper.getSysDepartmentById(id);
  }

  @Override
  public Paging<SysDepartmentVo> getSysDepartmentPageList(SysDepartmentPageParam sysDepartmentPageParam) throws Exception {
    Page<SysDepartmentVo> page = new PageInfo<>(sysDepartmentPageParam, OrderItem.desc("create_time"));
    IPage<SysDepartmentVo> iPage = sysDeptMapper.getSysDepartmentPageList(page, sysDepartmentPageParam);
    return new Paging(iPage);
  }

  @Override
  public boolean isEnableSysDepartment(String id) throws Exception {
    SysDept sysDept = new SysDept();
    sysDept.setId(id);
    sysDept.setStatus(StateEnum.ENABLE.getCode());
    Long count = sysDeptMapper.selectCount(new QueryWrapper<>(sysDept));
    return count > 0;
  }

  @Override
  public List<SysDept> getAllDepartmentList() {
    SysDept sysDept = new SysDept();
    sysDept.setStatus(StateEnum.ENABLE.getCode());
    // 获取所有已启用的部门列表
    return sysDeptMapper.selectList(new QueryWrapper(sysDept));
  }

  @Override
  public List<SysDepartmentTreeVo> getDepartmentTree() {
    List<SysDept> sysDeptList = getAllDepartmentList();
    if (CollectionUtils.isEmpty(sysDeptList)) {
      return null;
    }
    List<SysDepartmentTreeVo> list = sysDepartmentConvertMapper.toDto(sysDeptList);
    List<SysDepartmentTreeVo> treeVos = new ArrayList<>();
    for (SysDepartmentTreeVo treeVo : list) {
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
  public SysDepartmentTreeVo findChildren(SysDepartmentTreeVo tree, List<SysDepartmentTreeVo> list) {
    for (SysDepartmentTreeVo vo : list) {
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
