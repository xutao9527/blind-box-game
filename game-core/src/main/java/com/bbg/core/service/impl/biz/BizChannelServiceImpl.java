package com.bbg.core.service.impl.biz;

import cn.hutool.core.codec.Base58;
import cn.hutool.core.convert.Convert;
import com.bbg.core.annotation.RedisCache;
import com.bbg.core.annotation.RedisClear;
import com.bbg.core.constrans.KeyConst;
import com.bbg.core.mapper.biz.BizUserMapper;
import com.bbg.core.service.biz.BizUserService;
import com.bbg.core.utils.IdTool;
import com.bbg.model.biz.BizUser;
import com.bbg.model.csgo.CsgoRobot;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.bbg.model.biz.BizChannel;
import com.bbg.core.mapper.biz.BizChannelMapper;
import com.bbg.core.service.biz.BizChannelService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.Serializable;
import java.util.*;
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
    @Lazy
    @Autowired
    private BizChannelService selfProxy;

    @RedisCache(key = KeyConst.BIZ_CHANNEL_LIST)
    public Map<String, BizChannel> getChannelAsMap() {
        List<BizChannel> bizChannels = super.list(QueryWrapper.create().eq(BizChannel::getEnable, true));
        return bizChannels.stream().collect(Collectors.toMap(BizChannel::getChannelDomain, Function.identity()));
    }

    @Override
    @RedisClear(key = KeyConst.BIZ_CHANNEL_LIST)
    public boolean save(BizChannel entity) {
        entity.setId(IdTool.nextId());
        entity.setChannelCode(Base58.encode(Convert.longToBytes(entity.getId())));
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
        entity.setChannelCode(null);
        return super.updateById(entity);
    }

    public Map<String,String> getChannelCode() {
        Map<String,String> map = new HashMap<>();
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        // 优先使用请求头中的promoCode,查询所属用户的渠道码
        String promoCode = request.getHeader("promoCode");
        if(promoCode != null) {
            QueryWrapper queryWrapper = QueryWrapper.create().from(BizUser.class).eq(BizUser::getPromoCode, promoCode);
            BizUser bizUser = getMapper().selectOneByQueryAs(queryWrapper, BizUser.class);
            if(bizUser != null) {
                map.put("channelCode", bizUser.getChannelCode());
                map.put("invitationCode", bizUser.getPromoCode());
                return map;
            }
        }
        // 从请求头中获取域名，查询渠道码
        String requestDomain = null;
        Enumeration<String> headers = request.getHeaders("X-Forwarded-Host");
        if (headers.hasMoreElements()) {
            requestDomain = headers.nextElement();
        }
        Map<String, BizChannel> channelAsMap = selfProxy.getChannelAsMap();
        BizChannel bizChannel = channelAsMap.get(requestDomain);
        if(bizChannel != null) {
            map.put("channelCode", bizChannel.getChannelCode());
        }
        return map;
    }

}
