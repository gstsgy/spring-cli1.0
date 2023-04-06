package com.gstsgy.permission.controller;

import com.gstsgy.base.model.ResponseBean;
import com.gstsgy.permission.service.RoleDataService;
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
@Tag(name = "数据权限相关")
@RequestMapping("/roledata")
public class RoleDataController {
    @Autowired
    private RoleDataService roleDataService;

    @GetMapping("ware")
    @Operation(description ="获取该角色所有的仓库")
    public ResponseBean queryWare(@Parameter(description  = "角色id", required = true)Long roleId) {
        return roleDataService.getAllWarehouseByRole(roleId);
    }

    @PostMapping("data")
    @Operation(description ="保存角色数据信息")
    public ResponseBean saveData(@Parameter(description  = "角色id 通过param传", required = true)Long roleId,
                                 @Parameter(description  = "仓库 ID 通过data传", required = true)
                                 @RequestBody List<String> wares) {
        return roleDataService.saveData(roleId,wares);
    }

}
