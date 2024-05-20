package com.bbg.box.controller.biz;

import com.bbg.box.base.BaseController;
import com.bbg.model.biz.BizDictDetail;
import com.bbg.box.service.biz.BizDictDetailService;
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
 * 系统字典详情 控制层。
 *
 * @author bbg
 * @since 2024-05-03
 */
@RestController
@Tag(name = "系统字典详情接口")
@RequestMapping("/bizDictDetail")
@RequiredArgsConstructor
public class BizDictDetailController extends BaseController<BizDictDetail, BizDictDetailService> {

    public final BizDictDetailService bizDictDetailService;
}