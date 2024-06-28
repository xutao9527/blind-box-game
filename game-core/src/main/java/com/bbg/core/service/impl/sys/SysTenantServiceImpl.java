package com.bbg.core.service.impl.sys;

import cn.hutool.core.codec.Base58;
import cn.hutool.core.convert.Convert;
import com.bbg.core.annotation.RedisCache;
import com.bbg.core.annotation.RedisClear;
import com.bbg.core.constrans.KeyConst;
import com.bbg.core.service.RedisService;
import com.bbg.core.utils.IdTool;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.bbg.model.sys.SysTenant;
import com.bbg.core.mapper.sys.SysTenantMapper;
import com.bbg.core.service.sys.SysTenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * 系统租户 服务层实现。
 *
 * @author bbg
 * @since 2024-06-24
 */
@Service
@RequiredArgsConstructor
public class SysTenantServiceImpl extends ServiceImpl<SysTenantMapper, SysTenant> implements SysTenantService {
    public final RedisService redisService;

    @Override
    public boolean save(SysTenant entity) {
        SysTenant rootTenant = getOne(QueryWrapper.create(new SysTenant().setEnable(true)).isNull(SysTenant::getParentId));
        entity.setId(IdTool.nextId());
        entity.setParentId(rootTenant.getId());// 设置父级租户编号
        entity.setTenantCode(Base58.encode(Convert.longToBytes(entity.getId())));   // 生成租户编码
        return super.save(entity);
    }

    @Override
    @RedisClear(value = "#entity.id", key = KeyConst.TENANT_ID)
    public boolean updateById(SysTenant entity) {
        entity.setTenantCode(null); // 不允许修改租户编码
        entity.setParentId(null);// 不允许修改父租户编号
        redisService.delete(KeyConst.TENANT_LIST);// 清缓存
        return super.updateById(entity);

    }

    @Override
    @RedisClear(value = "#id", key = KeyConst.TENANT_ID)
    public boolean removeById(Serializable id) {
        SysTenant entity = getById(id); // 逻辑删除
        if (entity.getParentId() != null) {// 不允许禁用顶级租户
            entity.setEnable(false);
        }
        redisService.delete(KeyConst.TENANT_LIST);// 清缓存
        return this.updateById(entity);
    }

    @Override
    @RedisCache(value = "#id", key = KeyConst.TENANT_ID)
    public SysTenant getById(Serializable id) {
        return super.getById(id);
    }

    @Override
    @RedisCache(key = KeyConst.TENANT_LIST)
    public List<SysTenant> list() {
        return super.list();
    }
}
