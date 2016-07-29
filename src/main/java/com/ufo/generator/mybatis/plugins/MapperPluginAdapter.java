package com.ufo.generator.mybatis.plugins;

import com.ufo.generator.builder.SourceCodeBuilder;
import com.ufo.generator.util.StringUtil;
import org.mybatis.generator.api.*;
import org.mybatis.generator.api.dom.OutputUtilities;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.XmlConstants;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.exception.ShellException;
import org.mybatis.generator.internal.DomWriter;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.util.*;

/***
 * 添加生成selectByParameters的sql和mapper方法
 *
 * @author hekang
 * @created 2016/1/18
 */
public class MapperPluginAdapter extends PluginAdapter {

    private Map<String, String> mapperFiles;

    public MapperPluginAdapter() {
        super();
        mapperFiles = new LinkedHashMap<>();
    }

    private static String SELECT_BY_PARAMETERS = "selectByParameters";

    private static String SELECT_BY_PAGELIST = "selectByPageList";

    private static String SELECT_BY_PAGELIST_SUM = "selectByPageListSum";

    private static String BATCH_INSERT = "batchInsert";

    private static String BATCH_DELETE = "batchDelete";

    public boolean validate(List<String> warnings) {
        return true;
    }

    // 已经抽象到基类接口
    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        if (checkInterface(interfaze)){
            boolean all = introspectedTable.getTableConfiguration().getProperties().size() == 0;
            // 加selectByParameters方法
            if (introspectedTable.getTableConfigurationProperty("selectByParameters") != null || all)
                addClientSelectByParameters(interfaze, introspectedTable);
            // 加selectByParameters方法
            if (introspectedTable.getTableConfigurationProperty("selectByPageList") != null || all) {
                addClientSelectByPageList(interfaze, introspectedTable);
                addClientSelectByPageListSum(interfaze, introspectedTable);
            }
            // 加batchInsert方法
            if (introspectedTable.getTableConfigurationProperty("batchInsert") != null || all)
                addClientBatchInsert(interfaze, introspectedTable);
            // 加batchInsert方法
            if (introspectedTable.getTableConfigurationProperty("batchDelete") != null || all)
                addClientBatchDelete(interfaze, introspectedTable);
        }
        return super.clientGenerated(interfaze, topLevelClass, introspectedTable);
    }

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        boolean all = introspectedTable.getTableConfiguration().getProperties().size() == 0;
        // 加selectByParameters sql
        if (introspectedTable.getTableConfigurationProperty("selectByParameters") != null || all)
            addSqlMapSelectByParameters(document, introspectedTable);
        // 加selectByParameters sql
        if (introspectedTable.getTableConfigurationProperty("selectByPageList") != null || all) {
            addSqlMapSelectByPageList(document, introspectedTable);
            addSqlMapSelectByPageListSum(document, introspectedTable);
        }
        // 加batchInsert sql
        if (introspectedTable.getTableConfigurationProperty("batchInsert") != null || all)
            addBatchInsert(document, introspectedTable);
        // 加batchInsert sql
        if (introspectedTable.getTableConfigurationProperty("batchDelete") != null || all)
            addBatchDelete(document, introspectedTable);
        return super.sqlMapDocumentGenerated(document, introspectedTable);
    }

    /**
     * 合并Mybatis配置文件
     * @return
     */
    @Override
    public List<GeneratedXmlFile> contextGenerateAdditionalXmlFiles() {
        if (properties.get("isAddConfiguration") == null || !properties.get("isAddConfiguration").equals("true")){
            return null;
        }

        String xmlPath = properties.getProperty("targetProject") + File.separator + StringUtil.replace(properties.getProperty("targetPackage"), ".", File.separator) + File.separator + properties
                .getProperty("fileName", "MapperConfig.xml");
        File targetFile = new File(xmlPath);
        if (targetFile.exists()){
            // 合并配置文件
            try {
                InputSource existingFile = new InputSource(new InputStreamReader(new FileInputStream(targetFile), "UTF-8"));
                DocumentBuilderFactory factory = DocumentBuilderFactory
                        .newInstance();
                factory.setExpandEntityReferences(false);
                DocumentBuilder builder = factory.newDocumentBuilder();
                org.w3c.dom.Document existingDocument = builder.parse(existingFile);
                Element conf = existingDocument.getDocumentElement();
                NodeList nodeList = existingDocument.getDocumentElement().getElementsByTagName("mappers");
                Element mappers;
                if (nodeList != null && nodeList.getLength() != 0){
                    mappers = (Element)nodeList.item(0);
                    NodeList mapperNodeList = mappers.getElementsByTagName("mapper");
                    for (int i = 0; i < mapperNodeList.getLength(); i ++){
                        Node mapper = mapperNodeList.item(i);
                        if (mapper.getNodeName().equals("mapper")){
                            mapperFiles.remove(mapper.getAttributes().item(0).getNodeValue());
                        }
                    }
                } else {
                    mappers = existingDocument.createElement("mappers");
                    conf.appendChild(mappers);
                }
                for (String resource : mapperFiles.values()) {
                    mappers.appendChild(existingDocument.createTextNode("\t"));
                    Element mapper = existingDocument.createElement("mapper");
                    mapper.setAttribute("resource", resource);
                    mappers.appendChild(mapper);
                    mappers.appendChild(existingDocument.createTextNode("\n\t"));
                }
                writeFile(targetFile, prettyPrint(existingDocument), "UTF-8");
            } catch (Exception e){
                System.out.println(e);
            }
            return null;
        }

        // 新建mybatis配置文件
        Document document = new Document(
                XmlConstants.MYBATIS3_MAPPER_CONFIG_PUBLIC_ID,
                XmlConstants.MYBATIS3_MAPPER_CONFIG_SYSTEM_ID);

        XmlElement root = new XmlElement("configuration"); //$NON-NLS-1$
        document.setRootElement(root);

        XmlElement mappers = new XmlElement("mappers"); //$NON-NLS-1$
        root.addElement(mappers);

        XmlElement mapper;
        for (String mapperFile : mapperFiles.values()) {
            mapper = new XmlElement("mapper"); //$NON-NLS-1$
            mapper.addAttribute(new Attribute("resource", mapperFile)); //$NON-NLS-1$
            mappers.addElement(mapper);
        }

        GeneratedXmlFile gxf = new GeneratedXmlFile(document, properties
                .getProperty("fileName", "MapperConfig.xml"), //$NON-NLS-1$ //$NON-NLS-2$
                properties.getProperty("targetPackage"), //$NON-NLS-1$
                properties.getProperty("targetProject"), //$NON-NLS-1$
                false, context.getXmlFormatter());

        List<GeneratedXmlFile> answer = new ArrayList<GeneratedXmlFile>(1);
        answer.add(gxf);

        return answer;
    }

    @Override
    public boolean sqlMapGenerated(GeneratedXmlFile sqlMap,
                                   IntrospectedTable introspectedTable) {
        StringBuilder sb = new StringBuilder();
        sb.append(sqlMap.getTargetPackage());
        sb.append('.');
        String temp = sb.toString();
        sb.setLength(0);
        sb.append(temp.replace('.', '/'));
        sb.append(sqlMap.getFileName());
        mapperFiles.put(sb.toString(), sb.toString());

        return true;
    }

    /**
     * Mapper类加selectByParameters方法
     * @param interfaze
     * @param introspectedTable
     */
    private void addClientSelectByParameters(Interface interfaze, IntrospectedTable introspectedTable) {
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
        importedTypes.add(FullyQualifiedJavaType.getNewListInstance());

        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);

        FullyQualifiedJavaType returnType = FullyQualifiedJavaType
                .getNewListInstance();
        FullyQualifiedJavaType listType = new FullyQualifiedJavaType(
                introspectedTable.getBaseRecordType());

        importedTypes.add(listType);
        returnType.addTypeArgument(listType);
        method.setReturnType(returnType);
        method.setName(SELECT_BY_PARAMETERS);
        FullyQualifiedJavaType parameterType = FullyQualifiedJavaType.getNewMapInstance();
        importedTypes.add(parameterType);
        parameterType.addTypeArgument(FullyQualifiedJavaType.getStringInstance());
        parameterType.addTypeArgument(FullyQualifiedJavaType.getObjectInstance());
        Parameter parameter = new Parameter(parameterType, "parameter");
        method.addParameter(parameter);

        context.getCommentGenerator().addGeneralMethodComment(method,
                introspectedTable);

        interfaze.addImportedTypes(importedTypes);
        interfaze.addMethod(method);
    }

    /**
     * Mapper类加addClientSelectByPageList方法
     * @param interfaze
     * @param introspectedTable
     */
    private void addClientSelectByPageList(Interface interfaze, IntrospectedTable introspectedTable) {
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
        importedTypes.add(FullyQualifiedJavaType.getNewListInstance());

        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);

        FullyQualifiedJavaType returnType = FullyQualifiedJavaType
                .getNewListInstance();
        FullyQualifiedJavaType listType = new FullyQualifiedJavaType(
                introspectedTable.getBaseRecordType());

        importedTypes.add(listType);
        returnType.addTypeArgument(listType);
        method.setReturnType(returnType);
        method.setName(SELECT_BY_PAGELIST);
        FullyQualifiedJavaType parameterType = FullyQualifiedJavaType.getNewMapInstance();
        importedTypes.add(parameterType);
        parameterType.addTypeArgument(FullyQualifiedJavaType.getStringInstance());
        parameterType.addTypeArgument(FullyQualifiedJavaType.getObjectInstance());
        Parameter parameter = new Parameter(parameterType, "parameter");
        method.addParameter(parameter);

        context.getCommentGenerator().addGeneralMethodComment(method,
                introspectedTable);

        interfaze.addImportedTypes(importedTypes);
        interfaze.addMethod(method);
    }

    /**
     * Mapper类加addClientSelectByPageListSum方法
     * @param interfaze
     * @param introspectedTable
     */
    private void addClientSelectByPageListSum(Interface interfaze, IntrospectedTable introspectedTable) {
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
        importedTypes.add(FullyQualifiedJavaType.getNewListInstance());

        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);

        FullyQualifiedJavaType returnType = FullyQualifiedJavaType.getNewMapInstance();
        importedTypes.add(returnType);
        returnType.addTypeArgument(FullyQualifiedJavaType.getObjectInstance());
        returnType.addTypeArgument(FullyQualifiedJavaType.getObjectInstance());
        method.setReturnType(returnType);
        method.setName(SELECT_BY_PAGELIST_SUM);
        FullyQualifiedJavaType parameterType = FullyQualifiedJavaType.getNewMapInstance();
        importedTypes.add(parameterType);
        parameterType.addTypeArgument(FullyQualifiedJavaType.getStringInstance());
        parameterType.addTypeArgument(FullyQualifiedJavaType.getObjectInstance());
        Parameter parameter = new Parameter(parameterType, "parameter");
        method.addParameter(parameter);

        context.getCommentGenerator().addGeneralMethodComment(method,
                introspectedTable);

        interfaze.addImportedTypes(importedTypes);
        interfaze.addMethod(method);
    }

    /**
     * Mapper类加batchInsert方法
     * @param interfaze
     * @param introspectedTable
     */
    private void addClientBatchInsert(Interface interfaze, IntrospectedTable introspectedTable) {
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
        importedTypes.add(FullyQualifiedJavaType.getNewListInstance());

        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);

        FullyQualifiedJavaType returnType = FullyQualifiedJavaType
               .getIntInstance();

        method.setReturnType(returnType);
        method.setName(BATCH_INSERT);
        FullyQualifiedJavaType parameterType = FullyQualifiedJavaType
                .getNewListInstance();
        FullyQualifiedJavaType listType = new FullyQualifiedJavaType(
                introspectedTable.getBaseRecordType());
        parameterType.addTypeArgument(listType);
        importedTypes.add(parameterType);
        Parameter parameter = new Parameter(parameterType, "recordList");
        method.addParameter(parameter);

        context.getCommentGenerator().addGeneralMethodComment(method,
                introspectedTable);

        interfaze.addImportedTypes(importedTypes);
        interfaze.addMethod(method);
    }

    /**
     * Mapper类加batchDelete方法
     * @param interfaze
     * @param introspectedTable
     */
    private void addClientBatchDelete(Interface interfaze, IntrospectedTable introspectedTable) {
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
        importedTypes.add(FullyQualifiedJavaType.getNewListInstance());

        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);

        FullyQualifiedJavaType returnType = FullyQualifiedJavaType
                .getIntInstance();

        method.setReturnType(returnType);
        method.setName(BATCH_DELETE);
        FullyQualifiedJavaType parameterType = FullyQualifiedJavaType
                .getNewListInstance();
        FullyQualifiedJavaType listType = new FullyQualifiedJavaType("java.lang.Long");
        parameterType.addTypeArgument(listType);
        importedTypes.add(parameterType);
        Parameter parameter = new Parameter(parameterType, "ids");
        method.addParameter(parameter);

        context.getCommentGenerator().addGeneralMethodComment(method,
                introspectedTable);

        interfaze.addImportedTypes(importedTypes);
        interfaze.addMethod(method);
    }

    /**
     * sqlMap加selectByParameters sql
     * @param document
     * @param introspectedTable
     */
    private void addSqlMapSelectByParameters(Document document, IntrospectedTable introspectedTable){
        XmlElement answer = new XmlElement("select"); //$NON-NLS-1$

        answer.addAttribute(new Attribute(
                "id", SELECT_BY_PARAMETERS)); //$NON-NLS-1$
        if (introspectedTable.getRules().generateResultMapWithBLOBs()) {
            answer.addAttribute(new Attribute("resultMap", //$NON-NLS-1$
                    introspectedTable.getResultMapWithBLOBsId()));
        } else {
            answer.addAttribute(new Attribute("resultMap", //$NON-NLS-1$
                    introspectedTable.getBaseResultMapId()));
        }

        answer.addAttribute(new Attribute("parameterType", //$NON-NLS-1$
                "java.util.HashMap"));

        context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();
        sb.append("select "); //$NON-NLS-1$

        Iterator<IntrospectedColumn> iter = introspectedTable.getAllColumns()
                .iterator();
        while (iter.hasNext()) {
            sb.append(MyBatis3FormattingUtilities.getSelectListPhrase(iter
                    .next()));

            if (iter.hasNext()) {
                sb.append(", "); //$NON-NLS-1$
            }

            if (sb.length() > 80) {
                answer.addElement(new TextElement(sb.toString()));
                sb.setLength(0);
            }
        }

        if (sb.length() > 0) {
            answer.addElement((new TextElement(sb.toString())));
        }

        sb.setLength(0);
        sb.append("from "); //$NON-NLS-1$
        sb.append(introspectedTable
                .getAliasedFullyQualifiedTableNameAtRuntime());
        answer.addElement(new TextElement(sb.toString()));

        document.getRootElement().addElement(answer);
    }

    /**
     * sqlMap加addSqlMapSelectByPageList sql
     * @param document
     * @param introspectedTable
     */
    private void addSqlMapSelectByPageList(Document document, IntrospectedTable introspectedTable){
        XmlElement answer = new XmlElement("select"); //$NON-NLS-1$

        answer.addAttribute(new Attribute(
                "id", SELECT_BY_PAGELIST)); //$NON-NLS-1$
        if (introspectedTable.getRules().generateResultMapWithBLOBs()) {
            answer.addAttribute(new Attribute("resultMap", //$NON-NLS-1$
                    introspectedTable.getResultMapWithBLOBsId()));
        } else {
            answer.addAttribute(new Attribute("resultMap", //$NON-NLS-1$
                    introspectedTable.getBaseResultMapId()));
        }

        answer.addAttribute(new Attribute("parameterType", //$NON-NLS-1$
                "java.util.HashMap"));

        context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();
        sb.append("select "); //$NON-NLS-1$

        Iterator<IntrospectedColumn> iter = introspectedTable.getAllColumns()
                .iterator();
        while (iter.hasNext()) {
            sb.append(MyBatis3FormattingUtilities.getSelectListPhrase(iter
                    .next()));

            if (iter.hasNext()) {
                sb.append(", "); //$NON-NLS-1$
            }

            if (sb.length() > 80) {
                answer.addElement(new TextElement(sb.toString()));
                sb.setLength(0);
            }
        }

        if (sb.length() > 0) {
            answer.addElement((new TextElement(sb.toString())));
        }

        sb.setLength(0);
        sb.append("from "); //$NON-NLS-1$
        sb.append(introspectedTable
                .getAliasedFullyQualifiedTableNameAtRuntime());
        answer.addElement(new TextElement(sb.toString()));

        document.getRootElement().addElement(answer);
    }

    /**
     * sqlMap加addSqlMapSelectByPageListSum sql
     * @param document
     * @param introspectedTable
     */
    private void addSqlMapSelectByPageListSum(Document document, IntrospectedTable introspectedTable){
        XmlElement answer = new XmlElement("select"); //$NON-NLS-1$

        answer.addAttribute(new Attribute(
                "id", SELECT_BY_PAGELIST_SUM)); //$NON-NLS-1$
        answer.addAttribute(new Attribute("resultType", //$NON-NLS-1$
                "java.util.HashMap"));
        answer.addAttribute(new Attribute("parameterType", //$NON-NLS-1$
                "java.util.HashMap"));

        context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();
        sb.append("select count(1) as totalNum "); //$NON-NLS-1$

        Iterator<IntrospectedColumn> iter = introspectedTable.getAllColumns()
                .iterator();
        while (iter.hasNext()) {
            IntrospectedColumn col = iter.next();
            if (col.getFullyQualifiedJavaType().getFullyQualifiedName().equals("java.lang.Long") && col.getActualColumnName().indexOf("id") == -1) {
                sb.append(" ,sum(");
                sb.append(MyBatis3FormattingUtilities.getSelectListPhrase(col));
                sb.append(")");
                if (sb.length() > 80) {
                    answer.addElement(new TextElement(sb.toString()));
                    sb.setLength(0);
                }
            }
        }

        if (sb.length() > 0) {
            answer.addElement((new TextElement(sb.toString())));
        }

        sb.setLength(0);
        sb.append("from "); //$NON-NLS-1$
        sb.append(introspectedTable
                .getAliasedFullyQualifiedTableNameAtRuntime());
        answer.addElement(new TextElement(sb.toString()));

        document.getRootElement().addElement(answer);
    }

    /**
     * sqlMap加batchInsert sql
     * @param document
     * @param introspectedTable
     */
    private void addBatchInsert(Document document, IntrospectedTable introspectedTable){
        XmlElement answer = new XmlElement("insert");

        answer.addAttribute(new Attribute("id", BATCH_INSERT));

        answer.addAttribute(new Attribute("parameterType", FullyQualifiedJavaType.getNewListInstance().getFullyQualifiedName()));

        context.getCommentGenerator().addComment(answer);

        StringBuilder insertClause = new StringBuilder();
        StringBuilder valuesClause = new StringBuilder();

        insertClause.append("insert into "); //$NON-NLS-1$
        insertClause.append(introspectedTable
                .getFullyQualifiedTableNameAtRuntime());
        insertClause.append(" ("); //$NON-NLS-1$

        valuesClause.append("values <foreach collection=\"list\" item=\"item\" separator=\",\">("); //$NON-NLS-1$

        List<String> valuesClauses = new ArrayList<String>();
        Iterator<IntrospectedColumn> iter = introspectedTable.getAllColumns()
                .iterator();
        while (iter.hasNext()) {
            IntrospectedColumn introspectedColumn = iter.next();
            if (introspectedColumn.isIdentity()) {
                // cannot set values on identity fields
                continue;
            }

            insertClause.append(MyBatis3FormattingUtilities
                    .getEscapedColumnName(introspectedColumn));
            valuesClause.append(MyBatis3FormattingUtilities
                    .getParameterClause(introspectedColumn, "item."));
            if (iter.hasNext()) {
                insertClause.append(", "); //$NON-NLS-1$
                valuesClause.append(", "); //$NON-NLS-1$
            }

            if (valuesClause.length() > 80) {
                answer.addElement(new TextElement(insertClause.toString()));
                insertClause.setLength(0);
                OutputUtilities.xmlIndent(insertClause, 1);

                valuesClauses.add(valuesClause.toString());
                valuesClause.setLength(0);
                OutputUtilities.xmlIndent(valuesClause, 1);
            }
        }

        insertClause.append(')');
        answer.addElement(new TextElement(insertClause.toString()));

        valuesClause.append(")</foreach>");
        valuesClauses.add(valuesClause.toString());
        for (String clause : valuesClauses) {
            answer.addElement(new TextElement(clause));
        }

        document.getRootElement().addElement(answer);
    }

    /**
     * sqlMap加batchDelete sql
     * @param document
     * @param introspectedTable
     */
    private void addBatchDelete(Document document, IntrospectedTable introspectedTable){
        XmlElement answer = new XmlElement("delete");
        answer.addAttribute(new Attribute("id", BATCH_DELETE));
        answer.addAttribute(new Attribute("parameterType", FullyQualifiedJavaType.getNewListInstance().getFullyQualifiedName()));
        context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();
        sb.append("delete from "); //$NON-NLS-1$
        sb.append(introspectedTable
                .getFullyQualifiedTableNameAtRuntime());
        answer.addElement(new TextElement(sb.toString()));

        answer.addElement(new TextElement("where id in "));
        answer.addElement(new TextElement("<foreach collection=\"list\" item=\"id\" open=\"(\" close=\")\" separator=\",\">"));
        answer.addElement(new TextElement("  ${id}"));
        answer.addElement(new TextElement("</foreach>"));
        document.getRootElement().addElement(answer);
    }

    @Override
    public boolean clientInsertMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return checkInterface(interfaze) && introspectedTable.getTableConfigurationProperty("insert") == null;
    }

    @Override
    public boolean sqlMapInsertElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return introspectedTable.getTableConfigurationProperty("insert") == null;
    }

    @Override
    public boolean clientSelectByPrimaryKeyMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return checkInterface(interfaze) && introspectedTable.getTableConfigurationProperty("selectByPrimaryKey") == null;
    }

    @Override
    public boolean sqlMapSelectByPrimaryKeyElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return introspectedTable.getTableConfigurationProperty("selectByPrimaryKey") == null;
    }

    @Override
    public boolean clientDeleteByPrimaryKeyMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return checkInterface(interfaze) && introspectedTable.getTableConfigurationProperty("deleteByPrimaryKey") == null;
    }

    @Override
    public boolean sqlMapDeleteByPrimaryKeyElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return introspectedTable.getTableConfigurationProperty("deleteByPrimaryKey") == null;
    }

    @Override
    public boolean clientSelectAllMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return checkInterface(interfaze) && introspectedTable.getTableConfigurationProperty("selectAll") == null;
    }

    @Override
    public boolean sqlMapSelectAllElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return introspectedTable.getTableConfigurationProperty("selectAll") == null;
    }

    @Override
    public boolean clientUpdateByPrimaryKeyWithoutBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return checkInterface(interfaze) && introspectedTable.getTableConfigurationProperty("updateByPrimaryKey") == null;
    }

    @Override
    public boolean sqlMapUpdateByPrimaryKeyWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return introspectedTable.getTableConfigurationProperty("updateByPrimaryKey") == null;
    }

    private boolean checkInterface(Interface interfaze){
        if (interfaze == null || interfaze.getSuperInterfaceTypes() == null || interfaze.getSuperInterfaceTypes().size() == 0)
            return true;
        for (FullyQualifiedJavaType type : interfaze.getSuperInterfaceTypes()){
            if (type.getShortName().startsWith("BaseMapper"))
                return false;
        }
        return true;
    }

    private static String prettyPrint(org.w3c.dom.Document document) throws ShellException {
        DomWriter dw = new DomWriter();
        String s = dw.toString(document);
        return s;
    }

    /**
     * Writes, or overwrites, the contents of the specified file
     *
     * @param file
     * @param content
     */
    private void writeFile(File file, String content, String fileEncoding) throws IOException {
        FileOutputStream fos = new FileOutputStream(file, false);
        OutputStreamWriter osw;
        if (fileEncoding == null) {
            osw = new OutputStreamWriter(fos);
        } else {
            osw = new OutputStreamWriter(fos, fileEncoding);
        }

        BufferedWriter bw = new BufferedWriter(osw);
        bw.write(content);
        bw.close();
    }
}
