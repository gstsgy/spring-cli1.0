package com.gstsgy.base.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

import java.io.Serializable;
@Data
public abstract class BaseTable implements Serializable {
    @TableField(fill = FieldFill.INSERT)
    private String insertYmd;//
    @TableField(fill = FieldFill.INSERT)
    private Long insertId;//
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateYmd;//
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateId;

    private String remark;//备用字段

    private String remark1;//备用字段

    private String remark2;//备用字段

    private String remark3;//备用字段

    private String remark4;//备用字段

    private String remark5;//备用字段
    @TableField(fill = FieldFill.INSERT)
    private Boolean effective;//
    @TableField(fill = FieldFill.INSERT)
    @Version
    private Integer updateFlag;//

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
