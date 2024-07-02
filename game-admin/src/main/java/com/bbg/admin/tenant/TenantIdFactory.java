package com.bbg.admin.tenant;

import com.bbg.core.utils.TenantUtil;
import com.bbg.model.sys.SysTenant;
import com.mybatisflex.core.tenant.TenantFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

@Component
public class TenantIdFactory implements TenantFactory {
    @Override
    public Object[] getTenantIds() {
        Long tenantId = TenantUtil.getTenantId();                   // 从工具类获取租户编号
        boolean isSuperTenant = TenantUtil.isSuperTenant();         // 从工具类获取是否超级租户
        if(isSuperTenant){
            return new Object[]{};                                  // 超级租户,返回空数据,可以操作所有租户数据
        }
        if (tenantId != null) {
            return new Object[]{tenantId};                          // 普通租户,返回租户编号,只能操作自己租户数据
        }
        return new Object[]{};                                      // 未登录用户,返回空数据,登录时候使用
    }
}
