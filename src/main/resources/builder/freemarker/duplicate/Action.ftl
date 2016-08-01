package com.ufo.${project_name}.center.web.${pkg_name};

import com.ufo.${project_name}.center.model.${model_name};
import com.ufo.${project_name}.center.service.${pkg_name}.${model_name}Service;
import com.ufo.${project_name}.common.exceptions.ServiceException;
import com.ufo.${project_name}.common.mybatis.pagehelper.PageList;
import com.ufo.${project_name}.common.web.BaseAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/***
 * ${model_title}控制器
 *
 * @author generator
 * @created ${date?string("yyyy-MM-dd")}
 */
@Controller
@RequestMapping("/${model_name_uncapitalize}")
public class ${model_name}Action extends BaseAction {

    @Autowired
    ${model_name}Service ${model_name_uncapitalize}Service;

    @RequestMapping("index.htm")
    public String index() {
        return this.toView("${pkg_name}/index");
    }

<#if insert==true>
    @RequestMapping("add.htm")
    public String addView() {
        return toView("${pkg_name}/operator");
    }
</#if>

<#if updateByPrimaryKey==true && selectByPrimaryKey==true>
    @RequestMapping("modify.htm")
    public String modifyView(Long id, ModelMap map) throws ServiceException {
        map.put("data", ${model_name_uncapitalize}Service.queryById(id));
        return toView("${pkg_name}/operator");
    }
</#if>

<#if selectByPageList==true>
    @RequestMapping("queryForPage.json")
    @ResponseBody
    public Object queryForPage(Integer page, Integer rows) throws ServiceException {
        Map<String, Object> params = new HashMap<>();
        PageList<${model_name}> pageList = ${model_name_uncapitalize}Service.queryForPage(params, page, rows);
        return buildDateGrid(pageList);
    }
</#if>

<#if insert==true>
    @RequestMapping("add.do")
    @ResponseBody
    public Object add(${model_name} dto) throws ServiceException {
        ${model_name_uncapitalize}Service.add(dto);
        return success(true);
    }
</#if>

<#if updateByPrimaryKey==true>
    @RequestMapping("modify.do")
    @ResponseBody
    public Object modify(${model_name} dto) throws ServiceException {
        ${model_name_uncapitalize}Service.modify(dto);
        return success(true);
    }
</#if>

<#if deleteByPrimaryKey==true>
    @RequestMapping("delete.do")
    @ResponseBody
    public Object delete(Long id) throws ServiceException {
        ${model_name_uncapitalize}Service.delete(id);
        return success(true);
    }
</#if>
}
