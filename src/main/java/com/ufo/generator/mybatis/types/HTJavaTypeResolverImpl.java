package com.ufo.generator.mybatis.types;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;

import java.sql.Types;

/***
 * java类型处理器
 *
 * @author hekang
 * @created 2016/1/18
 */
public class HTJavaTypeResolverImpl extends JavaTypeResolverDefaultImpl {

    public HTJavaTypeResolverImpl(){
        super();
        // tinyint转为Integer
        typeMap.put(Types.TINYINT, new JdbcTypeInformation("TINYINT", //$NON-NLS-1$
                new FullyQualifiedJavaType(Integer.class.getName())));
    }
}
