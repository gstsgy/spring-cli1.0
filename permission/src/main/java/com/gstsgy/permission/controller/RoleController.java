package com.gstsgy.permission.controller;

import com.gstsgy.base.model.ResponseBean;
import com.gstsgy.permission.bean.db.RoleDO;
import com.gstsgy.permission.service.RoleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName RoleController
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/19 下午3:15
 **/

@RestController
@Tag(name = "角色")
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;


    @Operation(description ="新增一个角色")
    @PostMapping("role")
    public ResponseBean addRole(@RequestBody RoleDO roleDO) {
        return roleService.addRole(roleDO);
    }
    @Operation(description ="删除角色")
    @DeleteMapping("roles")
    public ResponseBean deleteRoles(@RequestBody List<RoleDO> roleDOs) {
        return roleService.deleteRoles(roleDOs);
    }

    @Operation(description ="查询角色")
    @GetMapping("roles")
    public ResponseBean queryRole(@Parameter(description ="角色名称") String name, Integer pageSize, Integer pageNum) {

        return roleService.queryRoles(name,pageSize,pageNum);
    }
    @Operation(description ="修改一个角色")
    @PutMapping("role")
    public ResponseBean updateRole(@RequestBody RoleDO roleDO) {
        return roleService.updateRole(roleDO);
    }
    @Operation(description ="查询角色枚举")
    @GetMapping("roleenum")
    public ResponseBean queryRoleEnum() {

        return roleService.queryRoleEnum();
    }

}
