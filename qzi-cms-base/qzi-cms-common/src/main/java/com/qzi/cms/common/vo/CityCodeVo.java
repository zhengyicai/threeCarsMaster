package com.qzi.cms.common.vo;

import java.util.List;

public class CityCodeVo {
    private String value;
    private String text;
    private List<CityCodeVo> children;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<CityCodeVo> getChildren() {
        return children;
    }

    public void setChildren(List<CityCodeVo> children) {
        this.children = children;
    }
}
