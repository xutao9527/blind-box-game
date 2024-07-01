package com.bbg.code.gen;

import com.bbg.code.gen.generator.CodeGenEntity;
import com.bbg.code.gen.generator.CodeGenNormal;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

public class GenCore {
    public static String[] sysTables = {
            "sys_tenant",//租户
            "sys_menu",//菜单
            "sys_role",//角色
            "sys_role_menu",//角色菜单
            "sys_user",//用户
            "sys_user_role",//用户角色
    };

    public static String[] bizTables = {
            "biz_user",//账号
            "biz_dict",//业务字典
            "biz_dict_detail",//字典详情
            "biz_data",//业务数据
            "biz_config",//平台配置
            "biz_channel",//渠道
            "biz_pay_platform",//支付平台
            "biz_pay_order",//支付订单
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
            "csgo_robot", //机器人
            "csgo_roll", //roll房
            "csgo_roll_user",//roll房的用户
            "csgo_roll_good",//roll房的商品
            "csgo_open_box_log",// 开箱记录
            "csgo_dream_good_log"// 追梦记录
    };

    @Test
    // 生成 model -> Entity
    public void generateEntity(){
        CodeGenEntity codeGenEntity = new CodeGenEntity();
        codeGenEntity.generate(bizTables, "com.bbg", ".biz");
        codeGenEntity.generate(sysTables, "com.bbg", ".sys");
        codeGenEntity.generate(csgoTables, "com.bbg", ".csgo");
        // codeGenEntity.generate(new String[]{"sys_menu"}, "com.bbg", ".sys");
    }

    @Test
    // 生成 core -> Mapper Service ServiceImpl
    public void generateCode(){
        String extPackagePath = Paths.get(System.getProperty("user.dir")).getParent()
                .resolve("game-core")
                .resolve("src")
                .resolve("main")
                .resolve("java")
                .toString();
        CodeGenNormal codeGenNormal = new CodeGenNormal();
        codeGenNormal.generateService(bizTables, "com.bbg.core", ".biz", extPackagePath);
        codeGenNormal.generateService(csgoTables, "com.bbg.core", ".csgo", extPackagePath);
        codeGenNormal.generateService(sysTables, "com.bbg.core", ".sys", extPackagePath);
    }
}
