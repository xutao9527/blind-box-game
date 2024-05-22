package com.bbg.schedule.service.impl.biz;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.bbg.model.biz.CsgoUserInfo;
import com.bbg.schedule.mapper.biz.CsgoUserInfoMapper;
import com.bbg.schedule.service.biz.CsgoUserInfoService;
import org.springframework.stereotype.Service;

/**
 * CSGO用户信息 服务层实现。
 *
 * @author bbg
 * @since 2024-05-22
 */
@Service
public class CsgoUserInfoServiceImpl extends ServiceImpl<CsgoUserInfoMapper, CsgoUserInfo> implements CsgoUserInfoService {

}
