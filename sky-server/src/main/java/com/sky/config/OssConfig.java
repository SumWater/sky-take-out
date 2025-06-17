package com.sky.config;

import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyun.oss.common.utils.StringUtils;
import com.aliyuncs.auth.Credential;
import com.aliyuncs.exceptions.ClientException;
import com.sky.properties.AliOssProperties;
import com.sky.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * 阿里云文件上传配置类
 */
@Configuration
@Slf4j
public class OssConfig {

    @Bean
    @ConditionalOnMissingBean
    public AliOssUtil aliOssUtil(AliOssProperties aliOssProperties) throws ClientException {
        log.info("初始化阿里云文件上传工具：{}",aliOssProperties);
        // 从环境变量中获取访问凭证。运行本代码示例之前，请确保已设置环境变量OSS_ACCESS_KEY_ID和OSS_ACCESS_KEY_SECRET。
        String accessKeyId = StringUtils.trim(System.getenv("OSS_ACCESS_KEY_ID"));
        String secretAccessKey = StringUtils.trim(System.getenv("OSS_ACCESS_KEY_SECRET"));

        return new AliOssUtil(aliOssProperties.getEndpoint(),
                accessKeyId,
                secretAccessKey,
                aliOssProperties.getBucketName());
    }

}
