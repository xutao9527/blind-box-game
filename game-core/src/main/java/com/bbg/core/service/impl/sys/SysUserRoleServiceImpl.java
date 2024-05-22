package com.bbg.core.service.impl.sys;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.bbg.model.sys.SysUserRole;
import com.bbg.core.mapper.sys.SysUserRoleMapper;
import com.bbg.core.service.sys.SysUserRoleService;
import org.springframework.stereotype.Service;

/**
 * 用户-角色-中间 服务层实现。
 *
 * @author bbg
 * @since 2024-05-22
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

}
