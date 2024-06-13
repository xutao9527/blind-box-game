package com.bbg.core.service.impl.biz;

import com.bbg.core.annotation.RedisCache;
import com.bbg.core.annotation.RedisClear;
import com.bbg.core.constrans.KeyConst;
import com.bbg.model.csgo.CsgoRobot;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.bbg.model.biz.BizChannel;
import com.bbg.core.mapper.biz.BizChannelMapper;
import com.bbg.core.service.biz.BizChannelService;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 渠道管理 服务层实现。
 *
 * @author bbg
 * @since 2024-06-13
 */
@Service
public class BizChannelServiceImpl extends ServiceImpl<BizChannelMapper, BizChannel> implements BizChannelService {

    @RedisCache(key = KeyConst.BIZ_CHANNEL_LIST)
    public Map<String,BizChannel> getChannelAsMap(){
        List<BizChannel> bizChannels = super.list(QueryWrapper.create().eq(CsgoRobot::getEnable,true));
        return bizChannels.stream().collect(Collectors.toMap(BizChannel::getChannelCode, Function.identity()));
    }

    @Override
    @RedisClear(key = KeyConst.BIZ_CHANNEL_LIST)
    public boolean save(BizChannel entity) {
        return super.save(entity);
    }

    @Override
    @RedisClear(key = KeyConst.BIZ_CHANNEL_LIST)
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }

    @Override
    @RedisClear(key = KeyConst.BIZ_CHANNEL_LIST)
    public boolean updateById(BizChannel entity) {
        return super.updateById(entity);
    }
}
