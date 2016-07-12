package com.ufo.generator.mybatis.javamapper;

import com.ufo.generator.util.StringUtil;
import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.codegen.mybatis3.javamapper.SimpleJavaClientGenerator;
import org.mybatis.generator.config.PropertyRegistry;

import java.util.ArrayList;
import java.util.List;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

/***
 * 自定义Mapper接口生成器
 *
 * @author hekang
 * @created 2016/1/18
 */
public class HTJavaClientGenerator extends SimpleJavaClientGenerator {

    public HTJavaClientGenerator(){
        super();
    }

    @Override
    public List<CompilationUnit> getCompilationUnits() {
        progressCallback.startTask(getString("Progress.17", //$NON-NLS-1$
                introspectedTable.getFullyQualifiedTable().toString()));
        CommentGenerator commentGenerator = context.getCommentGenerator();
        String mapperName = introspectedTable.getMyBatis3JavaMapperType();
        // 加包区分
        String pkgName = StringUtil.lowerCase(introspectedTable.getTableConfiguration().getDomainObjectName());
        mapperName = mapperName.replaceAll(".mapper.", "." + pkgName + ".mapper.");
        introspectedTable.setMyBatis3JavaMapperType(mapperName);
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(mapperName);
        Interface interfaze = new Interface(type);
        interfaze.setVisibility(JavaVisibility.PUBLIC);
        commentGenerator.addJavaFileComment(interfaze);

        String rootInterface = introspectedTable
                .getTableConfigurationProperty(PropertyRegistry.ANY_ROOT_INTERFACE);
        if (!stringHasValue(rootInterface)) {
            rootInterface = context.getJavaClientGeneratorConfiguration()
                    .getProperty(PropertyRegistry.ANY_ROOT_INTERFACE);
        }

        if (stringHasValue(rootInterface)) {
            FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(
                    rootInterface);
            // 自定义加泛型
            fqjt.addTypeArgument(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));
            fqjt.addTypeArgument(new FullyQualifiedJavaType("java.lang.Long"));

            interfaze.addSuperInterface(fqjt);
            interfaze.addImportedType(fqjt);
        } else {
            addDeleteByPrimaryKeyMethod(interfaze);
            addInsertMethod(interfaze);
            addSelectByPrimaryKeyMethod(interfaze);
            addSelectAllMethod(interfaze);
            addUpdateByPrimaryKeyMethod(interfaze);
        }

        List<CompilationUnit> answer = new ArrayList<CompilationUnit>();
        if (context.getPlugins().clientGenerated(interfaze, null,
                introspectedTable)) {
            answer.add(interfaze);
        }

        List<CompilationUnit> extraCompilationUnits = getExtraCompilationUnits();
        if (extraCompilationUnits != null) {
            answer.addAll(extraCompilationUnits);
        }

        return answer;
    }
}
