package com.bbg.code.gen.generator;

import com.bbg.code.gen.generator.base.*;
import com.bbg.model.base.BaseModel;
import com.mybatisflex.codegen.Generator;
import com.mybatisflex.codegen.config.EntityConfig;
import com.mybatisflex.codegen.config.GlobalConfig;
import com.mybatisflex.codegen.generator.GeneratorFactory;


public class CodeGenVue extends CodeGenBase {

    public void generate(String[] generateTables, String sourcePath,String extPath,boolean isOverwrite) {
        this.generateImpl(generateTables, sourcePath,extPath,isOverwrite);
    }

    public void generateImpl(String[] generateTables, String sourceDir,String extPath,boolean isOverwrite) {
        GeneratorFactory.registerGenerator("VueIndex", new CustomVueIndexGenerator());
        GeneratorFactory.registerGenerator("VueList", new CustomVueListGenerator());
        GeneratorFactory.registerGenerator("VueEdit", new CustomVueEditGenerator());
        GlobalConfig globalConfig = new GlobalConfig();

        globalConfig.setCustomConfig("VueIndexOverwriteEnable", isOverwrite);
        globalConfig.setCustomConfig("VueListOverwriteEnable", isOverwrite);
        globalConfig.setCustomConfig("VueEditOverwriteEnable", isOverwrite);

        globalConfig.getStrategyConfig()
                .setGenerateTable(generateTables);
        // 生成源码目录
        if (sourceDir != null && !sourceDir.trim().isEmpty()) {
            globalConfig.getPackageConfig()
                    .setSourceDir(sourceDir);
        }
        // 生成表列表
        globalConfig.getStrategyConfig()
                .setGenerateTable(generateTables);
        // 设置包路径
        globalConfig.getPackageConfig().setEntityPackage(extPath);
        // 执行
        Generator generator = new Generator(this.getDatasource(), globalConfig);
        generator.generate();
    }
}
