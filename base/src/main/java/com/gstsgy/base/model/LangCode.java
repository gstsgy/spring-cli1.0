package com.gstsgy.base.model;


/**
 * 多语言的code
 */
public class LangCode {
    private String langCode;

    public String getLangCode() {
        return langCode;
    }

    public LangCode(String langCode) {
        this.langCode = langCode;
    }

    public static LangCode of(String langCode){
        return new LangCode(langCode);
    }

    @Override
    public String toString() {
        return langCode;
    }


}
