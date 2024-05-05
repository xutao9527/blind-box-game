package com.bbg.box.service.csgo;

import com.bbg.model.biz.BizUser;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import com.bbg.model.csgo.CsgoBox;

import java.util.List;

/**
 * CSGO箱子 服务层。
 *
 * @author bbg
 * @since 2024-05-03
 */
public interface CsgoBoxService extends IService<CsgoBox> {

    public CsgoBox getBoxesById(Long id);

    public List<CsgoBox> getBoxesByType(String type);

    public void openBox(BizUser bizUser, Long boxId);
}
