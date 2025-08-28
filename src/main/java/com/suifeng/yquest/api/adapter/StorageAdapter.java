package com.suifeng.yquest.api.adapter;

import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;

/**
 * 文件存储适配器
 */
public interface StorageAdapter {
    /**
     * 创建bucket桶
     */
    void createBucket(String bucket);

    /**
     * 上传文件
     */
    void uploadFile(MultipartFile uploadFile, String bucket, String objectName);

    /**
     * 下载文件
     *
     * @param bucket
     * @param objectName
     * @return
     */
    InputStream downLoad(String bucket, String objectName);

    /**
     * 删除桶
     *
     * @param bucket
     * @return
     */
    void deleteBucket(String bucket);

    /**
     * 删除文件
     *
     * @param bucket
     * @return
     */
    void deleteObject(String bucket, String objectName);

    String getUrl(String bucketName, String objectName);
}
