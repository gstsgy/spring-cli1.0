package com.gstsgy.permission.controller;


import com.gstsgy.base.model.ResponseBean;
import com.gstsgy.permission.bean.db.OperatorDO;
import com.gstsgy.permission.service.RoleMenuService;
import com.gstsgy.permission.service.UserService;
import com.gstsgy.permission.utils.JWTUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 描述: 认证接口
 *
 * @Auther: hujj
 * @Date: 2020/12/19 13:53
 */
@Tag(name = "权限")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleMenuService roleMenuService;


    /**
     * 登陆认证接口
     *
     * @param userDTO
     * @return
     */
    @Operation(description ="登录")
    @PostMapping("/login")
    public ResponseBean login(@RequestBody OperatorDO userDTO) {
        if (userDTO == null) {
            return ResponseBean.getParamEmptyException("用户名和密码");
        }
        OperatorDO queryOne = userService.queryOne(userDTO.getCode());
        if (queryOne == null) {
            queryOne = userService.queryOneByEmail(userDTO.getCode());
        }
        if (queryOne == null) {

            return ResponseBean.getParamUnmatchedException("用户名不存在");
        }

        if (!queryOne.getPasswd().equals(userService.encrypt(userDTO))) {
            return ResponseBean.getParamUnmatchedException("密码不正确");

        }

        String token = JWTUtil.createJwtToken(queryOne);
        return ResponseBean.getSuccess(token);
    }


    @Operation(description ="获取菜单")
    @GetMapping("/menus")
    public ResponseBean getMenus(Integer type) {

        return roleMenuService.getAllMenuTree(type, JWTUtil.getUser());
    }

    @Operation(description ="获取功能")
    @GetMapping("/funs")
    public ResponseBean getFuns(Integer type) {
        return roleMenuService.getAllFuns(type, JWTUtil.getUser());
    }
}
