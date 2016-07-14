package com.ufo.${project_name}.center.web.${model_name_uncapitalize};

import com.ufo.${project_name}.center.model.${model_name};
import com.ufo.${project_name}.center.service.${model_name_uncapitalize}.${model_name}Service;
import com.ufo.${project_name}.common.service.BaseService;
import com.ufo.${project_name}.common.web.BaseTemplateAction;
import com.ufo.${project_name}.common.exceptions.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
public class ${model_name}Action extends BaseTemplateAction<${model_name}, ${id_type}> {

    @Autowired
    ${model_name}Service ${model_name_uncapitalize}Service;

    @Override
    protected BaseService<${model_name}, ${id_type}> getService() {
        return ${model_name_uncapitalize}Service;
    }

    @RequestMapping("index.htm")
    public String index() throws ServiceException {
        return this.toView("${model_name_uncapitalize}/${model_name_uncapitalize}Index");
    }

    @RequestMapping("queryForPage.json")
    @ResponseBody
    public Object queryForPage(Integer page, Integer rows) throws ServiceException {
        Map<String, Object> params = new HashMap<>();
        return gridResult(getService().queryForPage(params, page, rows));
    }

    @RequestMapping("add.do")
    @ResponseBody
    public Object add(${model_name} dto) throws ServiceException {
        return add(dto);
    }

    @RequestMapping("modify.do")
    @ResponseBody
    public Object modify(${model_name} dto) throws ServiceException {
        return modify(dto);
    }

    @RequestMapping("delete.do")
    @ResponseBody
    public Object delete(Long id) throws ServiceException {
        return delete(id);
    }
}
