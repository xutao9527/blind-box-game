package com.bbg.core.config;

import com.bbg.model.sys.SysTenant;
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
            Object tenantObject = attributes.getAttribute("tenantObject", RequestAttributes.SCOPE_REQUEST);
            if(tenantObject instanceof SysTenant sysTenant){
                if(sysTenant.getParentId() == null){    //超级租户,返回空数据
                    return new Object[]{};
                }else{
                    return new Object[]{sysTenant.getId()};
                }
            }
        }
        return new Object[]{};
    }
}
