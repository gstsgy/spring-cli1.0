package com.gstsgy.sequence.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import com.gstsgy.base.model.BaseTable;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@TableName("g_sequence")
public class Sequence extends BaseTable {
    @TableId
    private Long id;

    private String gKey; //设置联合索引

    private String subkey;//设置联合索引

    @Builder.Default
    private Long startnum = 1L;
    @Builder.Default
    private Integer step = 1;
    @Builder.Default
    private Long maxnum  = 999999999L;
    @Builder.Default
    private boolean dayresetting = false;

    private String lastday;

    private Long value;
}
