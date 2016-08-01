package com.ufo.${project_name}.center.service.${pkg_name};

import com.ufo.${project_name}.center.model.${model_name};
import com.ufo.${project_name}.common.exceptions.ServiceException;
import com.ufo.${project_name}.common.mybatis.pagehelper.PageList;

import java.util.List;
import java.util.Map;

/***
 * ${model_title}服务
 *
 * @author generator
 * @created ${date?string("yyyy-MM-dd")}
 */
public interface ${model_name}Service {

<#if insert==true>
    int add(${model_name} ${model_name_uncapitalize}) throws ServiceException;
</#if>

<#if updateByPrimaryKey==true>
    int modify(${model_name} ${model_name_uncapitalize}) throws ServiceException;
</#if>

<#if deleteByPrimaryKey==true>
    int delete(${id_type} id) throws ServiceException;
</#if>

<#if selectByPrimaryKey==true>
    ${model_name} queryById(${id_type} id) throws ServiceException;
</#if>

<#if selectByParameters==true>
    PageList<${model_name}> queryForPage(Map<String, Object> parameterObject, Integer pageNum, Integer pageSize) throws ServiceException;
</#if>

<#if batchInsert==true>
    int batchAdd(List<${model_name}> ${model_name_uncapitalize}) throws ServiceException;
</#if>

<#if batchDelete==true>
    int batchDelete(List<${id_type}> ids) throws ServiceException;
</#if>
}
