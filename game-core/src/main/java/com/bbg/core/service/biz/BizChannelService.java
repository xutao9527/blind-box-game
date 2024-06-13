package com.bbg.core.service.biz;

import com.mybatisflex.core.service.IService;
import com.bbg.model.biz.BizChannel;

import java.util.Map;

/**
 * 渠道管理 服务层。
 *
 * @author bbg
 * @since 2024-06-13
 */
public interface BizChannelService extends IService<BizChannel> {

    Map<String, BizChannel> getChannelAsMap()
}
