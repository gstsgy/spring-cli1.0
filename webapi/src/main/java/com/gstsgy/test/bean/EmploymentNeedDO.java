package com.gstsgy.test.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gstsgy.base.model.BaseTable;
import lombok.Data;
import lombok.EqualsAndHashCode;
@TableName("employment_need")
@EqualsAndHashCode(callSuper = true)
@Data
public class EmploymentNeedDO extends BaseTable {
    @TableId
    private Long id;
}
