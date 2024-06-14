package com.bbg.core.service.impl.biz;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.bbg.model.biz.BizPayOrder;
import com.bbg.core.mapper.biz.BizPayOrderMapper;
import com.bbg.core.service.biz.BizPayOrderService;
import org.springframework.stereotype.Service;

/**
 * 支付订单 服务层实现。
 *
 * @author bbg
 * @since 2024-06-14
 */
@Service
public class BizPayOrderServiceImpl extends ServiceImpl<BizPayOrderMapper, BizPayOrder> implements BizPayOrderService {

}
