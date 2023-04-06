package com.gstsgy.permission.bean.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gstsgy.base.model.BaseTable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName MenuDO
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/14 下午1:16
 **/
@EqualsAndHashCode(callSuper = true)
@Schema(description = "菜单")
@TableName(value = "menu")
@Data
public class MenuDO extends BaseTable {
    @TableId(type = IdType.NONE)
    private Long id;
    @Schema(description = "名称")
    private String name;
    @Schema(description = "菜单级别")
    private String level;
    @Schema(description = "父菜单id")
    private Long parentId;
    @Schema(description = "菜单图标")
    private String ico;
    @Schema(description = "菜单路由")
    private String path;
    @Schema(description = "菜单位置")
    private String posi;
    @Schema(description = "菜单类型",required = true)
    private Integer type;
    @Schema(description = "菜单顺序",required = true)
    private Integer seq;

}
