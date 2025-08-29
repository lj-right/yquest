package com.suifeng.yquest.service.impl;

import com.suifeng.yquest.api.adapter.StorageAdapter;
import com.suifeng.yquest.entity.AuthUser;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Service
public class FileService {

        @Resource
        private final StorageAdapter storageAdapter;

        public FileService(StorageAdapter storageAdapter) {
            this.storageAdapter = storageAdapter;
        }

        public String getUrl(String bucketName,String objectName){
            return storageAdapter.getUrl(bucketName,objectName);
        }

        /**
         * 上传文件
         * @param uploadFile
         * @param bucket
         * @param objectName
         * @return
         */
        public String uploadFile(MultipartFile uploadFile, String bucket, String objectName){
            storageAdapter.uploadFile(uploadFile,bucket,objectName);
            objectName = objectName + "/" + uploadFile.getOriginalFilename();
            return storageAdapter.getUrl(bucket, objectName);

        }

        /**
         * 删除oss指定文件
         * @param user
         * @return
         */
        public void deletefile(AuthUser user) {
            storageAdapter.deleteObject("suifeng",user.getAvatar());
        }
}
