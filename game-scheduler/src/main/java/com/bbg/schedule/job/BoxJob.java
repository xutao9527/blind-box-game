package com.bbg.schedule.job;

import com.bbg.core.service.biz.BizDictService;
import com.bbg.core.service.biz.BizUserService;
import com.bbg.core.service.csgo.CsgoBoxService;
import com.bbg.core.utils.SpringUtil;
import com.bbg.core.utils.TenantUtil;
import com.bbg.model.biz.BizUser;
import com.bbg.model.csgo.CsgoBox;
import com.bbg.schedule.loader.BoxLoader;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class BoxJob {
    // 线程安全的用户集合
    public static volatile List<Long> userIds = new ArrayList<>();
    // 用户类型
    public static volatile String userType;
    // 箱子类型
    public static volatile String boxType;

    // 用户类型总数
    public static volatile long userTypeCount;
    // 随机数
    public static volatile SecureRandom random = new SecureRandom();

    public static class Check implements Job {
        @Override
        public void execute(JobExecutionContext jobExecutionContext)  {
            BoxLoader boxLoader = SpringUtil.getBean(BoxLoader.class);
            boxLoader.reLoadJob();
        }
    }

    public static class OpenBox implements Job {
        @Override
        public void execute(JobExecutionContext jobExecutionContext)  {
            JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
            long tenantId = jobDataMap.getLongValue("tenantId");
            try {
                TenantUtil.setTenantId(tenantId);
                // 加载虚拟用户类型
                if (userType == null) {
                    BizDictService bizDictService = SpringUtil.getBean(BizDictService.class);
                    userType = bizDictService.getDictByTag("user_type").getValueByAlias("virtual_user");
                }
                // 加载对战箱子类型
                if (boxType == null) {
                    BizDictService bizDictService = SpringUtil.getBean(BizDictService.class);
                    boxType = bizDictService.getDictByTag("csgo_box_type").getValueByAlias("battle_box");
                }
                // 检查虚拟用户列表数量是否需要更新
                BizUserService bizUserService = SpringUtil.getBean(BizUserService.class);
                long newUserTypeCount = bizUserService.getUserTypeCount(userType);
                if (userTypeCount != newUserTypeCount) {
                    userTypeCount = newUserTypeCount;
                    userIds = bizUserService.getUserIdsByType(userType);
                }
                // -------------------------------------------------------------------------------------------------------------------
                // 随机获取一个虚拟用户
                long userId = userIds.get(random.nextInt(userIds.size()));
                BizUser bizUser = bizUserService.getById(userId);
                // 随机获得箱子
                CsgoBoxService csgoBoxService = SpringUtil.getBean(CsgoBoxService.class);
                List<CsgoBox> csgoBoxes = csgoBoxService.getBoxesByType(boxType);
                CsgoBox box = csgoBoxes.get(random.nextInt(csgoBoxes.size()));
                csgoBoxService.openBox(bizUser, box.getId());
            } finally {
                TenantUtil.clear();
            }
        }
    }
}
