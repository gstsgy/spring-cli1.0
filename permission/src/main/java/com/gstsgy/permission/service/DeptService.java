package com.gstsgy.permission.service;

import com.gstsgy.base.model.ResponseBean;
import com.gstsgy.permission.bean.db.DeptDO;
import com.gstsgy.permission.bean.view.TreeNodeVO;

/**
 * @ClassName DeptService
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/16 下午4:29
 **/
public interface DeptService {

    ResponseBean addDept(DeptDO deptDO);

    void deleteDepts(TreeNodeVO deptDO);

    ResponseBean queryDept();


    ResponseBean updateDept(TreeNodeVO deptDO);

    //获取该部门及下属部门的人员数量
    Long getDeptPeopleCount(Long id);

    ResponseBean queryDeptEnum();
}
