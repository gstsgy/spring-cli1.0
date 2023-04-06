package com.gstsgy.permission.service.impl;

import com.gstsgy.permission.service.MenuService;
import com.gstsgy.permission.service.RoleMenuService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gstsgy.base.model.ResponseBean;
import com.gstsgy.permission.bean.db.MenuDO;
import com.gstsgy.permission.bean.db.RoleMenuDO;
import com.gstsgy.permission.bean.db.UserRoleDO;
import com.gstsgy.permission.bean.view.MenuTreeVO;
import com.gstsgy.permission.bean.view.TreeNodeVO;
import com.gstsgy.permission.mapper.MenuMapper;
import com.gstsgy.permission.mapper.RoleMenuMapper;
import com.gstsgy.permission.mapper.UserRoleMapper;
import com.gstsgy.permission.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @ClassName RoleMenuServiceImpl
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/23 上午10:42
 **/
@Service
@Transactional
public class RoleMenuServiceImpl implements RoleMenuService {
    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public ResponseBean getAllMenuTree(Integer type) {

        List<TreeNodeVO> tree = null;
        long userId = JWTUtil.getUser();


        if (userId == 1) {
            //查询模块
            tree = menuService.queryChildren(null, type).stream().
                    map(item -> new TreeNodeVO(1, null, item.getName(), item.getId() + "")).
                    collect(Collectors.toList());
            // 二级菜单
            tree.forEach(item -> item.setChildren(menuService.queryChildren(Long.parseLong(item.getValue()), null).
                    stream().map(it -> new TreeNodeVO(2, item.getValue(), it.getName(), it.getId() + "")).
                    collect(Collectors.toList())));
            // 三级菜单
            //systemParamService.
            tree.forEach(item -> item.getChildren().forEach(it ->
                    it.setChildren(menuService.queryChildren(Long.parseLong(it.getValue()), null).stream().
                            map(i -> new TreeNodeVO(3, it.getValue(), i.getName(), i.getId() + "")).
                            collect(Collectors.toList()))));
        } else {
            // 查询所有菜单并缓存
           /* QueryWrapper<MenuDO> mquery = new QueryWrapper<>();
            mquery.lambda().eq(MenuDO::getType, type);

            List<MenuDO> allMenus = menuMapper.selectList(mquery);

            Map<Long, MenuDO> mapMenus = allMenus.stream().collect(Collectors.toMap(MenuDO::getId, it -> it));*/


            // 1  通过用户 查询角色
            QueryWrapper<UserRoleDO> query = new QueryWrapper<>();
            query.lambda().eq(UserRoleDO::getUserId, userId);
            // 所有角色
            List<UserRoleDO> list = userRoleMapper.selectList(query);

            // 三级菜单
            List<TreeNodeVO> threeTree = list.stream().flatMap(it -> roleMenuMapper.selectMenuIdByRole(it.getRoleId(), type).stream()).

                    map(i -> new TreeNodeVO(3, i.getParentId() + "", i.getName(), i.getId() + "")).distinct().
                    collect(Collectors.toList());

            Map<String, List<TreeNodeVO>> threeTreeMap = threeTree.stream().collect(Collectors.groupingBy(TreeNodeVO::getParentVal));

            // 二级菜单
            List<TreeNodeVO> twoTree = threeTreeMap.keySet().stream().map(it -> menuMapper.selectById(it)).
                    map(i -> {
                                TreeNodeVO treeNodeVO = new TreeNodeVO(2, i.getParentId() + "", i.getName(), i.getId() + "");
                                treeNodeVO.setChildren(threeTreeMap.get(i.getId() + ""));
                                return treeNodeVO;
                            }
                    ).distinct().
                    collect(Collectors.toList());


            Map<String, List<TreeNodeVO>> twoTreeMap = twoTree.stream().collect(Collectors.groupingBy(TreeNodeVO::getParentVal));

            // 一级菜单
            tree = twoTreeMap.keySet().stream().map(it -> menuMapper.selectById(it)).

                    map(i -> {
                                TreeNodeVO treeNodeVO = new TreeNodeVO(1, null, i.getName(), i.getId() + "");
                                treeNodeVO.setChildren(twoTreeMap.get(i.getId() + ""));
                                return treeNodeVO;
                            }
                    ).distinct().
                    collect(Collectors.toList());
        }

        return ResponseBean.getSuccess(tree);
    }

    @Override
    public ResponseBean getAllMenuByRole(Long roleId, Integer type) {


        List<MenuDO> list = roleMenuMapper.selectMenuIdByRole(roleId, type);
        return ResponseBean.getSuccess(list);
    }

    @Override
    public ResponseBean saveMenu(Long roleId, List<Long> menus) {


        QueryWrapper<RoleMenuDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(RoleMenuDO::getRoleId, roleId);
        // 删除旧的
        List<RoleMenuDO> listOld = roleMenuMapper.selectList(queryWrapper);
        listOld.forEach(item -> {
            if (!menus.contains(item.getMenuId())) {

                roleMenuMapper.deleteById(item.getId());
            }
        });
        List<Long> listOldMenu = listOld.stream().map(RoleMenuDO::getMenuId).collect(Collectors.toList());

        // 新增新的
        menus.forEach(item -> {
            if (!listOldMenu.contains(item)) {

                RoleMenuDO roleMenuDO = new RoleMenuDO();
                MenuDO menuDO = menuMapper.selectById(item);
                if (menuDO.getLevel().equals("3")) {
                    roleMenuDO.setRoleId(roleId);
                    roleMenuDO.setMenuId(item);
                    roleMenuMapper.insert(roleMenuDO);
                }

            }
        });
        return ResponseBean.getSuccess(true);
    }


    @Override
    public ResponseBean getAllMenuTree(Integer type, Long userId) {
        if (userId == 1) {
            //查询模块
            List<MenuTreeVO> tree = menuService.queryChildren(null, type).stream().sorted(Comparator.comparing(MenuDO::getSeq)).
                    map(MenuTreeVO::new).
                    collect(Collectors.toList());
            // 二级菜单
            tree.forEach(item -> item.setChildren(menuService.queryChildren(item.getMenuId(), null).
                    stream().sorted(Comparator.comparing(MenuDO::getSeq)).map(MenuTreeVO::new).peek(it -> it.setParentLabel(item.getMenuLabel())).
                    collect(Collectors.toList())));
            return ResponseBean.getSuccess(tree);
        }
        // 查询所有菜单并缓存
        QueryWrapper<MenuDO> mquery = new QueryWrapper<>();
        mquery.lambda().eq(MenuDO::getType, type);

        List<MenuDO> allMenus = menuMapper.selectList(mquery);

        Map<Long, MenuDO> mapMenus = allMenus.stream().collect(Collectors.toMap(MenuDO::getId, it -> it));


        // 1  通过用户 查询角色
        QueryWrapper<UserRoleDO> query = new QueryWrapper<>();
        query.lambda().eq(UserRoleDO::getUserId, userId);
        List<UserRoleDO> list = userRoleMapper.selectList(query);


        Map<Long, List<MenuTreeVO>> map = list.stream().flatMap(item -> roleMenuMapper.
                selectMenuIdByRole(item.getRoleId(), type).stream()).
                map(MenuDO::getParentId).distinct().map(mapMenus::get).filter(Objects::nonNull).
                map(MenuTreeVO::new).collect(Collectors.groupingBy(MenuTreeVO::getParentid));


        List<MenuTreeVO> menuIdlist = map.keySet().stream().filter(Objects::nonNull).map(mapMenus::get).filter(Objects::nonNull).
                sorted(Comparator.comparingInt(MenuDO::getSeq)).
                map(MenuTreeVO::new).
                peek(item -> item.setChildren(map.get(item.getMenuId()).stream().sorted(Comparator.comparingInt(MenuTreeVO::getDisplayorder)).peek(it -> it.setParentLabel(item.getMenuLabel())).collect(Collectors.toList()))).collect(Collectors.toList());
        return ResponseBean.getSuccess(menuIdlist);
    }

    @Override
    public ResponseBean getAllFuns(Integer type, Long userId) {
        QueryWrapper<MenuDO> pageQuery = new QueryWrapper<>();
        pageQuery.lambda().eq(MenuDO::getLevel, 2).eq(MenuDO::getType, type);
        Map<Long, MenuDO> pages = menuMapper.selectList(pageQuery).stream().
                collect(Collectors.toMap(MenuDO::getId, it -> it));

        if (userId == 1) {
            //查询所有三级菜单
            QueryWrapper<MenuDO> query = new QueryWrapper<>();
            query.lambda().eq(MenuDO::getLevel, 3).eq(MenuDO::getType, type);
            List<MenuDO> list = menuMapper.selectList(query);

            return ResponseBean.getSuccess(list.stream().collect(Collectors.toMap(it -> getLastPath(pages.get(it.getParentId()).getPath()) + "_" + getLastPath(it.getPath()), obj -> true)));
        }
        // 1  通过用户 查询角色
        QueryWrapper<UserRoleDO> query = new QueryWrapper<>();
        query.lambda().eq(UserRoleDO::getUserId, userId);
        List<UserRoleDO> list = userRoleMapper.selectList(query);

        Map map = list.stream().flatMap(item -> roleMenuMapper.
                selectMenuIdByRole(item.getRoleId(), type).stream()).
                distinct().filter(Objects::nonNull).
                collect(Collectors.toMap(it -> getLastPath(pages.get(it.getParentId()).getPath()) + "_" + getLastPath(it.getPath()), obj -> true));

        return ResponseBean.getSuccess(map);

    }


    private String getLastPath(String str) {
        if (Objects.isNull(str)) return "fun";
        return str.substring(str.lastIndexOf('/') + 1);
    }
}
