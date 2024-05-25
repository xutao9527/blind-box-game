package com.bbg.code.gen;

import com.bbg.code.gen.generator.CodeGenNormal;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.Arrays;

public class GenBox {
    public static String[] bizTables = {
            "biz_user",//账号
            "biz_dict",//业务字典
            "biz_dict_detail",//字典详情
    };

    public static String[] csgoTables = {
            "csgo_user_info",//用户信息
            "csgo_config",//游戏配置
            "csgo_goods",//商品信息
            "csgo_box",//盲盒
            "csgo_box_goods",//盲盒商品
            "csgo_storehouse",//装备仓库
            "csgo_capital_record",//资金流水
            "csgo_battle_room",//对战房间
            "csgo_battle_room_box",//房间的箱子
            "csgo_battle_room_user",//房间的用户
            "csgo_battle_room_good",// 房间获取装备记录
            "csgo_robot", //机器人
            "csgo_roll", //roll房
            "csgo_roll_user",//roll房的用户
            "csgo_roll_good",//roll房的商品
            "csgo_open_box_log",// 开箱记录
            "csgo_dream_good_log"// 追梦记录
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
        // codeGenNormal.generateController(bizTables, "com.bbg.box", ".biz", extPackagePath, "/templates/enjoy/CustomAppController.tpl");
        // codeGenNormal.generateController(csgoTables, "com.bbg.box", ".csgo", extPackagePath, "/templates/enjoy/CustomAppController.tpl");
        codeGenNormal.generateController(new String[]{"csgo_robot_test"}, "com.bbg.box", ".csgo", extPackagePath, "/templates/enjoy/CustomAppController.tpl");
    }
}
