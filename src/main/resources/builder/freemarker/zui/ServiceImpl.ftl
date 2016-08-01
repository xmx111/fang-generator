package com.ufo.${project_name}.center.service.${pkg_name}.impl;

import com.ufo.${project_name}.center.dao.${pkg_name}.${model_name}DAO;
import com.ufo.${project_name}.center.model.${model_name};
import com.ufo.${project_name}.center.service.${pkg_name}.${model_name}Service;
import com.ufo.${project_name}.common.dao.BaseDAO;
import com.ufo.${project_name}.common.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/***
 * ${model_title}服务实现
 *
 * @author generator
 * @created ${date?string("yyyy-MM-dd")}
 */
@Service
public class ${model_name}ServiceImpl extends BaseServiceImpl<${model_name}, ${id_type}> implements ${model_name}Service {

    @Autowired
    ${model_name}DAO ${model_name_uncapitalize}DAO;

    @Override
    protected BaseDAO<${model_name}, ${id_type}> getDAO() {
        return ${model_name_uncapitalize}DAO;
    }
}
