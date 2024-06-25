package com.bbg.admin.controller.sys;

import com.bbg.admin.base.controller.sys.BaseSysTenantController;
import com.bbg.core.entity.ApiRet;
import com.bbg.model.sys.SysTenant;
import com.mybatisflex.core.query.QueryWrapper;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.ArrayList;
import java.util.List;

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