package com.bbg.code.gen.generator.base;

import com.mybatisflex.codegen.config.GlobalConfig;
import com.mybatisflex.codegen.config.PackageConfig;
import com.mybatisflex.codegen.entity.Column;
import com.mybatisflex.codegen.entity.Table;
import com.mybatisflex.codegen.generator.IGenerator;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomVueListGenerator implements IGenerator {

    private String templatePath = "/templates/enjoy/CustomVueList.tpl";

    @Override
    public String getTemplatePath() {
        return "/templates/enjoy/CustomVueList.tpl";
    }

    @Override
    public void setTemplatePath(String s) {
        templatePath = s;
    }

    @Override
    public void generate(Table table, GlobalConfig globalConfig) {
        Boolean overwrite = (Boolean) globalConfig.getCustomConfig("VueListOverwriteEnable");
        PackageConfig packageConfig = globalConfig.getPackageConfig();
        File vueFile = new File(packageConfig.getSourceDir() +
                File.separator + packageConfig.getEntityPackage() +
                File.separator + table.getName() +
                File.separator + "list.vue");

        if(overwrite!=null){
            if (!vueFile.exists() || overwrite) {
                Map<String, Object> params = new HashMap<>(4);
                List<Column> columns = table.getColumns().stream().peek(c -> {
                    String comment = c.getComment().split(":")[0];
                    c.setComment(comment);
                }).toList();
                table.setColumns(columns);
                params.put("table", table);
                params.put("entityClassName", table.buildEntityClassName());
                globalConfig.getTemplateConfig().getTemplate().generate(params, templatePath, vueFile);
                System.out.println("ListVue ---> " + vueFile);
            }
        }
    }
}
