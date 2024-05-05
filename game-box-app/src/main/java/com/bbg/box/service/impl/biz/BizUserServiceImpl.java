package com.bbg.box.service.impl.biz;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.bbg.model.biz.BizUser;
import com.bbg.box.mapper.biz.BizUserMapper;
import com.bbg.box.service.biz.BizUserService;
import org.springframework.stereotype.Service;

/**
 * 业务用户 服务层实现。
 *
 * @author bbg
 * @since 2024-05-03
 */
@Service
public class BizUserServiceImpl extends ServiceImpl<BizUserMapper, BizUser> implements BizUserService {
    public BizUser getOneWithRelations(QueryWrapper query){
        return getMapper().selectOneWithRelationsByQuery(query);
    }
}
