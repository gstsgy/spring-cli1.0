package com.gstsgy.permission.bean.view;

import lombok.Data;

/**
 * @Classname WebEnum
 * @Description TODO
 * @Date 2020/12/25 上午11:06
 * @Created by guyue
 */
@Data
public class WebEnumVO {
    private Object value;  //

    private String label;

    public WebEnumVO(){

    }

    public WebEnumVO(Object value, String label) {
        this.value = value;
        this.label = label;
    }
}
