package com.bbg.core.service.sys;

import com.mybatisflex.core.service.IService;
import com.bbg.model.sys.SysTenant;

/**
 * 系统租户 服务层。
 *
 * @author bbg
 * @since 2024-06-24
 */
public interface SysTenantService extends IService<SysTenant> {
    Long getTenantId(String tenantCode);
}
