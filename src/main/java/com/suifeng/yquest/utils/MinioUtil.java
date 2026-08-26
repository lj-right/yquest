package com.suifeng.yquest.utils;

import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import org.apache.tomcat.jni.FileInfo;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * minio文件操作工具
 */
@Component
public class MinioUtil {

    @Resource
    private MinioClient minioClient;

    /**
     * 创建bucket桶
     */
    public void createBucket(String bucket) throws Exception {
        boolean exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
        if (!exists) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
        }
    }

    /**
     * 上传文件
     */
    public void uploadFile(InputStream inputStream, String bucket, String objectName) throws Exception {
        // 确保 InputStream 支持 mark/reset，并提供足够的缓冲区大小
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, 10*1024*1024);
        minioClient.putObject(PutObjectArgs.builder().bucket(bucket).object(objectName)
                .stream(bufferedInputStream, -1, Integer.MAX_VALUE).build());
    }

    /**
     * 下载文件
     *
     * @param bucket
     * @param objectName
     * @return
     */
    public InputStream downLoad(String bucket, String objectName) throws Exception {
        return minioClient.getObject(
                GetObjectArgs.builder().bucket(bucket).object(objectName).build()
        );
    }

    /**
     * 删除桶
     *
     * @param bucket
     * @return
     */
    public void deleteBucket(String bucket) throws Exception {
        minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucket).build()
        );
    }

    /**
     * 删除文件
     *
     * @param bucket
     * @return
     */
    public void deleteObject(String bucket, String objectName) throws Exception {
        minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucket).object(objectName).build()
        );
    }

    /**
     * 获取文件预签名URL（默认1小时有效）
     */
    public String getPreviewFileUrl(String bucketName, String objectName) throws Exception{
        return getPreviewFileUrl(bucketName, objectName, 3600);
    }

    /**
     * 获取文件预签名URL（临时授权访问，适用于私有桶）
     *
     * @param bucketName 桶名
     * @param objectName 对象键
     * @param expirySeconds 有效期（秒，最大7天）
     * @return 带签名的临时访问URL
     */
    public String getPreviewFileUrl(String bucketName, String objectName, int expirySeconds) throws Exception{
        GetPresignedObjectUrlArgs args = GetPresignedObjectUrlArgs.builder()
                .method(Method.GET)
                .bucket(bucketName).object(objectName)
                .expiry(expirySeconds)
                .build();
        return minioClient.getPresignedObjectUrl(args);
    }
}
