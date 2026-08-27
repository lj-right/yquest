package com.suifeng.yquest.controller;


import cn.dev33.satoken.annotation.SaCheckPermission;
import com.google.common.base.Preconditions;
import com.suifeng.yquest.api.common.PageResult;
import com.suifeng.yquest.api.common.Result;
import com.suifeng.yquest.api.adapter.StorageAdapter;
import com.suifeng.yquest.entity.Resume;
import com.suifeng.yquest.service.ResumeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

/**
 * (Resume)表控制层
 */
@RestController
@RequestMapping("/resume")
@CrossOrigin
public class ResumeController {
    /**
     * 服务对象
     */
    @Resource
    private ResumeService resumeService;

    @Resource
    private StorageAdapter storageAdapter;

    /**
     * minio服务地址（用于从存储的文件URL中解析对象键）
     */
    @Value("${minio.url}")
    private String minioUrl;

    /**
     * 简历文件桶名
     */
    private static final String RESUME_BUCKET = "resumes";

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public Result<Resume> queryById(@PathVariable("id") Long id) {
        return Result.ok(this.resumeService.queryById(id));
    }

    /**
     * 上传简历
     *
     * @param file 简历文件
     * @param jobId 职位ID
     * @param userId 用户ID
     * @param userName 用户姓名
     * @param userEmail 用户邮箱
     * @param userPhone 用户电话
     * @param selfIntroduction 自我介绍
     * @return 上传结果
     */
    @PostMapping("/upload")
    public Result<Long> uploadResume(@RequestParam("file") MultipartFile file,
                                      @RequestParam("jobId") Long jobId, 
                                      @RequestParam("userId") Long userId, 
                                      @RequestParam("userName") String userName, 
                                      @RequestParam("userEmail") String userEmail, 
                                      @RequestParam("userPhone") String userPhone, 
                                      @RequestParam(value = "selfIntroduction", required = false) String selfIntroduction) {
        try {
            // 生成唯一文件名
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String fileName = UUID.randomUUID().toString() + extension;
            
            // 上传文件到MinIO
            String bucketName = "resumes";
            storageAdapter.createBucket(bucketName);
            storageAdapter.uploadFile(file, bucketName, fileName);
            
            // 获取文件URL（实际对象键为 fileName/原始文件名，与Adapter上传逻辑保持一致）
            String fileUrl = storageAdapter.getUrl(RESUME_BUCKET, fileName + "/" + originalFilename);
            
            // 创建简历记录
            Resume resume = new Resume();
            resume.setJobId(jobId);
            resume.setUserId(userId);
            resume.setUserName(userName);
            resume.setUserEmail(userEmail);
            resume.setUserPhone(userPhone);
            resume.setResumeFileUrl(fileUrl);
            resume.setResumeFileName(originalFilename);
            resume.setSelfIntroduction(selfIntroduction);
            
            // 保存简历（MyBatis-Plus插入后回填自增ID）
            boolean result = resumeService.insert(resume);

            // 返回简历ID（前端用它提交内推申请）；职位申请次数由 /refer/apply/save 统一计数，此处不重复累加
            if (result) {
                return Result.ok(resume.getId());
            }
            return Result.fail("简历保存失败！");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("上传失败：" + e.getMessage());
        }
    }

    /**
     * 获取简历文件的预签名下载URL
     * 私有桶文件无法直接访问（AccessDenied），需通过带签名的临时授权URL下载/预览
     *
     * @param resume 简历（ID必填）
     * @return 预签名URL（1小时内有效）
     */
    @PostMapping("/getDownloadUrl")
    public Result<String> getDownloadUrl(@RequestBody Resume resume) {
        try {
            Preconditions.checkArgument(Objects.nonNull(resume) && Objects.nonNull(resume.getId()), "简历ID不能为空！");
            Resume dbResume = this.resumeService.queryById(resume.getId());
            Preconditions.checkArgument(Objects.nonNull(dbResume), "简历不存在！");
            String fileUrl = dbResume.getResumeFileUrl();
            Preconditions.checkArgument(StringUtils.isNotBlank(fileUrl), "该简历没有附件文件！");
            // 从存储的URL中解析对象键：{minio.url}/{bucket}/{objectName}
            String objectKey = fileUrl.replace(minioUrl + "/" + RESUME_BUCKET + "/", "");
            // 兼容历史数据：旧数据URL缺少"/原始文件名"段，真实对象键为 {uuid.ext}/{原始文件名}
            if (!objectKey.contains("/")) {
                objectKey = objectKey + "/" + dbResume.getResumeFileName();
            }
            return Result.ok(storageAdapter.getPresignedFileUrl(RESUME_BUCKET, objectKey));
        } catch (IllegalArgumentException e) {
            return Result.fail(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("获取下载链接失败：" + e.getMessage());
        }
    }

    /**
     * 编辑数据
     *
     * @param resume 实体
     * @return 编辑结果
     */
    @PutMapping("/edit")
    public Result<Boolean> edit(@RequestBody Resume resume) {
        return Result.ok(this.resumeService.update(resume));
    }

    /**
     * 删除数据
     *
     * @param resume 实体
     * @return 删除是否成功
     */
    @DeleteMapping("/deleteById")
    public Result<Boolean> deleteById(@RequestBody Resume resume) {
        return Result.ok(this.resumeService.deleteById(resume.getId()));
    }

    /**
     * 分页查询
     * @param resume
     * @return
     */
    @PostMapping("/queryPage")
    public Result<PageResult<Resume>> queryPage(@RequestBody Resume resume) {
        return Result.ok(this.resumeService.queryByPage(resume));
    }

    /**
     * 根据职位ID查询简历
     *
     * @param resume 查询条件
     * @return 分页结果
     */
    @PostMapping("/byJob")
    public Result<PageResult<Resume>> queryByJobId(@RequestBody Resume resume) {
        return Result.ok(this.resumeService.queryByJobId(resume));
    }

    /**
     * 根据用户ID查询简历
     *
     * @param resume 查询条件
     * @return 分页结果
     */
    @PostMapping("/byUser")
    public Result<PageResult<Resume>> queryByUserId(@RequestBody Resume resume) {
        return Result.ok(this.resumeService.queryByUserId(resume));
    }

    /**
     * 处理简历状态
     *
     * @param id 简历ID
     * @param status 状态（1：通过，2：拒绝）
     * @return 处理结果
     */
    @PostMapping("/process/{id}/{status}")
    public Result<Boolean> processResume(@PathVariable("id") Long id, @PathVariable("status") Integer status) {
        return Result.ok(this.resumeService.processResume(id, status));
    }

}
