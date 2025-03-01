package com.bbg.core.service.impl.sys;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.bbg.model.sys.SysMenu;
import com.bbg.core.mapper.sys.SysMenuMapper;
import com.bbg.core.service.sys.SysMenuService;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * 系统菜单 服务层实现。
 *
 * @author bbg
 * @since 2024-05-22
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Override
    public boolean removeById(Serializable id) {
        SysMenu entity = getById(id); // 逻辑删除
        if (entity.getParentId() != null) {
            entity.setEnable(false);
        }
        return this.updateById(entity);
    }

}
