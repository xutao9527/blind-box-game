package com.bbg.core.constrans;

import com.bbg.core.utils.TenantUtil;

public class KeyConst {
    // 租户缓存
    public final static String TENANT_ID = "tenant::id";
    // 租户缓存
    public final static String TENANT_CODE = "tenant::code";
    // 所有租户缓存
    public final static String TENANT_LIST = "tenant::list";
    // admin uid 指向 token
    public final static String ADMIN_TOKEN_UID = "admin::token::uid";
    // admin token 指向 管理员信息
    public final static String ADMIN_INFO_TOKEN = "admin::info::token";
    // admin token 指向 管理员权限
    public final static String ADMIN_PERMISSION_TOKEN = "admin::permission::token";
    // user uid 指向 token
    public final static String USER_TOKEN_UID = "user::token::uid";
    // user token
    public final static String USER_INFO_TOKEN = "user::info::token";
    // 不同用户类型的人数
    public final static String USER_TYPE_COUNT = "user::type::count";                           //需要租户标识 tenantFlag = true
    // 用户短信验证码
    public final static String USER_SMS_CODE = "user::sms::code";                               //需要租户标识 tenantFlag = true

    // 业务平台配置
    public final static String BIZ_CONFIG_NAME_ALIAS = "biz::config::nameAlias";                //需要租户标识 tenantFlag = true
    // 业务渠道列表
    public final static String BIZ_CHANNEL_LIST = "biz::channel::list";                         //需要租户标识 tenantFlag = true

    // 游戏配置
    public final static String GAME_CONFIG_NAME_ALIAS = "game::config::nameAlias";              //需要租户标识 tenantFlag = true
    // 字典标签缓存
    public final static String DICT_TAG = "dict::tag";
    // 箱子缓存
    public final static String BOX_ID = "box::id";
    // 所有机器人
    public final static String ROBOT_LIST = "robot::list";                                      //需要租户标识 tenantFlag = true
    // 对战房间信息
    public final static String ROOM_INFO_ID = "battleRoom::info::roomId";
    // 对战房间信息列表 ,对战模式:0=所有,1=欧皇,2=非酋
    public final static String ROOM_LIST_INFO = "battleRoom::list";                             //需要租户标识 tenantFlag = true
    // 撸房信息
    public final static String ROLL_INFO_ID = "roll::info::rollId";
    // 撸房信息列表
    public final static String ROLL_LIST_INFO = "roll::info::list";                             //需要租户标识 tenantFlag = true


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

    public static String build(String prefix, String key, boolean tenantFlag) {
        if (tenantFlag) {
            Long tenantId = TenantUtil.getTenantId();
            if (key == null || key.isEmpty()) {
                return "%s::%s".formatted(prefix, tenantId);
            }
            return "%s::%s::%s".formatted(prefix, tenantId, key);
        } else {
            return build(prefix, key);
        }
    }

    public static String build(String prefix, String key) {
        if (key == null || key.isEmpty()) {
            return prefix;
        }
        return "%s::%s".formatted(prefix, key);
    }
}
