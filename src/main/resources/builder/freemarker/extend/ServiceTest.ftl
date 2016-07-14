package com.ufo.${project_name}.center.test.service.${model_name_uncapitalize};

import com.ufo.${project_name}.center.dao.${model_name_uncapitalize}.${model_name}DAO;
import com.ufo.${project_name}.center.test.junit.AbstractTest;
import com.ufo.${project_name}.center.model.${model_name};
import com.ufo.${project_name}.common.mybatis.pagehelper.PageList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * ${model_name}Service Tester.
 *
 * @author <Authors name>
 * @since <pre>${date?string("yyyy-MM-dd")}</pre>
 * @version 1.0
 */
public class ${model_name}ServiceTest extends AbstractTest {

    @Autowired
    ${model_name}DAO ${model_name_uncapitalize}DAO;

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testAdd(){
        // TODO: Test goes here...
    }

    @Test
    public void testModify(){
        // TODO: Test goes here...
    }

    @Test
    public void testDelete(){
        // TODO: Test goes here...
    }

    @Test
    public void testQueryById(){
        // TODO: Test goes here...
    }

    @Test
    public void testQueryForPage(){
        // TODO: Test goes here...
    }
}
