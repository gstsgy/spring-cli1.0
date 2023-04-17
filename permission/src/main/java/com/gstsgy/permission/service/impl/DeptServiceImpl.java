package com.gstsgy.permission.service.impl;

import com.gstsgy.base.model.LangCode;
import com.gstsgy.permission.bean.view.WebEnumVO;
import com.gstsgy.permission.service.DeptService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gstsgy.base.model.ResponseBean;
import com.gstsgy.permission.bean.db.DeptDO;
import com.gstsgy.permission.bean.db.OperatorDO;
import com.gstsgy.permission.bean.view.TreeNodeVO;
import com.gstsgy.permission.mapper.DeptMapper;
import com.gstsgy.permission.mapper.OperatorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName DeptServiceImpl
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/16 下午4:29
 **/
@Transactional
@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptMapper deptMapper;


    @Override
    public ResponseBean addDept(DeptDO deptDO) {
        List<DeptDO> lists = queryChildren(deptDO.getParentId());
        long count = lists.stream().filter(item -> item.getName().equals(deptDO.getName())).count();
        if (count > 0) {
            return ResponseBean.getParamUnmatchedException(LangCode.of("permission.dept_name_unique"));
        }

        return ResponseBean.getSuccess(deptMapper.insert(deptDO) > 0);
    }

    @Override
    public void deleteDepts(TreeNodeVO deptDO) {
        if (deptDO == null) {
            return;
        }

        DeptDO tmp = deptMapper.selectById(deptDO.getId());
        if (tmp != null) {
            deptMapper.deleteById(tmp.getId());
        }
        if (deptDO.getChildren() == null || deptDO.getChildren().size() == 0) {
            return;
        }
        deptDO.getChildren().forEach(this::deleteDepts);
    }

    @Override
    public ResponseBean queryDept() {
        List<DeptDO> root = queryChildren(null);
        List<TreeNodeVO> trees =
                root.stream().map(item -> new TreeNodeVO(item.getId(),item.getName())).collect(Collectors.toList());
        trees.forEach(this::getTree);
        return ResponseBean.getSuccess(trees);
        //return null;
    }

    @Override
    public ResponseBean updateDept(TreeNodeVO deptDO) {


        DeptDO tmp = deptMapper.selectById(deptDO.getId());
        if (tmp != null) {
            tmp.setName(deptDO.getLabel());
            return ResponseBean.getSuccess(deptMapper.updateById(tmp) > 0);
        }
        return ResponseBean.getParamUnmatchedException(LangCode.of("core.empty"), LangCode.of("permission.dept"));
    }

    @Autowired
    private OperatorMapper operatorMapper;

    @Override
    public Long getDeptPeopleCount(Long id) {
        QueryWrapper<OperatorDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(OperatorDO::getDeptId, id);
        List<OperatorDO> lsittmp = operatorMapper.selectList(queryWrapper);
        List<DeptDO> list = queryChildren(id);
        if (list == null || list.size() == 0) {
            return (long) lsittmp.size();
        }
        Long count1 = list.stream().map(item -> getDeptPeopleCount(item.getId())).reduce(Long::sum).get();
        return (long) lsittmp.size() + count1;
    }

    @Override
    public ResponseBean queryDeptEnum() {
        QueryWrapper<DeptDO> queryWrapper = new QueryWrapper<>();
        List<DeptDO> list = deptMapper.selectList(queryWrapper);
        List<WebEnumVO> resultData = list.stream().map(it -> new WebEnumVO(it.getId(), it.getName())).collect(Collectors.toList());
        return ResponseBean.getSuccess(resultData);
    }

    private List<DeptDO> queryChildren(Long parentId) {
        QueryWrapper<DeptDO> queryWrapper = new QueryWrapper<>();
        if (parentId == null) {
            queryWrapper.lambda().isNull(DeptDO::getParentId);
        } else {
            queryWrapper.lambda().eq(DeptDO::getParentId, parentId);
        }
        return deptMapper.selectList(queryWrapper);
    }

    private void getTree(TreeNodeVO treeNodeView) {
        List<DeptDO> depts = queryChildren(treeNodeView.getId());
        if (depts == null || depts.size() == 0) {
            return;
        }
        List<TreeNodeVO> trees = depts.stream().map(item -> new TreeNodeVO(item.getId(),item.getName())).collect(Collectors.toList());
        treeNodeView.setChildren(trees);
        treeNodeView.getChildren().forEach(this::getTree);
    }
}
