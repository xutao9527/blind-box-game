package com.bbg.core.third.oss;

import com.alibaba.fastjson2.JSONObject;
import com.aliyun.oss.OSSClient;

import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.bbg.core.entity.OssConfig;
import com.bbg.core.entity.OssSignInfo;
import com.bbg.core.service.biz.BizConfigService;
import com.bbg.model.biz.BizConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class OssService {
    public final BizConfigService bizConfigService;

    public OssSignInfo doSign(String ossDir) {
        OssSignInfo ossSignInfo = null;
        BizConfig bizConfig = bizConfigService.getConfigByNameAlias("aliyun_oss_config");
        OssConfig ossConfig = null;
        if (bizConfig != null) {
            try {
                ossConfig = JSONObject.parseObject(bizConfig.getValue(), OssConfig.class);
            } catch (Exception e) {
                log.error("ossConfig parse error", e);
            }
        }
        if (ossConfig != null) {
            OSSClient ossClient = new OSSClient(ossConfig.getEndpoint(), new DefaultCredentialProvider(ossConfig.getAccessId(), ossConfig.getAccessKey()), null);
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, ossDir);
            String postPolicy = ossClient.generatePostPolicy(new Date(System.currentTimeMillis() + 30 * 1000), policyConds);
            String encodedPolicy = BinaryUtil.toBase64String(postPolicy.getBytes(StandardCharsets.UTF_8));
            String postSignature = ossClient.calculatePostSignature(postPolicy);
            ossSignInfo = new OssSignInfo()
                    .setAccessId(ossConfig.getAccessId())
                    .setPolicy(encodedPolicy)
                    .setSignature(postSignature)
                    .setBaseUrlPath(ossConfig.getBaseUrlPath())
                    .setOssDir(ossDir);
        }
        return ossSignInfo;

    }
}
