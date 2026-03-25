package com.suifeng.yquest.controller;


import cn.dev33.satoken.annotation.SaCheckPermission;
import com.suifeng.yquest.api.common.PageResult;
import com.suifeng.yquest.api.common.Result;
import com.suifeng.yquest.api.adapter.StorageAdapter;
import com.suifeng.yquest.entity.Resume;
import com.suifeng.yquest.service.JobService;
import com.suifeng.yquest.service.ResumeService;
import com.suifeng.yquest.api.common.Result;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
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
    private JobService jobService;

    @Resource
    private StorageAdapter storageAdapter;

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
    public Result<Boolean> uploadResume(@RequestParam("file") MultipartFile file, 
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
            
            // 获取文件URL
            String fileUrl = storageAdapter.getUrl(bucketName, fileName);
            
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
            
            // 保存简历
            boolean result = resumeService.insert(resume);
            
            // 增加职位申请次数
            if (result) {
                jobService.incrementApplyCount(jobId);
            }
            
            return Result.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("上传失败：" + e.getMessage());
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
        return Result.ok(this.resumeService.queryByJobId(resume.getJobId(), resume.getPageNo(), resume.getPageSize()));
    }

    /**
     * 根据用户ID查询简历
     *
     * @param resume 查询条件
     * @return 分页结果
     */
    @PostMapping("/byUser")
    public Result<PageResult<Resume>> queryByUserId(@RequestBody Resume resume) {
        return Result.ok(this.resumeService.queryByUserId(resume.getUserId(), resume.getPageNo(), resume.getPageSize()));
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
