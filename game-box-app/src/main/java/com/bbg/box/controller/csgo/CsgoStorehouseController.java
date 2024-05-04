package com.bbg.box.controller.csgo;

import com.bbg.box.base.BaseController;
import com.bbg.model.csgo.CsgoStorehouse;
import com.bbg.box.service.csgo.CsgoStorehouseService;
import com.bbg.core.entity.ApiRet;
import com.bbg.core.entity.ReqParams;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.constant.SqlOperator;
import com.mybatisflex.core.query.SqlOperators;
import lombok.RequiredArgsConstructor;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

/**
 * 个人装备仓库 控制层。
 *
 * @author bbg
 * @since 2024-05-04
 */
@RestController
@Tag(name = "个人装备仓库接口")
@RequestMapping("/csgoStorehouse")
public class CsgoStorehouseController extends BaseController<CsgoStorehouse, CsgoStorehouseService> {
    @Autowired
    protected  CsgoStorehouseService csgoStorehouseService;


}