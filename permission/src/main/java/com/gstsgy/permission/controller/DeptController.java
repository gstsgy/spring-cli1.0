package com.gstsgy.permission.controller;


import com.gstsgy.base.model.ResponseBean;
import com.gstsgy.permission.bean.db.DeptDO;
import com.gstsgy.permission.bean.view.TreeNodeVO;
import com.gstsgy.permission.service.DeptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName DeptController
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/16 下午4:27
 **/
@RestController
@Tag(name = "部门")
@RequestMapping("/dept")
public class DeptController {
    @Autowired
    private DeptService deptService;

    @Operation(description ="新增一个部门")
    @PostMapping("dept")
    public ResponseBean addDept(@RequestBody DeptDO deptDO) {
        return deptService.addDept(deptDO);
    }

    @Operation(description ="删除部门")
    @DeleteMapping("depts")
    public ResponseBean deleteDepts(@RequestBody TreeNodeVO deptDO) {
        if(deptDO==null){
            return  ResponseBean.getParamEmptyException("部门信息");
        }
        long peopleCount = deptService.getDeptPeopleCount(Long.parseLong(deptDO.getValue()));
        if(peopleCount>0){
            return ResponseBean.getParamUnmatchedException("该部门还存在人员，不允许删除");
        }
        deptService.deleteDepts(deptDO);
        return ResponseBean.getSuccess(true);
    }

    @Operation(description ="查询部门")
    @GetMapping("depts")
    public ResponseBean queryDept() {

        return deptService.queryDept();
    }

    @Operation(description ="修改一个部门")
    @PutMapping("dept")
    public ResponseBean updateDept(@RequestBody TreeNodeVO deptDO) {
        return deptService.updateDept(deptDO);
    }

    @GetMapping("deptpeoplecount")
    @Operation(description ="获取该部门及下属部门的人员数量")
    public ResponseBean getDeptPeopleCount( @Parameter(description ="部门id") Long  id){
        return ResponseBean.getSuccess(deptService.getDeptPeopleCount(id));
    }

    @Operation(description ="查询部门枚举")
    @GetMapping("deptenum")
    public ResponseBean queryDeptEnum() {

        return deptService.queryDeptEnum();
    }

}
