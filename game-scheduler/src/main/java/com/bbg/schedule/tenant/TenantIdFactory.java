package com.bbg.schedule.tenant;

import com.bbg.core.utils.TenantUtil;
import com.mybatisflex.core.tenant.TenantFactory;

public class TenantIdFactory implements TenantFactory {
    @Override
    public Object[] getTenantIds() {
        Long tenantId = TenantUtil.getTenantId();                     // 从工具类获取租户编号
        if (tenantId != null) {
            return new Object[]{tenantId};
        }
        return new Object[]{0L};                                      // 找不到租户编号，返回0让操作不了数据
    }
}
