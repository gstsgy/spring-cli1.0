package com.gstsgy.base.model;

import lombok.Data;

import java.io.Serializable;
@Data
public abstract class BaseTable implements Serializable {

    private String insertYmd;//

    private Long insertId;//

    private String updateYmd;//

    private Long updateId;

    private String remark;//备用字段

    private String remark1;//备用字段

    private String remark2;//备用字段

    private String remark3;//备用字段

    private String remark4;//备用字段

    private String remark5;//备用字段

    private Boolean effective;//

    private Integer updateFlag;//

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
