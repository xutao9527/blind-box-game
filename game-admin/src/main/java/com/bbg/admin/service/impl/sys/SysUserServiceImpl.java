package com.bbg.admin.service.impl.sys;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.bbg.model.sys.SysUser;
import com.bbg.admin.mapper.sys.SysUserMapper;
import com.bbg.admin.service.sys.SysUserService;
import org.springframework.stereotype.Service;

/**
 * 系统用户 服务层实现。
 *
 * @author bbg
 * @since 2024-04-25
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

}
