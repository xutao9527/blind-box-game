package com.bbg.admin.service.impl.sys;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.bbg.model.sys.SysUserRole;
import com.bbg.admin.mapper.sys.SysUserRoleMapper;
import com.bbg.admin.service.sys.SysUserRoleService;
import org.springframework.stereotype.Service;

/**
 * 用户-角色-中间 服务层实现。
 *
 * @author bbg
 * @since 2024-04-28
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

}
