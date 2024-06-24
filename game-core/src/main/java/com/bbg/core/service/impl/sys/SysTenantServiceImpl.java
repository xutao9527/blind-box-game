package com.bbg.core.service.impl.sys;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.bbg.model.sys.SysTenant;
import com.bbg.core.mapper.sys.SysTenantMapper;
import com.bbg.core.service.sys.SysTenantService;
import org.springframework.stereotype.Service;

/**
 * 系统租户 服务层实现。
 *
 * @author bbg
 * @since 2024-06-24
 */
@Service
public class SysTenantServiceImpl extends ServiceImpl<SysTenantMapper, SysTenant> implements SysTenantService {

}
