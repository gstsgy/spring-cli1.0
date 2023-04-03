package com.gstsgy.export.bean;

import java.util.Objects;

public class WebColumn {
    // 字段名
    private String key;
    // 顺序
    private int seq;
    // 是否显示 true才导出
    private boolean show;
    // column列名
    private String title;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WebColumn webColumn = (WebColumn) o;
        return seq == webColumn.seq && show == webColumn.show && Objects.equals(key, webColumn.key) && Objects.equals(title, webColumn.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, seq, show, title);
    }

    @Override
    public String toString() {
        return "WebColumn{" +
                "key='" + key + '\'' +
                ", seq=" + seq +
                ", show=" + show +
                ", title='" + title + '\'' +
                '}';
    }
}
