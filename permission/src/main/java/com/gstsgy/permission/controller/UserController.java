package com.gstsgy.permission.controller;

import com.gstsgy.base.model.ResponseBean;
import com.gstsgy.permission.bean.db.OperatorDO;
import com.gstsgy.permission.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @ClassName UserController
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/14 下午3:27
 **/
//
@RestController
@Tag(name = "用户管理")
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Operation(description ="新增用户")
    @PostMapping("/user")
    public ResponseBean addUser(@RequestBody OperatorDO operatorDO) {
        return userService.addUser(operatorDO);
    }

    @Operation(description ="删除用户")
    @DeleteMapping("/users")
    public ResponseBean deleteUser(@RequestBody List<OperatorDO> operatorDOs) {
        return userService. deleteUser( operatorDOs);
    }

    @Operation(description ="查询用户")
    @GetMapping("/users")
    public ResponseBean queryUser(OperatorDO operatorDO, Integer pageNum, Integer pageSize) {
        return userService.queryUser( operatorDO,  pageNum, pageSize);
    }

    @Operation(description ="修改用户")
    @PutMapping("/user")
    public ResponseBean updateUser(@RequestBody OperatorDO operatorDO) {
        return userService.updateUser( operatorDO);
    }

    @Operation(description ="获取用户职位")
    @GetMapping("/userposition")
    public ResponseBean getUserPosition() {
        return userService.getUserPosition();
    }


    @Operation(description ="修改密码")
    @PutMapping("/userpw")
    public ResponseBean updateUserpw(@RequestBody OperatorDO user,@Parameter(description ="加到请求头里") @RequestHeader("oldPwd") String oldPwd) throws Exception {
        return userService.updateUserpw(user,oldPwd);
    }

    @Operation(description ="根据用户名查询用户")
    @GetMapping("/user")
    public ResponseBean queryUserByCode(String code) {
        return  ResponseBean.getSuccess(userService.queryOne(code)) ;
    }



    @Operation(description ="查询用户枚举")
    @GetMapping("/userenum")
    public ResponseBean queryUserEnum(@Parameter(description ="部门id，注意大小写") Long deptId) {
        return userService.queryUserEnum(deptId);
    }


    @Operation(description ="重置密码")
    @PostMapping("/restpassword")
    public ResponseBean restpassword(@RequestBody OperatorDO user) {
        return userService.restpassword(user);
    }
}
