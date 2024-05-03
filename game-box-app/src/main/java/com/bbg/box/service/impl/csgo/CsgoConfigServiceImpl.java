package com.bbg.box.service.impl.csgo;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.bbg.model.csgo.CsgoConfig;
import com.bbg.box.mapper.csgo.CsgoConfigMapper;
import com.bbg.box.service.csgo.CsgoConfigService;
import org.springframework.stereotype.Service;

/**
 * 游戏配置 服务层实现。
 *
 * @author bbg
 * @since 2024-05-03
 */
@Service
public class CsgoConfigServiceImpl extends ServiceImpl<CsgoConfigMapper, CsgoConfig> implements CsgoConfigService {

}
