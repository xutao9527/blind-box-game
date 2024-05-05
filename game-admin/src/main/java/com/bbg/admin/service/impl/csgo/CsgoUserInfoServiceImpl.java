package com.bbg.admin.service.impl.csgo;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.bbg.model.csgo.CsgoUserInfo;
import com.bbg.admin.mapper.csgo.CsgoUserInfoMapper;
import com.bbg.admin.service.csgo.CsgoUserInfoService;
import org.springframework.stereotype.Service;

/**
 * CSGO用户信息 服务层实现。
 *
 * @author bbg
 * @since 2024-05-05
 */
@Service
public class CsgoUserInfoServiceImpl extends ServiceImpl<CsgoUserInfoMapper, CsgoUserInfo> implements CsgoUserInfoService {

}
