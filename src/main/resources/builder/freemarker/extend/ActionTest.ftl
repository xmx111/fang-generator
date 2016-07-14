package com.ufo.${project_name}.center.test.web.${model_name_uncapitalize};

import com.ufo.${project_name}.center.test.junit.AbstractWebTest;
import com.ufo.framework.util.UtilJson;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
* ${model_name}Action Tester.
*
* @author <Authors name>
    * @since <pre>${date?string("yyyy-MM-dd")}</pre>
    * @version 1.0
    */
    public class ${model_name}ActionTest extends AbstractWebTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
    *
    * Method: index()
    *
    */
    @Test
    public void testIndex() throws Exception {
        logger.info("测试用户管理首页");
        String html = mvc.perform(get("/${model_name_uncapitalize}/index.htm").with(apiSignRequestPostProcessor))
        .andReturn().getResponse().getContentAsString();
        System.out.println(html);
        logger.info("html:\n{}", UtilJson.writerWithDefaultPrettyPrinter(html));
    }

    /**
    *
    * Method: findByPage(Integer pageNum, Integer pageSize)
    *
    */
    @Test
    public void testFindByPage() throws Exception {
        //TODO: Test goes here...
    }

    /**
    *
    * Method: update(${project_name} dto)
    *
    */
    @Test
    public void testUpdate() throws Exception {
        //TODO: Test goes here...
    }

    /**
    *
    * Method: save(${project_name} dto)
    *
    */
    @Test
    public void testSave() throws Exception {
        //TODO: Test goes here...
    }

    /**
    *
    * Method: delete(${id_type} id)
    *
    */
    @Test
    public void testDelete() throws Exception {
        //TODO: Test goes here...
    }


}
