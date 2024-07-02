package com.bbg.schedule.tenant;

import com.bbg.core.utils.TenantUtil;
import com.mybatisflex.core.tenant.TenantFactory;

public class TenantIdFactory implements TenantFactory {

    private static final ThreadLocal<Long> tenantIdHolder = new ThreadLocal<>();

    @Override
    public Object[] getTenantIds() {
        Long tenantId = getTenantId();                     // 从工具类获取租户编号
        if (tenantId != null) {
            return new Object[]{tenantId};
        }
        return new Object[]{0L};                                      // 找不到租户编号，返回0让操作不了数据
    }

    public static void setTenantId(Long tenantId) {
        tenantIdHolder.set(tenantId);
    }

    public static Long getTenantId() {
        return tenantIdHolder.get();
    }

    public static void clear() {
        tenantIdHolder.remove();
    }
}
