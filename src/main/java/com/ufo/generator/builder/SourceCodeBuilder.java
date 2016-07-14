package com.ufo.generator.builder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ufo.generator.util.StringUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.lang.CharUtils;
import org.apache.logging.log4j.core.appender.rolling.action.Action;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;

/***
 * 基本CRUD框架代码生成工具类，Maven方式运行工程的pom.xml调用生成代码
 * 配置文件在：generator/model_list.properties
 * 生成的代码在：generator/codes目录下，用于重复生成拷贝某一个Model相关代码之用
 * 注意：生成的代码都在当前tools工程中，需自行根据需要拷贝所需相关代码到对应的业务工程目录或Package下，然后在业务工程中基于框架代码添加相关业务逻辑代码
 *
 * 模板文件位置：\src\main\resources\builder\freemarker
 *
 * @author hekang
 * @created 2016/1/18
 */
public class SourceCodeBuilder {

    public static String ENCODING = "UTF-8";

    public static String TEMPLATE_PATH = "/builder/freemarker";

    public static String TARGET_PATH = "." + File.separator + "target" + File.separator + "generated-codes" + File.separator;

    /**
     * 生成(fm,js,action,service,dao)
     * @param targetSrcPath     为了避免生成覆盖已改动的代码，默认target/generated-code，可以设置为src
     * @param genFilesType      生成哪几类文件(fm,js,action,service,dao)
     * @param genFilesModal     生成的文件模式：extend继承，duplicate复本
     * @param introspectedTable
     * @throws Exception
     */
    public static void generator(String targetSrcPath, String genFilesType, String genFilesModal, IntrospectedTable introspectedTable) throws Exception {
        Configuration cfg = new Configuration();
        // 设置FreeMarker的模版文件位置
        cfg.setClassForTemplateLoading(SourceCodeBuilder.class, TEMPLATE_PATH + File.separator + genFilesModal);
        cfg.setDefaultEncoding(ENCODING);

        String modelName = introspectedTable.getBaseRecordType(); // com.ufo.demo.center.model.User
        String rootPackagePath = StringUtil.replace(StringUtil.substringBeforeLast(modelName, ".model"), ".", File.separator); // rootPackagePath = com/hitao/demo/center
        String projectName = StringUtil.substringBetween(modelName, "com.ufo." , ".center.");     // projectName = demo
        String className = StringUtil.substringAfterLast(modelName, "."); //  className = User
        String uncapClassName = StringUtil.uncapitalize(className); //  className = user
        String pkgName = StringUtil.lowerCase(className); //  className = user

        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("date", new Date());
        dataMap.put("project_name", projectName);  // demo
        dataMap.put("model_name", className); // User
        dataMap.put("pkgName", pkgName); // pkgName
        dataMap.put("model_name_uncapitalize", uncapClassName);  // user
        dataMap.put("id_type", "Long");
        dataMap.put("model_title", className);
        boolean all = introspectedTable.getTableConfiguration().getProperties().size() == 0;
        dataMap.put("insert", introspectedTable.getTableConfigurationProperty("insert") != null || all);
        dataMap.put("selectByPrimaryKey", introspectedTable.getTableConfigurationProperty("selectByPrimaryKey") != null || all);
        dataMap.put("deleteByPrimaryKey", introspectedTable.getTableConfigurationProperty("deleteByPrimaryKey") != null || all);
        dataMap.put("selectAll", introspectedTable.getTableConfigurationProperty("selectAll") != null || all);
        dataMap.put("updateByPrimaryKey", introspectedTable.getTableConfigurationProperty("updateByPrimaryKey") != null || all);
        dataMap.put("selectByParameters", introspectedTable.getTableConfigurationProperty("selectByParameters") != null || all);
        dataMap.put("selectByPageList", introspectedTable.getTableConfigurationProperty("selectByPageList") != null || all);
        dataMap.put("batchInsert", introspectedTable.getTableConfigurationProperty("batchInsert") != null || all);
        dataMap.put("batchDelete", introspectedTable.getTableConfigurationProperty("batchDelete") != null || all);
        setField(dataMap, introspectedTable);

        String generatorCodePath = StringUtil.isNotBlank(targetSrcPath) ? targetSrcPath : TARGET_PATH;
        String javaCenterPath = generatorCodePath + File.separator + "main" + File.separator + "java" + File.separator + rootPackagePath;
        String testCenterPath = generatorCodePath + File.separator + "test" + File.separator + "java" + File.separator + rootPackagePath;
        String webAppPath = generatorCodePath + File.separator + "main" + File.separator + "webapp" + File.separator;
        if (genFilesType.indexOf("dao") != -1) {
            process(cfg.getTemplate("DAO.ftl"), dataMap, javaCenterPath + File.separator + "dao" + File.separator + pkgName + File.separator,
                    className + "DAO.java");
            process(cfg.getTemplate("DAOImpl.ftl"), dataMap, javaCenterPath + File.separator + "dao" + File.separator + pkgName + File.separator + "impl" + File.separator,
                    className + "DAOImpl.java");
        }
        if (genFilesType.indexOf("service") != -1) {
            process(cfg.getTemplate("Service.ftl"), dataMap, javaCenterPath + File.separator + "service" + File.separator + pkgName + File.separator,
                    className + "Service.java");
            process(cfg.getTemplate("ServiceImpl.ftl"), dataMap, javaCenterPath + File.separator + "service" + File.separator + pkgName + File.separator + "impl" + File.separator,
                    className + "ServiceImpl.java");
            process(cfg.getTemplate("ServiceTest.ftl"), dataMap, testCenterPath + File.separator + "test" + File.separator + "service" + File.separator + pkgName + File.separator,
                    className + "ServiceTest.java");
        }
        if (genFilesType.indexOf("action") != -1) {
            process(cfg.getTemplate("Action.ftl"), dataMap, javaCenterPath + File.separator + "web" + File.separator + pkgName + File.separator,
                    className + "Action.java");
            process(cfg.getTemplate("ActionTest.ftl"), dataMap, testCenterPath + File.separator + "test" + File.separator + "web" + File.separator + pkgName + File.separator,
                    className + "ActionTest.java");
        }
        if (genFilesType.indexOf("page") != -1) {
            process(cfg.getTemplate("PageIndex.ftl"), dataMap, webAppPath + "WEB-INF" + File.separator + "template" + File.separator + "view" + File.separator + pkgName + File.separator,
                    "index.ftl");
            process(cfg.getTemplate("PageOperator.ftl"), dataMap, webAppPath + "WEB-INF" + File.separator + "template" + File.separator + "view" + File.separator + pkgName + File.separator,
                    "operator.ftl");
        }
        if (genFilesType.indexOf("js") != -1) {
            process(cfg.getTemplate("JSIndex.ftl"), dataMap, webAppPath + "statics" + File.separator + "js" + File.separator + pkgName + File.separator,
                    "index.js");
            process(cfg.getTemplate("JSOperator.ftl"), dataMap, webAppPath + "statics" + File.separator + "js" + File.separator + pkgName + File.separator,
                    "operator.js");
        }
    }

    /**
     * 设置生成代码的字段值
     * @param dataMap
     * @param introspectedTable
     */
    private static void setField(Map<String, Object> dataMap, IntrospectedTable introspectedTable){
        List<ModelCodeField> modelFields = new ArrayList<>();
        int cnt = 1;
        for (IntrospectedColumn column : introspectedTable.getAllColumns()) {
            if ("id".equals(column.getActualColumnName())) {
                continue;
            }
            debug(" - Field=" + column.getActualColumnName());
            String fieldType = column.getFullyQualifiedJavaType().getFullyQualifiedName();

            ModelCodeField modelCodeField = null;
            if (fieldType.equals("java.lang.Integer")) {
                modelCodeField = new ModelCodeField();
                modelCodeField.setListWidth(80);
                modelCodeField.setListAlign("right");
            } else if (fieldType.equals("java.lang.Boolean")) {
                modelCodeField = new ModelCodeField();
                modelCodeField.setListAlign("center");
            } else if (fieldType.equals("java.lang.Number")) {
                modelCodeField = new ModelCodeField();
                modelCodeField.setListWidth(60);
                modelCodeField.setListAlign("right");
            } else if (fieldType.equals("java.lang.String")) {
                modelCodeField = new ModelCodeField();
                modelCodeField.setListWidth(60);
                modelCodeField.setFieldLength(column.getLength());
                modelCodeField.setListAlign("center");
            } else if (fieldType.equals("java.util.Date")) {
                modelCodeField = new ModelCodeField();
                modelCodeField.setListWidth(150);
                modelCodeField.setListAlign("center");
            }

            if (modelCodeField != null) {
                modelCodeField.setFieldName(column.getJavaProperty());
                modelCodeField.setFieldType(fieldType);
                modelCodeField.setTitle(column.getRemarks().split(" ")[0]);
                modelCodeField.setOrder(cnt++);
                modelFields.add(modelCodeField);
            }
        }
        Collections.sort(modelFields);
        dataMap.put("modelFields", modelFields);
    }

    private static void debug(String message) {
        System.out.println(message);
    }

    private static void process(Template template, Map<String, Object> root, String dir, String fileName)
            throws Exception {
        if ((dir + fileName).length() > 300) {
            throw new IllegalArgumentException("Dir path too long.");
        }
        File newsDir = new File(dir);
        if (!newsDir.exists()) {
            newsDir.mkdirs();
        }
        debug("Write to file: " + dir + fileName);
        Writer out = new OutputStreamWriter(new FileOutputStream(dir + fileName), "UTF-8");
        template.process(root, out);
    }

    /**
     * 对象属性转换为字段 例如：userName to user_name
     *
     * @param property
     *            字段名
     * @return
     */
    public static String propertyToField(String property) {
        if (null == property) {
            return "";
        }
        char[] chars = property.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (char c : chars) {
            if (CharUtils.isAsciiAlphaUpper(c)) {
                sb.append("-" + StringUtil.lowerCase(CharUtils.toString(c)));
            } else {
                sb.append(c);
            }
        }
        return sb.toString().toUpperCase();
    }

}
