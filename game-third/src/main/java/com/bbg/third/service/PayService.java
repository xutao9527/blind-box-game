package com.bbg.third.service;

import com.bbg.core.entity.ApiRet;

public interface PayService {

    ApiRet<Object> payCall();

    ApiRet<Object> payCallback();

    ApiRet<Object> payOrderQuery();
}
