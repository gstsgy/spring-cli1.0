package com.gstsgy.permission.service.impl;

import com.gstsgy.permission.bean.view.WebEnumVO;
import com.gstsgy.permission.service.MenuService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gstsgy.base.model.ResponseBean;
import com.gstsgy.permission.bean.db.MenuDO;
import com.gstsgy.permission.bean.db.RoleMenuDO;

import com.gstsgy.permission.mapper.MenuMapper;
import com.gstsgy.permission.mapper.RoleMenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName MenuServiceImpl
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/19 下午4:17
 **/
@Service
@Transactional
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public ResponseBean addMenu(MenuDO menuDO) {

       boolean result =   menuMapper.insert(menuDO)>0;
        if(menuDO.getLevel().equals("2")){

            if(result){
                MenuDO menuDO1 = new MenuDO();
                menuDO1.setName(menuDO.getName()+"-查询");
                menuDO1.setLevel("3");
                menuDO1.setParentId(menuDO.getId());
                menuDO1.setType(menuDO.getType());
                menuMapper.insert(menuDO1);
            }
        }

        return ResponseBean.getSuccess(result);
    }

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Override
    public void deleteMenu(List<MenuDO> menuDOs) {
        //System.out.println("----------"+menuDOs);
        if (menuDOs != null && menuDOs.size() > 0) {
            menuDOs.forEach(item -> {
                QueryWrapper<MenuDO> queryWrapper = new QueryWrapper<>();
                queryWrapper.lambda().eq(MenuDO::getId, item.getId());
                MenuDO menuDO1 = menuMapper.selectOne(queryWrapper);
                if (menuDO1 != null) {

                    menuMapper.deleteById(menuDO1.getId());

                    //删除菜单角色
                    QueryWrapper<RoleMenuDO> queryWrapper2 = new QueryWrapper<>();
                    queryWrapper2.lambda().eq(RoleMenuDO::getMenuId, menuDO1.getId());
                    List<RoleMenuDO> roleMenuDOS = roleMenuMapper.selectList(queryWrapper2);
                    roleMenuDOS.forEach(it -> {
                        roleMenuMapper.deleteById(it.getId());
                    });


                    //删除下级菜单
                    QueryWrapper<MenuDO> queryWrapper1 = new QueryWrapper<>();
                    queryWrapper1.lambda().eq(MenuDO::getParentId, item.getId());
                    List<MenuDO> menuDOS = menuMapper.selectList(queryWrapper1);
                    if (menuDOS != null && menuDOS.size() > 0) {
                        deleteMenu(menuDOS);
                    }
                }
            });
        }
        //return;
    }

    @Override
    public ResponseBean queryMenu(MenuDO menuDO, Integer pageSize, Integer pageNum) {
        LambdaQueryWrapper<MenuDO> queryWrapper = new QueryWrapper<MenuDO>().lambda();
        if (menuDO != null) {
            if (menuDO.getParentId() != null) {
                queryWrapper.eq(MenuDO::getParentId, menuDO.getParentId());
            }
            if (menuDO.getLevel() != null) {
                queryWrapper.eq(MenuDO::getLevel, menuDO.getLevel());
            }
            if (menuDO.getName() != null) {
                queryWrapper.like(MenuDO::getName, menuDO.getName());
            }
            if (menuDO.getType() != null) {
                queryWrapper.eq(MenuDO::getType, menuDO.getType());
            }
        }
        IPage<MenuDO> page = new Page<>(pageNum, pageSize);
        page =menuMapper.selectPage(page, queryWrapper);

        return ResponseBean.getSuccess(page);
    }

    @Override
    public ResponseBean queryMenuModel(Long parentId) {
        LambdaQueryWrapper<MenuDO> queryWrapper = new QueryWrapper<MenuDO>().lambda();
        if(parentId==null){
            queryWrapper.isNull(MenuDO::getParentId);
        }else{
            queryWrapper.eq(MenuDO::getParentId,parentId);
        }

        List<MenuDO> list = menuMapper.selectList(queryWrapper);
        List<WebEnumVO> result = list.stream().map(it->new WebEnumVO(it.getId(),it.getName())).collect(Collectors.toList());
        return ResponseBean.getSuccess(result);
    }

    @Override
    public ResponseBean updateMenu(MenuDO menuDO) {
        System.out.println(menuDO);
        QueryWrapper<MenuDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MenuDO::getId, menuDO.getId());
        MenuDO menuDO1 = menuMapper.selectOne(queryWrapper);
        if (menuDO1 == null) {
            return ResponseBean.getParamUnmatchedException("菜单不存在");
        }
        return ResponseBean.getSuccess(menuMapper.updateById(menuDO) > 0);
    }

    @Override
    public List<MenuDO> queryChildren(Long parentId,Integer type) {
        QueryWrapper<MenuDO> queryWrapper = new QueryWrapper<>();
        if(parentId==null){
            queryWrapper.lambda().isNull(MenuDO::getParentId);
        }else{
            queryWrapper.lambda().eq(MenuDO::getParentId,parentId);
        }
        if(type!=null){
            queryWrapper.lambda().eq(MenuDO::getType,type);
        }

        return menuMapper.selectList(queryWrapper);
    }
}
