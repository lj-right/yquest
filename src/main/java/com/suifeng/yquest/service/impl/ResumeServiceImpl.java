package com.suifeng.yquest.service.impl;

import com.suifeng.yquest.api.common.PageResult;
import com.suifeng.yquest.api.enums.IsDeletedFlagEnum;
import com.suifeng.yquest.entity.Resume;
import com.suifeng.yquest.dao.ResumeDao;
import com.suifeng.yquest.service.ResumeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * (Resume)表服务实现类
 */
@Service("resumeService")
public class ResumeServiceImpl implements ResumeService {
    @Resource
    private ResumeDao resumeDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Resume queryById(Long id) {
        return this.resumeDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param resume 筛选条件
     * @return 查询结果
     */
    @Override
    public PageResult<Resume> queryByPage(Resume resume) {
        PageResult<Resume> pageResult = new PageResult<>();
        pageResult.setPageNo(resume.getPageNo());
        pageResult.setPageSize(resume.getPageSize());
        int start = (resume.getPageNo() - 1) * resume.getPageSize();

        int count = resumeDao.countByCondition(resume);
        if (count == 0) {
            return pageResult;
        }
        List<Resume> resumeList = resumeDao.queryPage(resume, start, resume.getPageSize());
        pageResult.setRecords(resumeList);
        pageResult.setTotal(count);
        return pageResult;
    }

    /**
     * 条件查询
     *
     * @param resume 筛选条件
     * @return 结果列表
     */
    @Override
    public List<Resume> queryAll(Resume resume) {
        return resumeDao.queryAll(resume);
    }

    /**
     * 新增数据
     *
     * @param resume 实例对象
     * @return 实例对象
     */
    @Override
    public boolean insert(Resume resume) {
        resume.setStatus(0); // 待处理
        resume.setCreatedTime(new Date());
        resume.setUpdatedTime(new Date());
        resume.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        return this.resumeDao.insert(resume) > 0;
    }

    /**
     * 修改数据
     *
     * @param resume 实例对象
     * @return 实例对象
     */
    @Override
    public boolean update(Resume resume) {
        resume.setUpdatedTime(new Date());
        return this.resumeDao.update(resume) > 0;
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.resumeDao.deleteById(id) > 0;
    }

    /**
     * 根据职位ID查询简历
     *
     * @param jobId 职位ID
     * @param pageNo 页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    @Override
    public PageResult<Resume> queryByJobId(Long jobId, int pageNo, int pageSize) {
        PageResult<Resume> pageResult = new PageResult<>();
        pageResult.setPageNo(pageNo);
        pageResult.setPageSize(pageSize);
        int start = (pageNo - 1) * pageSize;

        int count = resumeDao.countByJobId(jobId);
        if (count == 0) {
            return pageResult;
        }
        List<Resume> resumeList = resumeDao.queryByJobId(jobId, start, pageSize);
        pageResult.setRecords(resumeList);
        pageResult.setTotal(count);
        return pageResult;
    }

    /**
     * 根据用户ID查询简历
     *
     * @param userId 用户ID
     * @param pageNo 页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    @Override
    public PageResult<Resume> queryByUserId(Long userId, int pageNo, int pageSize) {
        PageResult<Resume> pageResult = new PageResult<>();
        pageResult.setPageNo(pageNo);
        pageResult.setPageSize(pageSize);
        int start = (pageNo - 1) * pageSize;

        int count = resumeDao.countByUserId(userId);
        if (count == 0) {
            return pageResult;
        }
        List<Resume> resumeList = resumeDao.queryByUserId(userId, start, pageSize);
        pageResult.setRecords(resumeList);
        pageResult.setTotal(count);
        return pageResult;
    }

    /**
     * 根据职位ID和用户ID查询简历
     *
     * @param jobId 职位ID
     * @param userId 用户ID
     * @return 简历
     */
    @Override
    public Resume queryByJobIdAndUserId(Long jobId, Long userId) {
        return resumeDao.queryByJobIdAndUserId(jobId, userId);
    }

    /**
     * 处理简历状态
     *
     * @param id 简历ID
     * @param status 状态（1：通过，2：拒绝）
     * @return 是否成功
     */
    @Override
    public boolean processResume(Long id, Integer status) {
        Resume resume = new Resume();
        resume.setId(id);
        resume.setStatus(status);
        resume.setUpdatedTime(new Date());
        return this.resumeDao.update(resume) > 0;
    }

}
