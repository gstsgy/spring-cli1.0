package com.gstsgy.permission.controller;

import com.gstsgy.base.model.ResponseBean;
import com.gstsgy.permission.service.RoleMenuService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName RoleMenuController
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/23 上午10:39
 **/
@RestController
@Tag(name = "角色菜单相关")
@RequestMapping("/rolemenu")
public class RoleMenuController {
    @Autowired
    private RoleMenuService roleMenuService;

    @GetMapping("menutree")
    @Operation(description ="获取所有菜单树")
    public ResponseBean queryMenuTree(@Parameter(description  = "菜单类型", required = true) Integer type) {
        return roleMenuService.getAllMenuTree(type);
    }

    @GetMapping("menus")
    @Operation(description ="根据角色获取所有菜单id")
    public ResponseBean getAllMenuByRole(@Parameter(description  = "角色id", required = true)Long roleId,
                                         @Parameter(description  = "菜单类型", required = true) Integer type) {
        return roleMenuService.getAllMenuByRole(roleId,type);
    }

    @PostMapping("rolemenu")
    @Operation(description ="保存角色菜单")
    public ResponseBean saveRoleMenu(@Parameter(description  = "角色id 通过param传", required = true)Long roleId,
                                     @Parameter(description  = "menu ID 通过data传", required = true)
                                     @RequestBody List<Long> menus) {
        return roleMenuService.saveMenu(roleId,menus);
    }


    @GetMapping("usermenutree")
    public ResponseBean queryMenuTree(Integer type,Long userId) {

        return roleMenuService.getAllMenuTree(type,userId);
    }

    @GetMapping("usermenufuns")
    public ResponseBean queryMenuFuns(Integer type,Long userId) {

        return roleMenuService.getAllFuns(type,userId);
    }
}
