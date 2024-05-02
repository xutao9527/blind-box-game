package com.bbg.admin.service.impl.csgo;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.bbg.model.csgo.CsgoGoods;
import com.bbg.admin.mapper.csgo.CsgoGoodsMapper;
import com.bbg.admin.service.csgo.CsgoGoodsService;
import org.springframework.stereotype.Service;

/**
 * Csgo商品表 服务层实现。
 *
 * @author bbg
 * @since 2024-04-29
 */
@Service
public class CsgoGoodsServiceImpl extends ServiceImpl<CsgoGoodsMapper, CsgoGoods> implements CsgoGoodsService {

}
