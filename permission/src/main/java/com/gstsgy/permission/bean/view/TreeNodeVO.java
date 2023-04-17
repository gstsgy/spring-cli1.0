package com.gstsgy.permission.bean.view;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;

import java.util.List;
import java.util.function.Function;

/**
 * @ClassName DeptTre
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/19 上午10:47
 **/
@Data
@Schema(description = "部门树结构")
public class TreeNodeVO {

    private Long id;
    private String label;
    private List<TreeNodeVO> children; //      子节点属性数组


    public TreeNodeVO(Long id,  String label) {
        this.label = label;
        this.id = id;
    }

    public TreeNodeVO(){}
}
