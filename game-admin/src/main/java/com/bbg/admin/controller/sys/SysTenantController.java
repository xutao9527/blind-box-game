package com.bbg.admin.controller.sys;

import com.bbg.admin.base.controller.sys.BaseSysTenantController;
import com.bbg.core.entity.ApiRet;
import com.bbg.core.entity.ReqParams;
import com.bbg.model.sys.SysTenant;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 系统租户 控制层。
 *
 * @author bbg
 * @since 2024-06-24
 */
@RestController
@Tag(name = "系统租户接口")
@RequestMapping("/sysTenant")
public class SysTenantController extends BaseSysTenantController {

    @Override
    public ApiRet<Page<SysTenant>> page(ReqParams<SysTenant> reqParams) {
        ApiRet<Page<SysTenant>> apiRet = super.page(reqParams);
        List<SysTenant> records = apiRet.getData().getRecords();
        Map<Long, SysTenant> sysTenantMap = records.stream().collect(Collectors.toMap(SysTenant::getId, SysTenant -> SysTenant));
        records = records.stream().peek(sysTenant -> {
            if (sysTenant.getParentId() != null) {
                SysTenant parent = sysTenantMap.get(sysTenant.getParentId());
                if (sysTenant.getExpandProps() == null) {
                    sysTenant.setExpandProps(HashMap.newHashMap(1));
                }
                sysTenant.getExpandProps().put("parentTitle", Objects.isNull(parent) ? "" : parent.getTenantName());
            }
        }).toList();
        List<Long> ids = records.stream()
                .filter(sysTenant -> sysTenant.getParentId() == null || sysTenant.getExpandProps() == null || sysTenant.getExpandProps().get("parentTitle") == null)
                .map(SysTenant::getId).toList();
        if (!ids.isEmpty()) {
            List<SysTenant> sysTenants = sysTenantService.listByIds(ids);
            records.forEach(sysTenant -> {
                if (sysTenant.getParentId() == null || sysTenant.getExpandProps() == null || sysTenant.getExpandProps().get("parentTitle") == null) {
                    SysTenant parent = sysTenants.stream().filter(tenant -> tenant.getId().equals(sysTenant.getParentId())).findFirst().orElse(null);
                    if (sysTenant.getExpandProps() == null) {
                        sysTenant.setExpandProps(HashMap.newHashMap(1));
                    }
                    sysTenant.getExpandProps().put("parentTitle", Objects.isNull(parent) ? "" : parent.getTenantName());
                }
            });
        }
        return apiRet;
    }

    @GetMapping("getSelectTenants")
    @Operation(summary = "获得一二级租户", description = "获得一二级租户")
    public ApiRet<List<SysTenant>> getSelectTenants() {
        List<SysTenant> allTenant = new ArrayList<>();
        SysTenant rootTenant = sysTenantService.getOne(QueryWrapper.create(new SysTenant().setEnable(true)).isNull(SysTenant::getParentId));
        if(rootTenant!=null){
            allTenant = sysTenantService.list(QueryWrapper.create(new SysTenant().setEnable(true)).eq(SysTenant::getParentId, rootTenant.getId()));
            allTenant.addFirst(rootTenant);
        }
        return ApiRet.buildOk(allTenant);
    }
}