package com.gstsgy.permission.bean.view;

import com.baomidou.mybatisplus.annotation.TableField;
import com.gstsgy.permission.bean.db.OperatorDO;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName OperatorVO
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/16 下午1:58
 **/
@Data
@Schema
@EqualsAndHashCode(callSuper=true)
public class OperatorVO extends OperatorDO {
    @Schema(description = "部门名称")
    @TableField("dept.name")
    private String deptName;

    @Override
    public String toString() {
        return super.toString()+"OperatorVO{" +
                "deptName='" + deptName + '\'' +
                '}';
    }
}
