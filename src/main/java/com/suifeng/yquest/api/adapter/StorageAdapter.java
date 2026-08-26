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

    /**
     * 获取文件预签名访问URL（临时授权，适用于私有桶，无法直接访问的场景）
     *
     * @param bucketName 桶名
     * @param objectName 对象键
     * @return 带签名的临时访问URL
     */
    String getPresignedFileUrl(String bucketName, String objectName);
}
