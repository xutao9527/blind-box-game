package com.bbg.code.gen;

import com.bbg.code.gen.generator.*;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.Arrays;

public class GenAdmin {
    public static String[] sysTables = {
            "sys_menu",// 菜单
            "sys_role",// 角色
            "sys_role_menu",// 角色菜单
            "sys_user",// 用户
            "sys_user_role",// 用户角色
    };

    public static String[] bizTables = {
            "biz_user",// 账号
            "biz_dict",// 业务字典
            "biz_dict_detail",// 字典详情
            "biz_data",// 业务数据
            "biz_config",//平台配置
            "biz_channel",//渠道
    };

    public static String[] csgoTables = {
            "csgo_user_info",// 用户信息
            "csgo_config",// 游戏配置
            "csgo_goods",// 商品信息
            "csgo_box",// 盲盒
            "csgo_box_goods",// 盲盒商品
            "csgo_storehouse",// 装备仓库
            "csgo_capital_record",// 资金流水
            "csgo_battle_room",// 对战房间
            "csgo_battle_room_box",// 房间的箱子
            "csgo_battle_room_user",// 房间的用户
            "csgo_battle_room_good",// 房间获取装备记录
            "csgo_robot", // 机器人
            "csgo_roll", // roll房
            "csgo_roll_user",// roll房的用户
            "csgo_roll_good",// roll房的商品
            "csgo_open_box_log",// 开箱记录
            "csgo_dream_good_log"// 追梦记录
    };


    @Test
    // 生成 admin -> Controller
    public void generateCode() {
        String extPackagePath = Paths.get(System.getProperty("user.dir")).getParent()
                .resolve("game-admin")
                .resolve("src")
                .resolve("main")
                .resolve("java")
                .toString();
        CodeGenNormal codeGenNormal = new CodeGenNormal();
        sysTables = Arrays.stream(sysTables).filter(
                t -> !(t.equals("sys_role_menu") || t.equals("sys_user_role"))
        ).toList().toArray(new String[0]);
        codeGenNormal.generateBaseController(bizTables, "com.bbg.admin", ".biz", extPackagePath, "/templates/enjoy/CustomAdminBaseController.tpl");
        codeGenNormal.generateBaseController(csgoTables, "com.bbg.admin", ".csgo", extPackagePath, "/templates/enjoy/CustomAdminBaseController.tpl");
        codeGenNormal.generateBaseController(sysTables, "com.bbg.admin", ".sys", extPackagePath, "/templates/enjoy/CustomAdminBaseController.tpl");
        codeGenNormal.generateController(bizTables, "com.bbg.admin", ".biz", extPackagePath, "/templates/enjoy/CustomAdminController.tpl");
        codeGenNormal.generateController(csgoTables, "com.bbg.admin", ".csgo", extPackagePath, "/templates/enjoy/CustomAdminController.tpl");
        codeGenNormal.generateController(sysTables, "com.bbg.admin", ".sys", extPackagePath, "/templates/enjoy/CustomAdminController.tpl");

    }

    @Test
    // 生成 vue 页面
    public void generatePage() {
        String sourceDir = Paths.get(System.getProperty("user.dir")).getParent()
                .resolve("game-admin-web")
                .resolve("src")
                .resolve("views")
                .toString();
        sysTables = Arrays.stream(sysTables).filter(
                t -> (!(t.equals("sys_role_menu") || t.equals("sys_user_role")))
        ).toList().toArray(new String[0]);
        bizTables = Arrays.stream(bizTables).filter(
                t -> (!(t.equals("biz_dict_detail")))
        ).toList().toArray(new String[0]);
        CodeGenVue codeGenVue = new CodeGenVue();
        codeGenVue.generate(bizTables, sourceDir, "biz", false);
        codeGenVue.generate(sysTables, sourceDir, "sys", false);
        codeGenVue.generate(csgoTables, sourceDir, "csgo", false);
    }

    @Test
    // 生成 vue 单个页面
    public void generateOnePage() {
        String sourceDir = Paths.get(System.getProperty("user.dir")).getParent()
                .resolve("game-admin-web")
                .resolve("src")
                .resolve("views")
                .toString();
        CodeGenVue codeGenVue = new CodeGenVue();
        codeGenVue.generate(new String[]{"biz_dict_detail"}, sourceDir, "biz", true);
    }
}
