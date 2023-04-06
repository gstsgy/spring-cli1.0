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
@Schema(description = "角色数据")
@TableName(value = "role_data")
@Data
public class RoleDataDO extends BaseTable {
    @TableId(type = IdType.NONE)
    private Long id;

    private String warehouseId;

    private Long roleId;
}
