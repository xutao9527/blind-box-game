package com.bbg.code.gen.generator.base;

import com.mybatisflex.codegen.config.GlobalConfig;
import com.mybatisflex.codegen.config.PackageConfig;
import com.mybatisflex.codegen.entity.Table;
import com.mybatisflex.codegen.generator.IGenerator;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class CustomVueIndexGenerator implements IGenerator {

    private String templatePath = "/templates/enjoy/CustomVueIndex.tpl";

    @Override
    public String getTemplatePath() {
        return "/templates/enjoy/CustomVueIndex.tpl";
    }

    @Override
    public void setTemplatePath(String s) {
        templatePath = s;
    }

    @Override
    public void generate(Table table, GlobalConfig globalConfig) {
        Boolean overwrite = (Boolean) globalConfig.getCustomConfig("VueIndexOverwriteEnable");
        PackageConfig packageConfig = globalConfig.getPackageConfig();
        File vueFile = new File(packageConfig.getSourceDir() +
                File.separator + packageConfig.getEntityPackage() +
                File.separator + table.getName() +
                File.separator + "index.vue");

        if(overwrite!=null){
            if (!vueFile.exists() || overwrite) {
                Map<String, Object> params = new HashMap<>(4);
                params.put("table", table);
                params.put("entityClassName", table.buildEntityClassName());
                globalConfig.getTemplateConfig().getTemplate().generate(params, templatePath, vueFile);;
                System.out.println("IndexVue ---> " + vueFile);
            }
        }
    }
}
