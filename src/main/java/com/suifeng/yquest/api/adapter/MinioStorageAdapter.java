package com.suifeng.yquest.api.adapter;

import com.suifeng.yquest.utils.MinioUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class MinioStorageAdapter implements StorageAdapter {

    @Resource
    private MinioUtil minioUtil;

    /**
     * minioUrl
     */
    @Value("${minio.url}")
    private String url;

    @Override
    @SneakyThrows
    public void createBucket(String bucket) {
        minioUtil.createBucket(bucket);
    }

    @Override
    @SneakyThrows
    public void uploadFile(MultipartFile uploadFile, String bucket, String objectName) {
        try {
            minioUtil.createBucket(bucket);
            if (objectName != null) {
                minioUtil.uploadFile(new ByteArrayInputStream(uploadFile.getBytes()), bucket, objectName + "/" + uploadFile.getOriginalFilename());
            } else {
                minioUtil.uploadFile(new ByteArrayInputStream(uploadFile.getBytes()), bucket, uploadFile.getOriginalFilename());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @SneakyThrows
    public InputStream downLoad(String bucket, String objectName) {
        return minioUtil.downLoad(bucket, objectName);
    }

    @Override
    @SneakyThrows
    public void deleteBucket(String bucket) {
        minioUtil.deleteBucket(bucket);
    }

    @Override
    @SneakyThrows
    public void deleteObject(String bucket, String objectName) {
        minioUtil.deleteObject(bucket, objectName);
    }

    @Override
    @SneakyThrows
    public String getUrl(String bucketName, String objectName) {
        return url + "/" +bucketName + "/" + objectName;
    }

    @Override
    @SneakyThrows
    public String getPresignedFileUrl(String bucketName, String objectName) {
        // 基于内部minio endpoint签名，仅适用于服务端/Docker内网场景；浏览器下载请走后端流式转发接口
        return minioUtil.getPreviewFileUrl(bucketName, objectName);
    }


}
