package com.bbg.core.service.impl.biz;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.bbg.model.biz.BizData;
import com.bbg.core.mapper.biz.BizDataMapper;
import com.bbg.core.service.biz.BizDataService;
import org.springframework.stereotype.Service;

/**
 * 业务数据 服务层实现。
 *
 * @author bbg
 * @since 2024-05-23
 */
@Service
public class BizDataServiceImpl extends ServiceImpl<BizDataMapper, BizData> implements BizDataService {

}
