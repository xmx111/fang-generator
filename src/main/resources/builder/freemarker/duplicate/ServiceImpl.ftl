package com.ufo.${project_name}.center.service.${pkgName}.impl;

import com.ufo.${project_name}.center.dao.${pkgName}.${model_name}DAO;
import com.ufo.${project_name}.center.model.${model_name};
import com.ufo.${project_name}.center.service.${pkgName}.${model_name}Service;
import com.ufo.${project_name}.common.exceptions.DAOException;
import com.ufo.${project_name}.common.exceptions.ServiceException;
import com.ufo.${project_name}.common.mybatis.pagehelper.PageList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/***
 * ${model_title}服务实现
 *
 * @author generator
 * @created ${date?string("yyyy-MM-dd")}
 */
@Service
public class ${model_name}ServiceImpl implements ${model_name}Service {

    @Autowired
    ${model_name}DAO ${model_name_uncapitalize}DAO;

    public static Logger logger = LoggerFactory.getLogger(${model_name}ServiceImpl.class);

<#if insert==true>
    @Override
    public int add(${model_name} ${model_name_uncapitalize}) throws ServiceException {
        try{
            return ${model_name_uncapitalize}DAO.insert(${model_name_uncapitalize});
        }catch(DAOException e){
            throw new ServiceException("添加${model_title}失败", e);
        }
    }
</#if>

<#if updateByPrimaryKey==true>
    @Override
    public int modify(${model_name} ${model_name_uncapitalize}) throws ServiceException {
        try{
            return ${model_name_uncapitalize}DAO.update(${model_name_uncapitalize});
        }catch(DAOException e){
            throw new ServiceException("修改${model_title}失败", e);
        }
    }
</#if>

<#if deleteByPrimaryKey==true>
    @Override
    public int delete(${id_type} id) throws ServiceException {
        try{
            return ${model_name_uncapitalize}DAO.deleteById(id);
        }catch(DAOException e){
            throw new ServiceException("删除${model_title}失败", e);
        }
    }
</#if>

<#if selectByPrimaryKey==true>
    @Override
    public ${model_name} queryById(${id_type} id) throws ServiceException {
        try{
            return ${model_name_uncapitalize}DAO.selectById(id);
        }catch(DAOException e){
            throw new ServiceException(e.getMessage(), e);
        }
    }
</#if>

<#if selectByPageList==true>
    @Override
    public PageList<${model_name}> queryForPage(Map<String, Object> parameterObject, Integer pageNum, Integer pageSize) throws ServiceException {
        try{
            return ${model_name_uncapitalize}DAO.executeQueryForList(parameterObject, pageNum, pageSize);
        }catch(DAOException e){
            throw new ServiceException(e.getMessage(), e);
        }
    }
</#if>

<#if batchInsert==true>
    @Override
    public int batchAdd(List<${model_name}> ${model_name_uncapitalize}) throws ServiceException {
        try{
            if (CollectionUtils.isEmpty(${model_name_uncapitalize}))
                throw new ServiceException("添加列表为空");
            return ${model_name_uncapitalize}DAO.batchInsert(${model_name_uncapitalize});
        }catch(DAOException e){
            throw new ServiceException(e.getMessage(), e);
        }
    }
</#if>

<#if batchDelete==true>
    @Override
    public int batchDelete(List<${id_type}> ids) throws ServiceException {
        try{
            if (CollectionUtils.isEmpty(ids))
                throw new ServiceException("删除列表为空");
            return ${model_name_uncapitalize}DAO.batchDelete(ids);
        }catch(DAOException e){
            throw new ServiceException(e.getMessage(), e);
        }
    }
</#if>
}
