package com.bbg.core.config;

import com.mybatisflex.core.tenant.TenantFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

@Component
public class BbgTenantFactory implements TenantFactory {
    @Override
    public Object[] getTenantIds() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            Object tenantId = attributes.getAttribute("tenantId", RequestAttributes.SCOPE_REQUEST);
            if (tenantId != null) {
                return new Object[]{tenantId};
            }
        }
        return new Object[]{0};
    }
}
