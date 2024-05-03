package com.bbg.code.gen;

import com.bbg.code.gen.generator.CodeGenNormal;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.Arrays;

public class GenBox {
    public static String[] bizTables = {
            "biz_user",
            "biz_dict",
            "biz_dict_detail",
    };

    public static String[] csgoTables = {
            "csgo_config",
            "csgo_goods",
            "csgo_box",
            "csgo_box_goods",
    };

    @Test
    // 生成 admin -> Mapper Service ServiceImpl Controller
    public void generateCode(){
        String extPackagePath = Paths.get(System.getProperty("user.dir")).getParent()
                .resolve("game-box-app")
                .resolve("src")
                .resolve("main")
                .resolve("java")
                .toString();
        CodeGenNormal codeGenNormal = new CodeGenNormal();
        codeGenNormal.generateService(bizTables, "com.bbg.box", ".biz", extPackagePath);
        codeGenNormal.generateService(csgoTables, "com.bbg.box", ".csgo", extPackagePath);
        codeGenNormal.generateController(bizTables, "com.bbg.box", ".biz", extPackagePath, "/templates/enjoy/CustomAppController.tpl");
        codeGenNormal.generateController(csgoTables, "com.bbg.box", ".csgo", extPackagePath, "/templates/enjoy/CustomAppController.tpl");

    }
}
