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

    private int level;
    @Schema(description = "父级")
    private String parentVal; // 父级权限
    @Schema(description = "标题")
    private String title; //   标题
    @Schema(description = "值")
    private String value; //   标题
    private Boolean expand; //      是否展开直子节点
    // private Boolean disabled; //  禁掉响应
    private Boolean disableCheckbox; // 	禁掉 checkbox
    private Boolean selected; //  是否选中子节点
    private Boolean checked; //  是否勾选(如果勾选，子节点也会全部勾选)
    private Boolean contextmenu ; //  是否支持鼠标右键
    private List<TreeNodeVO> children; //      子节点属性数组
    private Function render; //  自定义当前节点渲染内容，见示例
    private Boolean file; //是否文件

    public TreeNodeVO(int level, String parentVal, String title, String value) {
        this.level = level;
        this.parentVal = parentVal;
        this.title = title;
        this.value = value;
    }

    public TreeNodeVO(){}
}
