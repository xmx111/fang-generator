package com.ufo.${project_name}.center.web.${pkg_name};

import com.ufo.${project_name}.center.model.${model_name};
import com.ufo.${project_name}.center.service.${pkg_name}.${model_name}Service;
import com.ufo.${project_name}.common.service.BaseService;
import com.ufo.${project_name}.common.web.BaseTemplateAction;
import com.ufo.${project_name}.common.exceptions.ServiceException;
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
@RequestMapping("/${pkg_name}")
public class ${model_name}Action extends BaseTemplateAction<${model_name}, ${id_type}> {

    @Autowired
    ${model_name}Service ${model_name_uncapitalize}Service;

    @Override
    protected BaseService<${model_name}, ${id_type}> getService() {
        return ${model_name_uncapitalize}Service;
    }

    @RequestMapping("index.htm")
    public String index() throws ServiceException {
        return this.toView("${pkg_name}/${connect_name}-index");
    }

    @RequestMapping("operator.htm")
    public String operator(Long id, ModelMap map) throws ServiceException {
        if (id != null)
            map.put("data", getService().queryById(id));
        return this.toView("${pkg_name}/${connect_name}-operator");
    }

    @RequestMapping("query.json")
    @ResponseBody
    public Object query(Integer page, Integer rows) throws ServiceException {
        Map<String, Object> params = new HashMap<>();
        return gridPageResult(getService().queryForPage(params, page, rows));
    }

    @RequestMapping("save.do")
    @ResponseBody
    public Object save(${model_name} dto) throws ServiceException {
        if (dto.getId() == null)
            return add(dto);
        else
            return modify(dto);
    }

    @RequestMapping("delete.do")
    @ResponseBody
    public Object delete(Long id) throws ServiceException {
        return delete(id);
    }
}
