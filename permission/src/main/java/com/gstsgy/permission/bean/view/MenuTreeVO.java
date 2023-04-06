package com.gstsgy.permission.bean.view;


import com.gstsgy.permission.bean.db.MenuDO;
import lombok.Data;

import java.util.List;

/**
 * @ClassName MenuTreeVO
 * @Description TODO 菜单结构
 * @Author guyue
 * @Date 2020/10/26 下午3:23
 **/
@Data
public class MenuTreeVO {
    // 默认两级菜单   模块->页面
    // 主页 -模块-页面
    private Long menuId;   //菜单id
    private String menuAction;   //菜单路径
    private String menuLabel;   //菜单标题
    private String relativePath;   //菜单实体路径（动态路由）
    private String isLeaf;   //是否叶子菜单
    private String menuView;   //模块图标
    private String parentLabel;//父级标题
    private Long parentid;
    private Integer displayorder;
    private List<MenuTreeVO> children;

    public MenuTreeVO(MenuDO menuDO) {
        this.menuId = menuDO.getId();
        this.menuAction = menuDO.getPath();
        this.menuLabel = menuDO.getName();
        this.relativePath = menuDO.getPosi();
        this.isLeaf = menuDO.getLevel().equals("2")?"1":"0";
        this.menuView=menuDO.getIco();
        this.parentid = menuDO.getParentId();
        this.displayorder = menuDO.getSeq();
    }

    public MenuTreeVO(){}
}
