package com.bbg.admin.service.impl.biz;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.bbg.model.biz.BizUser;
import com.bbg.admin.mapper.biz.BizUserMapper;
import com.bbg.admin.service.biz.BizUserService;
import org.springframework.stereotype.Service;

/**
 * 业务用户 服务层实现。
 *
 * @author bbg
 * @since 2024-04-25
 */
@Service
public class BizUserServiceImpl extends ServiceImpl<BizUserMapper, BizUser> implements BizUserService {

}
