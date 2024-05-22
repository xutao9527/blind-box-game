package com.bbg.code.gen.generator;

import com.bbg.code.gen.generator.base.CodeGenBase;
import com.mybatisflex.codegen.Generator;
import com.mybatisflex.codegen.config.EntityConfig;
import com.mybatisflex.codegen.config.GlobalConfig;
import com.mybatisflex.codegen.generator.GeneratorFactory;

public class CodeGenNormal extends CodeGenBase {

    public void generateService(String[] generateTables, String basePackagePath, String extPackagePath, String sourceDir) {
        GlobalConfig globalConfig = new GlobalConfig();
        // 生成源码目录
        if (sourceDir != null && !sourceDir.trim().isEmpty()) {
            globalConfig.getPackageConfig()
                    .setSourceDir(sourceDir);
        }
        // 生成表列表
        globalConfig.getStrategyConfig()
                .setGenerateTable(generateTables);
        // 设置包路径
        this.setPackageConfig(globalConfig.getPackageConfig(), basePackagePath, extPackagePath);
        // 注释
        globalConfig.getJavadocConfig()
                .setAuthor("bbg");

        //entity设置
        globalConfig.getEntityConfig()
                .setWithSwagger(true)
                .setSwaggerVersion(EntityConfig.SwaggerVersion.DOC);
        // Mapper
        globalConfig.enableMapper()
                .setOverwriteEnable(false);  //是否覆盖
        // Service
        globalConfig.enableService()
                .setOverwriteEnable(false);  //是否覆盖
        // ServiceImpl
        globalConfig.enableServiceImpl()
                .setOverwriteEnable(false);  //是否覆盖

        globalConfig.setCustomConfig("superPackagePath",basePackagePath + ".base.controller" + extPackagePath);
        // 执行
        Generator generator = new Generator(this.getDatasource(), globalConfig);
        generator.generate();
    }

    public void generateController(String[] generateTables, String basePackagePath, String extPackagePath, String sourceDir ,String templatePath) {
        if (templatePath != null && !templatePath.isEmpty()) {
            GeneratorFactory.getGenerator("controller").setTemplatePath(templatePath);
        } else {
            GeneratorFactory.getGenerator("controller").setTemplatePath("/templates/enjoy/controller.tpl");
        }

        GlobalConfig globalConfig = new GlobalConfig();
        // 生成源码目录
        if (sourceDir != null && !sourceDir.trim().isEmpty()) {
            globalConfig.getPackageConfig()
                    .setSourceDir(sourceDir);
        }
        // 生成表列表
        globalConfig.getStrategyConfig()
                .setGenerateTable(generateTables);
        // 设置包路径
        this.setPackageConfig(globalConfig.getPackageConfig(), basePackagePath, extPackagePath);
        // 注释
        globalConfig.getJavadocConfig()
                .setAuthor("bbg");

        //entity设置
        globalConfig.getEntityConfig()
                .setWithSwagger(true)
                .setSwaggerVersion(EntityConfig.SwaggerVersion.DOC);

        // Controller
        globalConfig.enableController()
                .setOverwriteEnable(false);  //是否覆盖

        globalConfig.setCustomConfig("superPackagePath",basePackagePath + ".base.controller" + extPackagePath);
        // 执行
        Generator generator = new Generator(this.getDatasource(), globalConfig);
        generator.generate();
    }

    public void generateBaseController(String[] generateTables, String basePackagePath, String extPackagePath, String sourceDir ,String templatePath) {
        if (templatePath != null && !templatePath.isEmpty()) {
            GeneratorFactory.getGenerator("controller").setTemplatePath(templatePath);
        } else {
            GeneratorFactory.getGenerator("controller").setTemplatePath("/templates/enjoy/controller.tpl");
        }

        GlobalConfig globalConfig = new GlobalConfig();
        // 生成源码目录
        if (sourceDir != null && !sourceDir.trim().isEmpty()) {
            globalConfig.getPackageConfig()
                    .setSourceDir(sourceDir);
        }
        // 生成表列表
        globalConfig.getStrategyConfig()
                .setGenerateTable(generateTables);
        // 设置包路径
        globalConfig.getPackageConfig()
                .setServicePackage("com.bbg.core" + ".service" + extPackagePath)
                .setEntityPackage("com.bbg" + ".model" + extPackagePath)
                .setControllerPackage(basePackagePath + ".base.controller" + extPackagePath);
        // this.setPackageConfig(globalConfig.getPackageConfig(), basePackagePath, extPackagePath);
        // 注释
        globalConfig.getJavadocConfig()
                .setAuthor("bbg");

        //entity设置
        globalConfig.getEntityConfig()
                .setWithSwagger(true)
                .setSwaggerVersion(EntityConfig.SwaggerVersion.DOC);
        // Controller
        globalConfig.enableController()
                .setClassPrefix("Base")
                .setOverwriteEnable(false);  //是否覆盖
        // 执行
        Generator generator = new Generator(this.getDatasource(), globalConfig);
        generator.generate();
    }
}
