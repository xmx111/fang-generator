package com.ufo.${project_name}.center.test.service.${pkg_name};

import com.ufo.${project_name}.center.dao.${pkg_name}.${model_name}DAO;
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

<#if insert==true>
    @Test
    public void testAdd(){
        // TODO: Test goes here...
    }
</#if>

<#if updateByPrimaryKey==true>
    @Test
    public void testModify(){
        // TODO: Test goes here...
    }
</#if>

<#if deleteByPrimaryKey==true>
    @Test
    public void testDelete(){
        // TODO: Test goes here...
    }
</#if>

<#if selectByPrimaryKey==true>
    @Test
    public void testQueryById(){
        // TODO: Test goes here...
    }
</#if>

<#if selectByParameters==true>
    @Test
    public void testQueryForPage(){
        // TODO: Test goes here...
    }
</#if>
}
