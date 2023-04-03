package com.gstsgy.export.bean;

import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author guyue
 * @version 3.0
 * @description: TODO
 * @date 2022/1/8 下午2:12
 */

public class ExportTemplate {
   // @Schema(description = "url")
    private String url;

   // @Schema(description = "源接口方法")
    private RequestMethod method;

   // @Schema(description = "模板内容")
    private List<WebColumn> columns;

    //@Schema(description = "参数")
    private Map<String,Object> params;

 //   @Schema(description = "参数")
    private List<Object> data;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public RequestMethod getMethod() {
        return method;
    }

    public void setMethod(RequestMethod method) {
        this.method = method;
    }

    public List<WebColumn> getColumns() {
        return columns;
    }

    public void setColumns(List<WebColumn> columns) {
        this.columns = columns;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExportTemplate that = (ExportTemplate) o;
        return Objects.equals(url, that.url) && method == that.method && Objects.equals(columns, that.columns) && Objects.equals(params, that.params) && Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, method, columns, params, data);
    }

    @Override
    public String toString() {
        return "ExportTemplate{" +
                "url='" + url + '\'' +
                ", method=" + method +
                ", columns=" + columns +
                ", params=" + params +
                ", data=" + data +
                '}';
    }
}
