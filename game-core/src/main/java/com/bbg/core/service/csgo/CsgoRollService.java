package com.bbg.core.service.csgo;

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
 * @since 2024-05-22
 */
public interface CsgoRollService extends IService<CsgoRoll> {
    /**
     * 获得撸房信息
     * 缓存信息默认存储180秒(根据内存实时调整)
     */
    CsgoRoll getInfo(Long rollId);

    /**
     * 获得撸房列表信息
     * 缓存信息默认存储500毫秒,避免高并发,缓解数据库压力
     */
    Page<CsgoRoll> getRollList(RollDto.GetRollListReq req);

    /**
     * 加入撸房
     */
    ApiRet<CsgoRoll> joinRoll(BizUser bizUser, Long rollId);

    /**
     * 上线房间
     */
    boolean onlineRoll(CsgoRoll csgoRoll);

    /**
     * 下线房间
     */
    boolean offlineRoll(CsgoRoll csgoRoll);
}
