package com.bbg.core.constants;

public class KeyConst {



    // admin uid 指向 token
    public final static String ADMIN_TOKEN_UID = "admin::token::uid";
    // admin token
    public final static String ADMIN_INFO_TOKEN = "admin::info::token";
    // user uid 指向 token
    public final static String USER_TOKEN_UID = "user::token::uid";
    // user token
    public final static String USER_INFO_TOKEN = "user::info::token";

    // 字典标签缓存
    public final static String DICT_TAG = "dict::tag";
    // 箱子缓存
    public final static String BOX_ID = "box::id";
    // 所有机器人
    public final static String ROBOT_LIST = "robot::list";

    // 方法锁  同步数据
    public final static String METHOD_SYNC_DATA_LOCK = "method::syncData";
    // 方法锁  开盲盒
    public final static String METHOD_OPEN_BOX_LOCK = "method::openBox";
    // 方法锁  追梦
    public final static String METHOD_DREAM_GOOD_LOCK = "method::dreamGood";
    // 方法锁  创建房间
    public final static String METHOD_CREATE_ROOM_LOCK = "method::CreateRoom";

    public static String build(String prefix, String key) {
        return "%s::%s".formatted(prefix, key);
    }
}
