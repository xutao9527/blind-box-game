package com.bbg.box.service.impl.csgo;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.bbg.model.csgo.CsgoBox;
import com.bbg.box.mapper.csgo.CsgoBoxMapper;
import com.bbg.box.service.csgo.CsgoBoxService;
import org.springframework.stereotype.Service;

/**
 * CSGO箱子 服务层实现。
 *
 * @author bbg
 * @since 2024-05-03
 */
@Service
public class CsgoBoxServiceImpl extends ServiceImpl<CsgoBoxMapper, CsgoBox> implements CsgoBoxService {

}
