package com.bbg.core.service.impl.csgo;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.bbg.model.csgo.CsgoBattleRoomUser;
import com.bbg.core.mapper.csgo.CsgoBattleRoomUserMapper;
import com.bbg.core.service.csgo.CsgoBattleRoomUserService;
import org.springframework.stereotype.Service;

/**
 * 对战房间用户 服务层实现。
 *
 * @author bbg
 * @since 2024-05-22
 */
@Service
public class CsgoBattleRoomUserServiceImpl extends ServiceImpl<CsgoBattleRoomUserMapper, CsgoBattleRoomUser> implements CsgoBattleRoomUserService {

}
