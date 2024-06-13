package com.bbg.core.service.impl.biz;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.bbg.model.biz.BizChannel;
import com.bbg.core.mapper.biz.BizChannelMapper;
import com.bbg.core.service.biz.BizChannelService;
import org.springframework.stereotype.Service;

/**
 * 渠道管理 服务层实现。
 *
 * @author bbg
 * @since 2024-06-13
 */
@Service
public class BizChannelServiceImpl extends ServiceImpl<BizChannelMapper, BizChannel> implements BizChannelService {

}
