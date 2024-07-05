package com.bbg.admin.controller.sys;

import com.bbg.admin.base.controller.sys.BaseSysTenantController;
import com.bbg.core.entity.ApiRet;
import com.bbg.core.entity.ReqParams;
import com.bbg.model.sys.SysTenant;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.tenant.TenantManager;
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
        return super.page(reqParams);
    }

    @GetMapping("getSubTenant")
    @Operation(summary = "查询所有子租户", description = "查询所有子租户")
    public List<SysTenant> getSubTenant() {
        return sysTenantService.list().stream().filter(
                sysTenant -> sysTenant.getParentId() != null && sysTenant.getEnable()
        ).collect(Collectors.toList());
    }

    @GetMapping("getTenantId")
    @Operation(summary = "查询租户编号", description = "根据租户编号查询租户编号")
    public Long getTenantId(@RequestParam("tenantCode") String tenantCode) {
        return sysTenantService.getTenantId(tenantCode);
    }

}