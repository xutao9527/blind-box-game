package com.bbg.box.service.impl.csgo;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.bbg.model.csgo.CsgoGoods;
import com.bbg.box.mapper.csgo.CsgoGoodsMapper;
import com.bbg.box.service.csgo.CsgoGoodsService;
import org.springframework.stereotype.Service;

/**
 * CSGO商品表 服务层实现。
 *
 * @author bbg
 * @since 2024-05-03
 */
@Service
public class CsgoGoodsServiceImpl extends ServiceImpl<CsgoGoodsMapper, CsgoGoods> implements CsgoGoodsService {

}
