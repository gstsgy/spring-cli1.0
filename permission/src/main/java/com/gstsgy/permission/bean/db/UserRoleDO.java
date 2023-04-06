package com.gstsgy.permission.bean.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gstsgy.base.model.BaseTable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName UserRoleDO
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/14 下午3:11
 **/
@EqualsAndHashCode(callSuper = true)
@Schema(description = "用户角色")
@TableName(value = "user_role")
@Data
public class UserRoleDO extends BaseTable {
    @TableId(type = IdType.NONE)
    private Long id;

    private Long userId;

    private Long roleId;

}
