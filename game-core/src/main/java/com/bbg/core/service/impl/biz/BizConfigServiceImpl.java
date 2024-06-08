package com.bbg.core.service.impl.biz;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.bbg.model.biz.BizConfig;
import com.bbg.core.mapper.biz.BizConfigMapper;
import com.bbg.core.service.biz.BizConfigService;
import org.springframework.stereotype.Service;

/**
 * 平台配置 服务层实现。
 *
 * @author bbg
 * @since 2024-06-08
 */
@Service
public class BizConfigServiceImpl extends ServiceImpl<BizConfigMapper, BizConfig> implements BizConfigService {

}
