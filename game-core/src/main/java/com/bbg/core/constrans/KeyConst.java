package com.bbg.core.constrans;

public class KeyConst {

    // admin uid 指向 token
    public final static String ADMIN_TOKEN_UID = "admin::token::uid";
    // admin token
    public final static String ADMIN_INFO_TOKEN = "admin::info::token";
    // user uid 指向 token
    public final static String USER_TOKEN_UID = "user::token::uid";
    // user token
    public final static String USER_INFO_TOKEN = "user::info::token";
    // 不同用户类型的人数
    public final static String USER_TYPE_COUNT = "user::type::count";

    // 业务平台配置
    public final static String BIZ_CONFIG_NAME_ALIAS = "biz::config::nameAlias";
    // 游戏配置
    public final static String GAME_CONFIG_NAME_ALIAS = "game::config::nameAlias";
    // 字典标签缓存
    public final static String DICT_TAG = "dict::tag";
    // 箱子缓存
    public final static String BOX_ID = "box::id";
    // 所有机器人
    public final static String ROBOT_LIST = "robot::list";
    // 对战房间信息
    public final static String ROOM_INFO_ID = "battleRoom::info::roomId";
    // 对战房间信息列表
    public final static String ROOM_LIST_INFO = "battleRoom::list";
    // 对战房间信息列表-战斗模式
    public final static String ROOM_LIST_INFO_BATTLE_MODEL = "battleRoom::list::battleModel";
    // 撸房信息
    public final static String ROLL_INFO_ID = "roll::info::rollId";
    // 撸房信息列表
    public final static String ROLL_LIST_INFO = "roll::info::list";


    // 方法锁  同步数据
    public final static String METHOD_SYNC_DATA_LOCK = "method::syncData";
    // 方法锁  开盲盒
    public final static String METHOD_OPEN_BOX_LOCK = "method::openBox::uid";
    // 方法锁  追梦
    public final static String METHOD_DREAM_GOOD_LOCK = "method::dreamGood::uid";
    // 方法锁  创建对战房间
    public final static String METHOD_CREATE_ROOM_LOCK = "method::createRoom::uid";
    // 方法锁  加入对战房间
    public final static String METHOD_JOIN_ROOM_LOCK = "method::joinRoom::roomId";
    // 方法锁 加入撸房
    public final static String METHOD_JOIN_ROLL_LOCK = "method::joinRoll::rollId";

    public static String build(String prefix, String key) {
        return "%s::%s".formatted(prefix, key);
    }
}
