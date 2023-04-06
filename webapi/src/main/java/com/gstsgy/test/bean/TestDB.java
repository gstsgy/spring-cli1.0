package com.gstsgy.test.bean;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "测试")
public class TestDB {
    @Schema(description = "测试")
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
