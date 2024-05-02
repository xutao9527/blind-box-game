package com.bbg.code.gen.generator.base;

import com.mybatisflex.codegen.config.PackageConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.nio.file.Paths;

public class CodeGenBase {
    protected DataSource getDatasource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/gbb?characterEncoding=utf-8&useInformationSchema=true");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        return dataSource;
    }

    protected void setPackageConfig(PackageConfig packageConfig, String basePackagePath, String extPackagePath) {
        // String entityPackagePath = basePackagePath.substring(0, basePackagePath.lastIndexOf('.'));
        packageConfig
                .setBasePackage(basePackagePath)
                // .setEntityPackage(entityPackagePath + ".model" + extPackagePath)
                .setEntityPackage("com.bbg" + ".model" + extPackagePath)
                .setMapperPackage(basePackagePath + ".mapper" + extPackagePath)
                .setServicePackage(basePackagePath + ".service" + extPackagePath)
                .setServiceImplPackage(basePackagePath + ".service.impl" + extPackagePath)
                .setControllerPackage(basePackagePath + ".controller" + extPackagePath);

    }

}
