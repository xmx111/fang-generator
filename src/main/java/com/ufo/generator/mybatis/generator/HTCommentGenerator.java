package com.ufo.generator.mybatis.generator;

import com.ufo.generator.util.StringUtil;
import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.Date;
import java.util.Properties;

/***
 * 自定义生成注释
 *
 * @author hekang
 * @created 2016/1/18
 */
public class HTCommentGenerator implements CommentGenerator {

    public HTCommentGenerator(){

    }

    @Override
    public void addConfigurationProperties(Properties properties) {

    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        // 属性注释
        if (introspectedColumn.getRemarks() != null && !introspectedColumn.getRemarks().equals("")) {
            field.addJavaDocLine("/**");
            field.addJavaDocLine(" * " + introspectedColumn.getRemarks());
            field.addJavaDocLine(" */");
        }
//        // 日期字段加转换注解
//        if (introspectedColumn.getFullyQualifiedJavaType().getFullyQualifiedName().equals("java.util.Date")){
//            field.addAnnotation("@org.springframework.format.annotation.DateTimeFormat(pattern = \"yyyy-MM-dd\")");
//        }
    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {

    }

    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean b) {
        innerClass.addJavaDocLine("/**");
        innerClass.addJavaDocLine(" * " + introspectedTable.getFullyQualifiedTable());
        innerClass.addJavaDocLine(" * @author mybatis generator");
        innerClass.addJavaDocLine(" * @created " + new Date().toString());
        innerClass.addJavaDocLine(" */");
    }

    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {

    }

    @Override
    public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable) {

    }

    @Override
    public void addGetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
//        if (method.getReturnType().getFullyQualifiedName().equals("java.util.Date")){
//            String projectName = StringUtil.substringBetween(introspectedTable.getContext().getJavaModelGeneratorConfiguration().getTargetPackage(), "com.ufo.", ".");
//            method.addAnnotation("@com.fasterxml.jackson.databind.annotation.JsonSerialize(using = com.ufo." + projectName + ".common.model.jackson.SimpleDateSerializer.class)");
//        }
    }

    @Override
    public void addSetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {

    }

    @Override
    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {

    }

    @Override
    public void addJavaFileComment(CompilationUnit compilationUnit) {
    }

    @Override
    public void addComment(XmlElement xmlElement) {

    }

    @Override
    public void addRootComment(XmlElement rootElement) {

    }
}
