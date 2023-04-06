package com.gstsgy.permission.service;


import com.gstsgy.base.model.ResponseBean;
import com.gstsgy.permission.bean.db.MenuDO;

import java.util.List;

/**
 * @ClassName MenuService
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/19 下午4:16
 **/
public interface MenuService {
    ResponseBean addMenu(MenuDO menuDO);

    void deleteMenu(List<MenuDO> menuDO);

    ResponseBean queryMenu(MenuDO menuDO, Integer pageSize,Integer pageNum);

    ResponseBean queryMenuModel(Long parentId);

    ResponseBean updateMenu(MenuDO menuDO);

    List<MenuDO> queryChildren(Long parentId,Integer type);
}
