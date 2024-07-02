package com.bbg.box.tenant;

import com.bbg.model.sys.SysTenant;
import com.mybatisflex.core.tenant.TenantFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

@Component
public class TenantIdFactory implements TenantFactory {
    @Override
    public Object[] getTenantIds() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            Object tenantIdObject = attributes.getAttribute("tenantId", RequestAttributes.SCOPE_REQUEST);
            if (tenantIdObject instanceof Long tenantId) {
                return new Object[]{tenantId};
            }
        }
        return new Object[]{0L};                                      // 找不到租户编号，返回0让操作不了数据
    }
}
