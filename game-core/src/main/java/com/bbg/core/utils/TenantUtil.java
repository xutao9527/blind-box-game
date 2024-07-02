package com.bbg.core.utils;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

public class TenantUtil {

    public static Long getTenantId(){
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            Object tenantIdObject = attributes.getAttribute("tenantId", RequestAttributes.SCOPE_REQUEST);
            if (tenantIdObject instanceof Long tenantId) {
                return tenantId;
            }
        }
        return null;
    }

    public static boolean isSuperTenant(){
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            Object isSuperTenantObject = attributes.getAttribute("isSuperTenant", RequestAttributes.SCOPE_REQUEST);
            if (isSuperTenantObject instanceof Boolean isSuperTenant) {
                return isSuperTenant;
            }
        }
        return false;
    }

}
