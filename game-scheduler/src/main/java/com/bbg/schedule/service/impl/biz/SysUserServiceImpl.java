package com.bbg.schedule.service.impl.biz;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.bbg.model.biz.SysUser;
import com.bbg.schedule.mapper.biz.SysUserMapper;
import com.bbg.schedule.service.biz.SysUserService;
import org.springframework.stereotype.Service;

/**
 * 系统用户 服务层实现。
 *
 * @author bbg
 * @since 2024-05-22
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

}
