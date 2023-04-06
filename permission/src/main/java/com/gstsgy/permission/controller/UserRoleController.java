package com.gstsgy.permission.controller;

import com.gstsgy.base.model.ResponseBean;
import com.gstsgy.permission.bean.db.OperatorDO;
import com.gstsgy.permission.bean.db.RoleDO;
import com.gstsgy.permission.service.UserRoleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName UserRoleController
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/20 上午10:45
 **/
@RestController
@Tag(name = "用户角色相关")
@RequestMapping("/userrole")

public class UserRoleController {
    @Autowired
    private UserRoleService userRoleService;

    @GetMapping("roles")
    @Operation(description ="通过用户查角色")
    public ResponseBean queryRoleByUser(@Parameter(description  = "用户id",required = true) Long userId){
       return  userRoleService.queryRoleByUser(userId);
    }


    @GetMapping("users")
    @Operation(description ="通过角色查用户")
    public ResponseBean queryUserByRole(@Parameter(description  = "角色id",required = true) Long roleId){
        return  userRoleService.queryUserByRole(roleId);
    }


    @PostMapping("roleuser")
    @Operation(description ="给用户加角色")
    public ResponseBean addRoleByUser(@RequestBody List<RoleDO> roles,
                                      @Parameter(description  = "用户id url传递",required = true)Long userId){
        return  userRoleService.addRoleByUser( roles,  userId);
    }


    @PostMapping("userrole")
    @Operation(description ="给角色加用户")
    public ResponseBean addUserByRole(@RequestBody List<OperatorDO> users,
                                      @Parameter(description  = "角色id  url传递",required = true)Long roleId){
        return  userRoleService.addUserByRole( users,  roleId);
    }


    @GetMapping("allroles")
    @Operation(description ="获取所有角色")
    public ResponseBean queryRoles(){
        return  userRoleService.queryRoles();
    }


    @GetMapping("allusers")
    @Operation(description ="获取所有用户")
    public ResponseBean queryUsers(){
        return  userRoleService.queryUsers();
    }

}
