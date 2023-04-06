package com.gstsgy.permission.service;


import com.gstsgy.base.model.ResponseBean;

import java.util.List;

/**
 * @ClassName RoleMenuService
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/23 上午10:41
 **/
public interface RoleMenuService {

    ResponseBean getAllMenuTree(Integer type);

    ResponseBean getAllMenuByRole(Long roleId,Integer type);

    ResponseBean saveMenu(Long roleId, List<Long> menus);

    ResponseBean getAllMenuTree(Integer type,Long userId);

    ResponseBean getAllFuns(Integer type,Long userId);
}
