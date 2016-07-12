package com.ufo.generator.builder;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 用于代码生成处理的辅助对象
 */
public class ModelCodeField implements Comparable<ModelCodeField> {
    /** 属性标题 */
    private String title;
    /** Java属性名称 */
    private String fieldName;
    /** 属性描述 */
    private String description;
    /** 属性在列表Grid中定义的宽度 */
    private Integer listWidth = 200;
    /** 在生成代码中属性的相对顺序 */
    private Integer order = Integer.MAX_VALUE;
    /** 属性在列表Grid中定义的对齐方式：left，right，center */
    private String listAlign = "left";
    /** 属性在编辑界面生成表单元素 */
    private boolean edit = true;
    /** 属性在Grid列表中生成column定义 */
    private boolean list = true;
    /** 属性类型，根据Java属性反射获取 */
    private String fieldType = "String";
    /** 属性类型，根据Java属性反射获取 */
    private Integer fieldLength = 0;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public int compareTo(ModelCodeField o) {
        return order.compareTo(o.getOrder());
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public String getListAlign() {
        return listAlign;
    }

    public void setListAlign(String listAlign) {
        this.listAlign = listAlign;
    }

    public Integer getListWidth() {
        return listWidth;
    }

    public void setListWidth(Integer listWidth) {
        this.listWidth = listWidth;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getUncapitalizeFieldType() {
        return StringUtils.uncapitalize(fieldType);
    }

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    public boolean getList() {
        return list;
    }

    public void setList(boolean list) {
        this.list = list;
    }

    public Integer getFieldLength() {
        return fieldLength;
    }

    public void setFieldLength(Integer fieldLength) {
        this.fieldLength = fieldLength;
    }
}
