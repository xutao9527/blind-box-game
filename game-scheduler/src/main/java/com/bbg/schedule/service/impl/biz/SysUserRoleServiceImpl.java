package com.bbg.schedule.service.impl.biz;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.bbg.model.biz.SysUserRole;
import com.bbg.schedule.mapper.biz.SysUserRoleMapper;
import com.bbg.schedule.service.biz.SysUserRoleService;
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
