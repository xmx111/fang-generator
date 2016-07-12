package com.ufo.${project_name}.center.dao.${pkgName};

import com.ufo.${project_name}.center.model.${model_name};
import com.ufo.${project_name}.common.exceptions.DAOException;
import com.ufo.${project_name}.common.mybatis.pagehelper.PageList;

import java.util.List;
import java.util.Map;

/***
 * ${model_title}数据库操作DAO
 *
 * @author generator
 * @created ${date?string("yyyy-MM-dd")}
 */
public interface ${model_name}DAO {

    <#if insert==true>
    /**
     * 新增
     * @param ${model_name_uncapitalize}
     * @return
     */
    int insert(${model_name} ${model_name_uncapitalize}) throws DAOException;
    </#if>

    <#if updateByPrimaryKey==true>
    /**
     * 修改
     * @param ${model_name_uncapitalize}
     * @return
     */
    int update(${model_name} ${model_name_uncapitalize}) throws DAOException;
    </#if>

    <#if deleteByPrimaryKey==true>
    /**
     * 根据id删除
     * @param id
     * @return
     */
    int deleteById(${id_type} id) throws DAOException;
    </#if>

    <#if selectByPrimaryKey==true>
    /**
     * 根据id查询对象
     * @param id
     * @return
     */
    ${model_name} selectById(${id_type} id) throws DAOException;
    </#if>

    <#if selectAll==true>
    /**
     * 查询所有对象列表
     * @return
     */
    List<${model_name}> selectAll() throws DAOException;
    </#if>

    <#if selectByParameters==true>
    /**
     * 根据参数查询对象列表
     * @param parameters
     * @return
     */
    List<${model_name}> selectByParameters(Map<String, Object> parameters) throws DAOException;
    </#if>

    <#if selectByParameters==true>
    /**
     * 分页查询
     * @param parameterObject
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageList<${model_name}> executeQueryForList(Map<String, Object> parameterObject, Integer pageNum, Integer pageSize) throws DAOException;
    </#if>

    <#if selectByParameters==true>
    /**
    * 分页查询
    * @param parameterObject
    * @param pageNum
    * @param pageSize
    * @return
    */
    PageList<${model_name}> executeSumQueryForList(Map<String, Object> parameterObject, Integer pageNum, Integer pageSize) throws DAOException;
    </#if>

    <#if batchInsert==true>
    /**
     * 批量新增
     * @param ${model_name_uncapitalize}
     * @return
     */
    int batchInsert(List<${model_name}> ${model_name_uncapitalize}) throws DAOException;
    </#if>

    <#if batchDelete==true>
    /**
     * 批量删除
     * @param ids
     * @return
     */
    int batchDelete(List<${id_type}> ids) throws DAOException;
    </#if>
}
