package com.bbg.box.service.impl.csgo;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.bbg.model.csgo.CsgoUserInfo;
import com.bbg.box.mapper.csgo.CsgoUserInfoMapper;
import com.bbg.box.service.csgo.CsgoUserInfoService;
import org.springframework.stereotype.Service;

/**
 * CSGO用户信息 服务层实现。
 *
 * @author bbg
 * @since 2024-05-04
 */
@Service
public class CsgoUserInfoServiceImpl extends ServiceImpl<CsgoUserInfoMapper, CsgoUserInfo> implements CsgoUserInfoService {

}
