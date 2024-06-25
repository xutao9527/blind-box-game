package com.bbg.core.service.impl.sys;

import cn.hutool.core.codec.Base58;
import cn.hutool.core.convert.Convert;
import com.bbg.core.utils.IdTool;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.bbg.model.sys.SysTenant;
import com.bbg.core.mapper.sys.SysTenantMapper;
import com.bbg.core.service.sys.SysTenantService;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * 系统租户 服务层实现。
 *
 * @author bbg
 * @since 2024-06-24
 */
@Service
public class SysTenantServiceImpl extends ServiceImpl<SysTenantMapper, SysTenant> implements SysTenantService {

    @Override
    public boolean save(SysTenant entity) {
        entity.setId(IdTool.nextId());
        entity.setTenantCode(Base58.encode(Convert.longToBytes(entity.getId())));   //生成租户编码
        return super.save(entity);
    }

    @Override
    public boolean updateById(SysTenant entity) {
        entity.setTenantCode(null); //不允许修改租户编码
        return super.updateById(entity);
    }

    @Override
    public boolean removeById(Serializable id) {
        SysTenant entity = getById(id); //逻辑删除
        entity.setEnable(false);
        return this.updateById(entity);
    }

}
