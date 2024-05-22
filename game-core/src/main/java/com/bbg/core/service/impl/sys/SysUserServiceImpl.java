package com.bbg.core.service.impl.sys;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.bbg.model.sys.SysUser;
import com.bbg.core.mapper.sys.SysUserMapper;
import com.bbg.core.service.sys.SysUserService;
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
