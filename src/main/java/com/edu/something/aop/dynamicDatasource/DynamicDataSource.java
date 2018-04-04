package com.edu.something.aop.dynamicDatasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/**
 * @author Tangzhihao
 * @date 2018/1/31
 * 注解的形式切面编程,继承AbstractRoutingDataSource实现多数据源的动态切换
 */

public class DynamicDataSource extends AbstractRoutingDataSource{
    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.getDataSourceType();
    }

}
