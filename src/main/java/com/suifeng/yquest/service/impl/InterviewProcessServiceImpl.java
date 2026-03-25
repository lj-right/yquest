package com.suifeng.yquest.service.impl;

import com.suifeng.yquest.api.common.PageResult;
import com.suifeng.yquest.api.enums.IsDeletedFlagEnum;
import com.suifeng.yquest.entity.InterviewProcess;
import com.suifeng.yquest.dao.InterviewProcessDao;
import com.suifeng.yquest.service.InterviewProcessService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * (InterviewProcess)表服务实现类
 */
@Service("interviewProcessService")
public class InterviewProcessServiceImpl implements InterviewProcessService {

    @Resource
    private InterviewProcessDao interviewProcessDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public InterviewProcess queryById(Long id) {
        return this.interviewProcessDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param interviewProcess 筛选条件
     * @return 查询结果
     */
    @Override
    public PageResult<InterviewProcess> queryByPage(InterviewProcess interviewProcess) {
        PageResult<InterviewProcess> pageResult = new PageResult<>();
        pageResult.setPageNo(interviewProcess.getPageNo());
        pageResult.setPageSize(interviewProcess.getPageSize());
        int start = (interviewProcess.getPageNo() - 1) * interviewProcess.getPageSize();

        int count = interviewProcessDao.countByCondition(interviewProcess);
        if (count == 0) {
            return pageResult;
        }
        List<InterviewProcess> processList = interviewProcessDao.queryPage(interviewProcess, start, interviewProcess.getPageSize());
        pageResult.setRecords(processList);
        pageResult.setTotal(count);
        return pageResult;
    }

    /**
     * 条件查询
     *
     * @param interviewProcess 筛选条件
     * @return 结果列表
     */
    @Override
    public List<InterviewProcess> queryAll(InterviewProcess interviewProcess) {
        return interviewProcessDao.queryAll(interviewProcess);
    }

    /**
     * 新增数据
     *
     * @param interviewProcess 实例对象
     * @return 实例对象
     */
    @Override
    public boolean insert(InterviewProcess interviewProcess) {
        interviewProcess.setStatus(0); // 进行中
        interviewProcess.setCreatedTime(new Date());
        interviewProcess.setUpdatedTime(new Date());
        interviewProcess.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        return this.interviewProcessDao.insert(interviewProcess) > 0;
    }

    /**
     * 修改数据
     *
     * @param interviewProcess 实例对象
     * @return 实例对象
     */
    @Override
    public boolean update(InterviewProcess interviewProcess) {
        interviewProcess.setUpdatedTime(new Date());
        return this.interviewProcessDao.update(interviewProcess) > 0;
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.interviewProcessDao.deleteById(id) > 0;
    }

    /**
     * 根据简历ID查询流程
     *
     * @param resumeId 简历ID
     * @return 流程
     */
    @Override
    public InterviewProcess queryByResumeId(Long resumeId) {
        return interviewProcessDao.queryByResumeId(resumeId);
    }

    /**
     * 根据职位ID查询流程
     *
     * @param jobId 职位ID
     * @param pageNo 页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    @Override
    public PageResult<InterviewProcess> queryByJobId(Long jobId, int pageNo, int pageSize) {
        PageResult<InterviewProcess> pageResult = new PageResult<>();
        pageResult.setPageNo(pageNo);
        pageResult.setPageSize(pageSize);
        int start = (pageNo - 1) * pageSize;

        int count = interviewProcessDao.countByJobId(jobId);
        if (count == 0) {
            return pageResult;
        }
        List<InterviewProcess> processList = interviewProcessDao.queryByJobId(jobId, start, pageSize);
        pageResult.setRecords(processList);
        pageResult.setTotal(count);
        return pageResult;
    }

    /**
     * 根据面试官ID查询流程
     *
     * @param interviewerId 面试官ID
     * @param pageNo 页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    @Override
    public PageResult<InterviewProcess> queryByInterviewerId(Long interviewerId, int pageNo, int pageSize) {
        PageResult<InterviewProcess> pageResult = new PageResult<>();
        pageResult.setPageNo(pageNo);
        pageResult.setPageSize(pageSize);
        int start = (pageNo - 1) * pageSize;

        int count = interviewProcessDao.countByInterviewerId(interviewerId);
        if (count == 0) {
            return pageResult;
        }
        List<InterviewProcess> processList = interviewProcessDao.queryByInterviewerId(interviewerId, start, pageSize);
        pageResult.setRecords(processList);
        pageResult.setTotal(count);
        return pageResult;
    }

    /**
     * 推进面试流程
     *
     * @param id 流程ID
     * @param nextStage 下一阶段
     * @param nextStageTime 下一阶段时间
     * @param interviewerId 面试官ID
     * @param interviewerName 面试官姓名
     * @return 是否成功
     */
    @Override
    public boolean advanceProcess(Long id, Integer nextStage, Date nextStageTime, Long interviewerId, String interviewerName) {
        InterviewProcess process = new InterviewProcess();
        process.setId(id);
        process.setCurrentStage(nextStage);
        process.setNextStageTime(nextStageTime);
        process.setInterviewerId(interviewerId);
        process.setInterviewerName(interviewerName);
        process.setUpdatedTime(new Date());
        return this.interviewProcessDao.update(process) > 0;
    }

    /**
     * 结束面试流程
     *
     * @param id 流程ID
     * @param status 状态（1：已完成，2：已取消）
     * @return 是否成功
     */
    @Override
    public boolean endProcess(Long id, Integer status) {
        InterviewProcess process = new InterviewProcess();
        process.setId(id);
        process.setStatus(status);
        process.setUpdatedTime(new Date());
        return this.interviewProcessDao.update(process) > 0;
    }

    /**
     * 拒绝面试流程
     *
     * @param id 流程ID
     * @return 是否成功
     */
    @Override
    public boolean rejectProcess(Long id) {
        InterviewProcess process = new InterviewProcess();
        process.setId(id);
        process.setCurrentStage(6); // 已拒绝
        process.setStatus(1); // 已完成
        process.setUpdatedTime(new Date());
        return this.interviewProcessDao.update(process) > 0;
    }

}
