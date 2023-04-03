package com.gstsgy.base.model;

import com.gstsgy.base.utils.LangUtils;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public class ResponseBean implements Serializable {
    private int code;

    private String message;

    private Object data;

    private ResponseBean() {
    }

    private ResponseBean(HttpStatus code, String message, Object data) {
        this.code = code.value();
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }


    private void setValue(HttpStatus code, String message, Object data) {
        this.code = code.value();
        this.message = message;
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result [code=" + code + ", message=" + message + ", data=" + data.toString() + "]";
    }

    private static Queue<ResponseBean> catchData = new ConcurrentLinkedDeque<>();


    public static ResponseBean getSuccess(Object data) {
        return getInstance(HttpStatus.OK, "成功", data);

    }

    public static ResponseBean getError(String errorMessage) {
        return getInstance(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage, null);
    }

    public static ResponseBean getParamEmptyException(String errorMessage) {
        return getInstance(HttpStatus.PRECONDITION_REQUIRED, "请求条件缺失："+errorMessage, null);
    }

    public static ResponseBean getParamUnmatchedException(String errorMessage) {
        return getInstance(HttpStatus.PRECONDITION_FAILED, "请求条件错误:"+errorMessage, null);
    }

    public static ResponseBean getError(String errorMessage, Object... args) {
        errorMessage = String.format(errorMessage, args);
        return getInstance(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage, null);
    }
    public static ResponseBean getNoLogin(String errorMessage) {
        return getInstance(HttpStatus.UNAUTHORIZED, errorMessage, null);
    }

    public static ResponseBean getInstance(HttpStatus code, String errorMessage, Object data) {
        if (catchData.size() < 256) {
            ResponseBean responseBean = new ResponseBean(code, errorMessage, data);
            catchData.offer(responseBean);
            return responseBean;
        }
        ResponseBean responseBean = catchData.poll();
        responseBean.setValue(code, errorMessage, data);
        catchData.offer(responseBean);
        return responseBean;
    }

    public static ResponseBean getError(LangCode langCode) {
        return getInstance(HttpStatus.INTERNAL_SERVER_ERROR, LangUtils.getMessage(langCode.getLangCode()), null);
    }

    public static ResponseBean getParamEmptyException(LangCode langCode) {
        return getInstance(HttpStatus.PRECONDITION_REQUIRED, LangUtils.getMessage("common.except428", LangUtils.getMessage(langCode.getLangCode())), null);
    }

    public static ResponseBean getParamUnmatchedException(LangCode langCode) {
        return getInstance(HttpStatus.PRECONDITION_FAILED, LangUtils.getMessage("common.except412", LangUtils.getMessage(langCode.getLangCode())), null);
    }

    public static ResponseBean getParamUnmatchedException(LangCode langCode, LangCode... args) {

        return getInstance(HttpStatus.PRECONDITION_FAILED, LangUtils.getMessage("common.except412",
                LangUtils.getMessage(langCode.getLangCode(), Arrays.stream(args).map(LangCode::getLangCode).map(LangUtils::getMessage).toArray())),
                null);
    }
    public static ResponseBean getParamUnmatchedException(LangCode langCode, String... args) {

        return getInstance(HttpStatus.PRECONDITION_FAILED, LangUtils.getMessage("common.except412",
                LangUtils.getMessage(langCode.getLangCode(), Arrays.stream(args).toArray())),
                null);
    }

    public static ResponseBean getError(LangCode langCode, Object... args) {
        return getInstance(HttpStatus.INTERNAL_SERVER_ERROR, LangUtils.getMessage(langCode.getLangCode(), args), null);
    }
    public static ResponseBean getError(LangCode langCode, LangCode... args) {

        return getInstance(HttpStatus.INTERNAL_SERVER_ERROR,
                LangUtils.getMessage(langCode.getLangCode(),
                        Arrays.stream(args).map(LangCode::getLangCode).map(LangUtils::getMessage).toArray()), null);
    }
}

