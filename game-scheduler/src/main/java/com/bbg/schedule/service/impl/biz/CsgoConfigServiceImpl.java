package com.bbg.schedule.service.impl.biz;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.bbg.model.biz.CsgoConfig;
import com.bbg.schedule.mapper.biz.CsgoConfigMapper;
import com.bbg.schedule.service.biz.CsgoConfigService;
import org.springframework.stereotype.Service;

/**
 * 游戏配置 服务层实现。
 *
 * @author bbg
 * @since 2024-05-22
 */
@Service
public class CsgoConfigServiceImpl extends ServiceImpl<CsgoConfigMapper, CsgoConfig> implements CsgoConfigService {

}
