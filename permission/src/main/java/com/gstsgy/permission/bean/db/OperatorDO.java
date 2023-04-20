package com.gstsgy.permission.bean.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gstsgy.base.model.BaseTable;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName OperatorDO
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/14 下午1:05
 **/
@EqualsAndHashCode(callSuper = true)
@Schema(description = "用户")
@Data
@TableName("operator")
public class OperatorDO extends BaseTable implements Cloneable {
    @TableId(type = IdType.NONE)
    private Long id;
    @Schema(description = "昵称",required = true)
    private String nickName;
    @Schema(description = "密码")
    private String passwd;
    @Schema(description = "邮箱")
    private String email;
    @Schema(description = "生日")
    private String birthday;
    @Schema(description = "性别",example = "1",required = true)
    private Integer gender;
    @Schema(description = "职位")
    private String position;
    @Schema(description = "部门",example = "1")
    private Long deptId;
    @Schema(description = "用户名",required = true)
    private String code;
    @Schema(description = "头像")
    private String avatarImg;
    @Schema(description = "密码修改时间")
    private String passwdUpdateYmd;

    public OperatorDO clone(){
        try {
            return (OperatorDO) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
