package com.bbg.core.utils;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

public class TenantUtil {
    private static final ThreadLocal<Long> tenantIdHolder = new ThreadLocal<>();

    public static void setTenantId(Long tenantId) {
        tenantIdHolder.set(tenantId);
    }

    public static void clear() {
        tenantIdHolder.remove();
    }

    public static Long getTenantId(){
        // 从请求中获取租户编号， 接受外部请求的租户编号
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            Object tenantIdObject = attributes.getAttribute("tenantId", RequestAttributes.SCOPE_REQUEST);
            if (tenantIdObject instanceof Long tenantId) {
                return tenantId;
            }
        }
        // 从线程变量中获取租户编号， 用于内部线程调用
        return tenantIdHolder.get();
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
