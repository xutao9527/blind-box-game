package com.bbg.admin.service.biz;

import com.mybatisflex.core.service.IService;
import com.bbg.model.biz.BizDict;

import java.io.Serializable;

/**
 * 系统字典 服务层。
 *
 * @author bbg
 * @since 2024-04-27
 */
public interface BizDictService extends IService<BizDict> {
     BizDict getDictByTag(String tag);

     boolean removeById(Serializable id);

     boolean updateById(BizDict entity);
}
