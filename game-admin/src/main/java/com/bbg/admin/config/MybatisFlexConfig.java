package com.bbg.admin.config;

import com.bbg.admin.tenant.TenantIdFactory;
import com.bbg.core.utils.IdTool;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.core.FlexGlobalConfig;
import com.mybatisflex.core.audit.AuditManager;
import com.mybatisflex.core.audit.ConsoleMessageCollector;
import com.mybatisflex.core.audit.MessageCollector;
import com.mybatisflex.core.keygen.IKeyGenerator;
import com.mybatisflex.core.keygen.KeyGeneratorFactory;
import com.mybatisflex.core.logicdelete.LogicDeleteProcessor;
import com.mybatisflex.core.logicdelete.impl.BooleanLogicDeleteProcessor;
import com.mybatisflex.core.mybatis.FlexConfiguration;
import com.mybatisflex.core.tenant.TenantManager;
import com.mybatisflex.spring.boot.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MybatisFlexConfig implements ConfigurationCustomizer {

    @Override
    public void customize(FlexConfiguration flexConfiguration) {
        KeyGeneratorFactory.register("SnowFlakeIdKeyGenerator", new SnowFlakeIdKeyGenerator());
        // 配置主键生成
        FlexGlobalConfig.KeyConfig keyConfig = new FlexGlobalConfig.KeyConfig();
        keyConfig.setKeyType(KeyType.Generator);
        keyConfig.setValue("SnowFlakeIdKeyGenerator");
        keyConfig.setBefore(true);
        FlexGlobalConfig flexGlobalConfig = FlexGlobalConfig.getDefaultConfig();
        flexGlobalConfig.setKeyConfig(keyConfig);
        // 开启审计功能
        AuditManager.setAuditEnable(true);
        // 设置 SQL 审计收集器
        MessageCollector collector = new ConsoleMessageCollector();
        AuditManager.setMessageCollector(collector);
        // 全局多租户配置
        flexGlobalConfig.setTenantColumn("tenant_id");
        TenantManager.setTenantFactory(new TenantIdFactory());
        // 逻辑删除配置
        flexGlobalConfig.setLogicDeleteColumn("is_deleted");
        flexGlobalConfig.setNormalValueOfLogicDelete(false);
        flexGlobalConfig.setDeletedValueOfLogicDelete(true);
        // LogicDeleteManager.setProcessor(logicDeleteProcessor());
    }

    @Bean
    public LogicDeleteProcessor logicDeleteProcessor(){
        return new BooleanLogicDeleteProcessor();
    }

    static class SnowFlakeIdKeyGenerator implements IKeyGenerator{
        @Override
        public Object generate(Object o, String s) {
            return IdTool.nextId();
        }
    }
}

