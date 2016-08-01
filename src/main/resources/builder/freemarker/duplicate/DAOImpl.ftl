package com.ufo.${project_name}.center.dao.${pkg_name}.impl;

import com.ufo.${project_name}.center.dao.${pkg_name}.${model_name}DAO;
import com.ufo.${project_name}.center.dao.${pkg_name}.mapper.${model_name}Mapper;
import com.ufo.${project_name}.center.model.${model_name};
import com.ufo.${project_name}.common.exceptions.DAOException;
import com.ufo.${project_name}.common.mybatis.pagehelper.PageList;
import com.ufo.${project_name}.common.util.PageUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/***
 * ${model_title}数据库操作DAO实现
 *
 * @author generator
 * @created ${date?string("yyyy-MM-dd")}
 */
@Repository
public class ${model_name}DAOImpl implements ${model_name}DAO {

    @Autowired
    ${model_name}Mapper ${model_name_uncapitalize}Mapper;

    protected final Logger logger = LogManager.getLogger(this.getClass());

    <#if insert==true>
    @Override
    public int insert(${model_name} model) throws DAOException {
        try{
            return ${model_name_uncapitalize}Mapper.insert(model);
        }catch(Throwable e){
            throw new DAOException(e.getMessage(),e);
        }
    }
    </#if>

    <#if updateByPrimaryKey==true>
    @Override
    public int update(${model_name} model) throws DAOException {
        try{
            return ${model_name_uncapitalize}Mapper.updateByPrimaryKey(model);
        }catch(Throwable e){
            throw new DAOException(e.getMessage(),e);
        }
    }
    </#if>

    <#if deleteByPrimaryKey==true>
    @Override
    public int deleteById(${id_type} id) throws DAOException {
        try{
            return ${model_name_uncapitalize}Mapper.deleteByPrimaryKey(id);
        }catch(Throwable e){
            throw new DAOException(e.getMessage(),e);
        }
    }
    </#if>

    <#if selectByPrimaryKey==true>
    @Override
    public ${model_name} selectById(${id_type} id) throws DAOException {
        try{
            return ${model_name_uncapitalize}Mapper.selectByPrimaryKey(id);
        }catch(Throwable e){
            throw new DAOException(e.getMessage(),e);
        }
    }
    </#if>

    <#if selectAll==true>
    @Override
    public List<${model_name}> selectAll() throws DAOException {
        try{
            return ${model_name_uncapitalize}Mapper.selectAll();
        }catch(Throwable e){
            throw new DAOException(e.getMessage(),e);
        }
    }
    </#if>

    <#if selectByParameters==true>
    @Override
    public List<${model_name}> selectByParameters(Map<String, Object> parameters) throws DAOException {
        try{
            return ${model_name_uncapitalize}Mapper.selectByParameters(parameters);
        }catch(Throwable e){
            throw new DAOException(e.getMessage(),e);
        }
    }
    </#if>

    <#if batchInsert==true>
    @Override
    public int batchInsert(List<${model_name}> modelList) throws DAOException {
        try{
            return ${model_name_uncapitalize}Mapper.batchInsert(modelList);
        }catch(Throwable e){
            throw new DAOException(e.getMessage(),e);
        }
    }
    </#if>

    <#if batchDelete==true>
    @Override
    public int batchDelete(List<${id_type}> ids) throws DAOException {
        try{
            return ${model_name_uncapitalize}Mapper.batchDelete(ids);
        }catch(Throwable e){
            throw new DAOException(e.getMessage(),e);
        }
    }
    </#if>

    <#if selectByPageList==true>
    /**
    * 带参数的分页查询
    * @param parameterObject
    * @param pageNum
    * @param pageSize
    * @return
    */
    public PageList<${model_name}> executeQueryForList(Map<String, Object> parameterObject, Integer pageNum, Integer pageSize) throws DAOException {
        try{
            PageUtil.startPage(pageNum, pageSize);
            List<${model_name}> rows = ${model_name_uncapitalize}Mapper.selectByPageList(parameterObject);
            PageList pageList = new PageList(rows);
            return pageList;
        }catch(Throwable e){
            throw new DAOException(e.getMessage(),e);
        }
    }
    </#if>

    <#if selectByPageList==true>
    /**
    * 带参数的分页合计查询
    * @param parameterObject
    * @param pageNum
    * @param pageSize
    * @return
    */
    public PageList<${model_name}> executeSumQueryForList(Map<String, Object> parameterObject, Integer pageNum, Integer pageSize) throws DAOException {
        try{
            PageUtil.startPageNotCount(pageNum, pageSize);
            return PageUtil.getPageList(${model_name_uncapitalize}Mapper.selectByPageList(parameterObject),
                    ${model_name_uncapitalize}Mapper.selectByPageListSum(parameterObject));
        }catch(Throwable e){
            throw new DAOException(e.getMessage(),e);
        }
    }
    </#if>

}
