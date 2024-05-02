package com.bbg.code.gen.generator.base;

import com.mybatisflex.codegen.config.GlobalConfig;
import com.mybatisflex.codegen.config.PackageConfig;
import com.mybatisflex.codegen.entity.Table;
import com.mybatisflex.codegen.generator.IGenerator;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class CustomEntityGenerator implements IGenerator {

    private String templatePath = "/templates/enjoy/CustomEntity.tpl";

    @Override
    public String getTemplatePath() {
        return "/templates/enjoy/CustomEntity.tpl";
    }

    @Override
    public void setTemplatePath(String s) {
        templatePath = s;
    }

    @Override
    public void generate(Table table, GlobalConfig globalConfig) {
        PackageConfig packageConfig = globalConfig.getPackageConfig();

        String entityPackagePath = packageConfig.getEntityPackage().replace(".", "/");
        File entityJavaFile = new File(packageConfig.getSourceDir(), entityPackagePath + "/" +
                table.buildEntityClassName() + ".java");

        Boolean overwrite = (Boolean) globalConfig.getCustomConfig("EntityImplOverwriteEnable");
        if(overwrite!=null){
            if (!entityJavaFile.exists() || overwrite) {
                Map<String, Object> params = new HashMap<>(4);
                params.put("table", table);
                params.put("entityPackageName", packageConfig.getEntityPackage());
                params.put("entityClassName", table.buildEntityClassName());
                params.put("packageConfig", packageConfig);
                params.put("javadocConfig", globalConfig.getJavadocConfig());
                params.put("isBase", false);
                globalConfig.getTemplateConfig().getTemplate().generate(params, templatePath, entityJavaFile);
                System.out.println("EntityImpl ---> " + entityJavaFile);
            }
        }
    }
}
