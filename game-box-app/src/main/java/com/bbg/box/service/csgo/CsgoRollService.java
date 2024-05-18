package com.bbg.box.service.csgo;

import com.bbg.core.box.dto.RollDto;
import com.bbg.core.entity.ApiRet;
import com.bbg.model.biz.BizUser;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.bbg.model.csgo.CsgoRoll;

/**
 * Roll房间 服务层。
 *
 * @author bbg
 * @since 2024-05-16
 */
public interface CsgoRollService extends IService<CsgoRoll> {

    CsgoRoll getInfo(Long rollId);

    Page<CsgoRoll> getRollList(RollDto.GetRollListReq req);

    ApiRet<CsgoRoll> joinRoll(BizUser bizUser, Long rollId);
}
