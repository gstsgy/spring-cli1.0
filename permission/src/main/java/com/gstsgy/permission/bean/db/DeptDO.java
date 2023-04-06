package com.gstsgy.permission.bean.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gstsgy.base.model.BaseTable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName DeptDO
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/14 上午9:37
 **/
@EqualsAndHashCode(callSuper = true)
@Schema(description = "部门")
@TableName(value = "dept")
@Data
public class DeptDO extends BaseTable {
    @TableId(type = IdType.NONE)
    private Long id;

    private String name;

    private Long parentId;

}
