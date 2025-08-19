package com.suifeng.yquest.config;

import com.suifeng.yquest.api.adapter.MinioStorageAdapter;
import com.suifeng.yquest.api.adapter.StorageAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RefreshScope
public class StorageConfig {

    @Value("${storage.service.type}")
    private String storageType;

    @Bean
    @RefreshScope
    public StorageAdapter storageService(){
        if ("minio".equals(storageType)) {
            return new MinioStorageAdapter();
        }else {
            throw new IllegalArgumentException("未找到对应的文件存储处理器");
        }
    }

}
