package com.bbg.core.constants;

public class KeyConst {


    // 字典标签缓存
    public final static String DICT_TAG = "dict::tag";
    // admin uid 指向 token
    public final static String ADMIN_TOKEN_UID = "admin::token::uid";
    // admin token
    public final static String ADMIN_INFO_TOKEN = "admin::info::token";
    // user uid 指向 token
    public final static String USER_TOKEN_UID = "user::token::uid";
    // user token
    public final static String USER_INFO_TOKEN = "user::info::token";

    // 方法锁  同步数据
    public final static String METHOD_SYNC_DATA_LOCK = "method::syncData";
    // 方法锁  开盲盒
    public final static String METHOD_OPEN_BOX_LOCK = "method::openBox";
    // 方法锁  追梦
    public final static String METHOD_DREAM_GOOD_LOCK = "method::dreamGood";

    public static String build(String prefix, String key) {
        return "%s::%s".formatted(prefix, key);
    }
}
