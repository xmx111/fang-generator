package com.ufo.generator.mybatis.plugins;

import com.ufo.generator.builder.SourceCodeBuilder;
import com.ufo.generator.util.StringUtil;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;

import java.util.List;

/***
 * Describe
 *
 * @author hekang
 * @version $Id:$
 * @created 2016/1/22
 */
public class CURDPluginAdapter extends PluginAdapter {

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    /**
     * 生成CURD的fm,js,action,service,dao文件
     * @param introspectedTable
     * @return
     */
    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        try {
            // 是否生成其它所有文件(fm,js,action,service,dao)
            String genFilesType = properties.getProperty("genFilesType");
            String genFilesModal = StringUtil.isNotBlank(properties.getProperty("genFilesModal")) ? properties.getProperty("genFilesModal") : "extend";
            String genTargetSrcPath = properties.getProperty("genTargetSrcPath");
            if (genFilesType != null && !genFilesType.equals(""));
                SourceCodeBuilder.generator(genTargetSrcPath, genFilesType, genFilesModal, introspectedTable);
        } catch (Exception e){
            System.out.println(e);
        }
        return super.contextGenerateAdditionalJavaFiles(introspectedTable);
    }
}
