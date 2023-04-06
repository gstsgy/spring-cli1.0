package com.gstsgy.permission.bean.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gstsgy.base.model.BaseTable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName RoleMenuDO
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/14 下午3:14
 **/
@EqualsAndHashCode(callSuper = true)
@Schema(description = "角色菜单")
@TableName(value = "role_menu")
@Data
public class RoleMenuDO extends BaseTable {
    @TableId(type = IdType.NONE)
    private Long id;

    private Long menuId;

    private Long roleId;
}
