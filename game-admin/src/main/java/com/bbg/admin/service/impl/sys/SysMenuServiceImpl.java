package com.bbg.admin.service.impl.sys;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.bbg.model.sys.SysMenu;
import com.bbg.admin.mapper.sys.SysMenuMapper;
import com.bbg.admin.service.sys.SysMenuService;
import org.springframework.stereotype.Service;

/**
 * 系统菜单 服务层实现。
 *
 * @author bbg
 * @since 2024-04-25
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

}
