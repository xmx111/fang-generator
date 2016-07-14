package com.ufo.${project_name}.center.dao.${model_name_uncapitalize}.impl;

import com.ufo.${project_name}.center.dao.${model_name_uncapitalize}.${model_name}DAO;
import com.ufo.${project_name}.center.dao.${model_name_uncapitalize}.mapper.${model_name}Mapper;
import com.ufo.${project_name}.center.model.${model_name};
import com.ufo.${project_name}.common.dao.impl.BaseDAOImpl;
import com.ufo.${project_name}.common.dao.mapper.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/***
 * ${model_title}数据库操作DAO实现
 *
 * @author generator
 * @created ${date?string("yyyy-MM-dd")}
 */
@Repository
public class ${model_name}DAOImpl extends BaseDAOImpl<${model_name}, ${id_type}> implements ${model_name}DAO {

    @Autowired
    ${model_name}Mapper ${model_name_uncapitalize}Mapper;

    @Override
    protected BaseMapper<${model_name}, ${id_type}> getMapper() {
        return ${model_name_uncapitalize}Mapper;
    }

}
