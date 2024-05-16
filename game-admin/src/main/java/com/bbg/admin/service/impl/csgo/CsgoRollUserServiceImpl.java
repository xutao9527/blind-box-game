package com.bbg.admin.service.impl.csgo;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.bbg.model.csgo.CsgoRollUser;
import com.bbg.admin.mapper.csgo.CsgoRollUserMapper;
import com.bbg.admin.service.csgo.CsgoRollUserService;
import org.springframework.stereotype.Service;

/**
 * Roll房间用户 服务层实现。
 *
 * @author bbg
 * @since 2024-05-16
 */
@Service
public class CsgoRollUserServiceImpl extends ServiceImpl<CsgoRollUserMapper, CsgoRollUser> implements CsgoRollUserService {

}
