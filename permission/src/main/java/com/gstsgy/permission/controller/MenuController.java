package com.gstsgy.permission.controller;


import com.gstsgy.base.model.ResponseBean;
import com.gstsgy.permission.service.MenuService;
import com.gstsgy.permission.bean.db.MenuDO;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName MenuController
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/19 下午4:16
 **/

@RestController
@Tag(name = "菜单")
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @Operation(description ="新增一个菜单")
    @PostMapping("menu")
    public ResponseBean addMenu(@RequestBody MenuDO menuDO) {
        return menuService.addMenu(menuDO);
    }

    @Operation(description ="删除菜单")
    @DeleteMapping("menus")
    public ResponseBean deleteMenus(@RequestBody List<MenuDO> menuDOS) {
        menuService.deleteMenu(menuDOS);
        return  ResponseBean.getSuccess(true);
    }

    @Operation(description ="查询菜单")
    @GetMapping("menus")
    public ResponseBean queryMenu(MenuDO menuDO, Integer pageSize,Integer pageNum) {

        return menuService.queryMenu( menuDO,  pageSize, pageNum);
    }

    @Operation(description ="查询菜单模块枚举")
    @GetMapping("modelenum")
    public ResponseBean queryMenuModel(Long parentId) {
        return menuService.queryMenuModel(parentId);
    }

    @Operation(description ="修改一个菜单")
    @PutMapping("menu")
    public ResponseBean updateMenu(@RequestBody MenuDO menuDO) {
        return menuService.updateMenu(menuDO);
    }
}
