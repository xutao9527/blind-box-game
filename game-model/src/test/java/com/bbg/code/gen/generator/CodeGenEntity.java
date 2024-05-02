package com.bbg.code.gen.generator;

import com.bbg.code.gen.generator.base.CodeGenBase;
import com.bbg.code.gen.generator.base.CustomEntityGenerator;
import com.bbg.model.base.BaseModel;
import com.mybatisflex.codegen.Generator;
import com.mybatisflex.codegen.config.EntityConfig;
import com.mybatisflex.codegen.config.GlobalConfig;

import com.mybatisflex.codegen.generator.GeneratorFactory;


public class CodeGenEntity extends CodeGenBase {

    public void generate(String[] generateTables, String basePackagePath, String extPackagePath){
        this.generateBase(generateTables, basePackagePath, ".record");
        this.generateImpl (generateTables, basePackagePath, extPackagePath);
    }

    public void generateBase(String[] generateTables, String basePackagePath, String extPackagePath) {
        GlobalConfig globalConfig = new GlobalConfig();

        globalConfig.getStrategyConfig()
                .setGenerateTable(generateTables);

        globalConfig.getPackageConfig().setEntityPackage(basePackagePath + ".model" + extPackagePath);

        globalConfig.getJavadocConfig()
                .setAuthor("bbg");

        globalConfig.enableEntity()
                .setClassSuffix("Record")
                .setSuperClass(BaseModel.class)
                .setOverwriteEnable(true)   //是否覆盖
                .setWithLombok(true)
                // .setWithActiveRecord(true)
                .setWithSwagger(true)
                .setSwaggerVersion(EntityConfig.SwaggerVersion.DOC)
                .setJdkVersion(17);


        Generator generator = new Generator(this.getDatasource(), globalConfig);
        generator.generate();
    }

    public void generateImpl(String[] generateTables, String basePackagePath, String extPackagePath) {
        GeneratorFactory.registerGenerator("EntityImpl", new CustomEntityGenerator());
        GlobalConfig globalConfig = new GlobalConfig();

        globalConfig.setCustomConfig("EntityImplOverwriteEnable", false);

        globalConfig.getStrategyConfig()
                .setGenerateTable(generateTables);

        globalConfig.getPackageConfig().setEntityPackage(basePackagePath + ".model" + extPackagePath);

        globalConfig.getJavadocConfig()
                .setAuthor("bbg");

        Generator generator = new Generator(this.getDatasource(), globalConfig);
        generator.generate();
    }
}
