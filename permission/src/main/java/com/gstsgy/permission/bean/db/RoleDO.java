package com.gstsgy.permission.bean.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gstsgy.base.model.BaseTable;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName RoleDO
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/14 下午1:11
 **/
@EqualsAndHashCode(callSuper = true)
@Schema(description = "角色")
@TableName(value = "role")
@Data
public class RoleDO extends BaseTable {
    @TableId(type = IdType.NONE)
    private Long id;
    @Schema(description = "名称")
    private String name;
    @Schema(description = "描述")
    private String desc;

}
