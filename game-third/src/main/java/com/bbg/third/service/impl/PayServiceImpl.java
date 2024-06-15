package com.bbg.third.service.impl;

import com.bbg.core.entity.ApiRet;
import com.bbg.third.service.PayService;
import org.springframework.stereotype.Service;

@Service
public class PayServiceImpl implements PayService {

    /**
     * 支付请求
     */
    @Override
    public ApiRet<Object> payCall() {
        return null;
    }

    /**
     * 支付回调
     */
    @Override
    public ApiRet<Object> payCallback() {
        return null;
    }

    /**
     * 支付订单查询
     */
    @Override
    public ApiRet<Object> payOrderQuery() {
        return null;
    }
}
