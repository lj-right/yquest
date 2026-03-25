package com.suifeng.yquest.service.impl;

import com.suifeng.yquest.api.common.PageResult;
import com.suifeng.yquest.api.enums.IsDeletedFlagEnum;
import com.suifeng.yquest.entity.InterviewRecord;
import com.suifeng.yquest.dao.InterviewRecordDao;
import com.suifeng.yquest.service.InterviewRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * (InterviewRecord)表服务实现类
 */
@Service("interviewRecordService")
public class  InterviewRecordServiceImpl implements InterviewRecordService {
    @Resource
    private InterviewRecordDao interviewRecordDao;

    /**
     *
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public InterviewRecord queryById(Long id) {
        return this.interviewRecordDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param interviewRecord 筛选条件
     * @return 查询结果
     */
    @Override
    public PageResult<InterviewRecord> queryByPage(InterviewRecord interviewRecord) {
        PageResult<InterviewRecord> pageResult = new PageResult<>();
        pageResult.setPageNo(interviewRecord.getPageNo());
        pageResult.setPageSize(interviewRecord.getPageSize());
        int start = (interviewRecord.getPageNo() - 1) * interviewRecord.getPageSize();

        int count = interviewRecordDao.countByCondition(interviewRecord);
        if (count == 0) {
            return pageResult;
        }
        List<InterviewRecord> recordList = interviewRecordDao.queryPage(interviewRecord, start, interviewRecord.getPageSize());
        pageResult.setRecords(recordList);
        pageResult.setTotal(count);
        return pageResult;
    }

    /**
     * 条件查询
     *
     * @param interviewRecord 筛选条件
     * @return 结果列表
     */
    @Override
    public List<InterviewRecord> queryAll(InterviewRecord interviewRecord) {
        return interviewRecordDao.queryAll(interviewRecord);
    }

    /**
     * 新增数据
     *
     * @param interviewRecord 实例对象
     * @return 实例对象
     */
    @Override
    public boolean insert(InterviewRecord interviewRecord) {
        interviewRecord.setResult(0); // 待评估
        interviewRecord.setCreatedTime(new Date());
        interviewRecord.setUpdatedTime(new Date());
        interviewRecord.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        return this.interviewRecordDao.insert(interviewRecord) > 0;
    }

    /**
     * 修改数据
     *
     * @param interviewRecord 实例对象
     * @return 实例对象
     */
    @Override
    public boolean update(InterviewRecord interviewRecord) {
        interviewRecord.setUpdatedTime(new Date());
        return this.interviewRecordDao.update(interviewRecord) > 0;
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.interviewRecordDao.deleteById(id) > 0;
    }

    /**
     * 根据流程ID查询面试记录
     *
     * @param processId 流程ID
     * @return 面试记录列表
     */
    @Override
    public List<InterviewRecord> queryByProcessId(Long processId) {
        return interviewRecordDao.queryByProcessId(processId);
    }

    /**
     * 根据职位ID查询面试记录
     *
     * @param jobId 职位ID
     * @param pageNo 页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    @Override
    public PageResult<InterviewRecord> queryByJobId(Long jobId, int pageNo, int pageSize) {
        PageResult<InterviewRecord> pageResult = new PageResult<>();
        pageResult.setPageNo(pageNo);
        pageResult.setPageSize(pageSize);
        int start = (pageNo - 1) * pageSize;

        int count = interviewRecordDao.countByJobId(jobId);
        if (count == 0) {
            return pageResult;
        }
        List<InterviewRecord> recordList = interviewRecordDao.queryByJobId(jobId, start, pageSize);
        pageResult.setRecords(recordList);
        pageResult.setTotal(count);
        return pageResult;
    }

    /**
     * 根据面试官ID查询面试记录
     *
     * @param interviewerId 面试官ID
     * @param pageNo 页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    @Override
    public PageResult<InterviewRecord> queryByInterviewerId(Long interviewerId, int pageNo, int pageSize) {
        PageResult<InterviewRecord> pageResult = new PageResult<>();
        pageResult.setPageNo(pageNo);
        pageResult.setPageSize(pageSize);
        int start = (pageNo - 1) * pageSize;

        int count = interviewRecordDao.countByInterviewerId(interviewerId);
        if (count == 0) {
            return pageResult;
        }
        List<InterviewRecord> recordList = interviewRecordDao.queryByInterviewerId(interviewerId, start, pageSize);
        pageResult.setRecords(recordList);
        pageResult.setTotal(count);
        return pageResult;
    }

    /**
     * 评估面试结果
     *
     * @param id 记录ID
     * @param result 结果（1：通过，2：未通过）
     * @param comments 评语
     * @return 操作结果
     */
    @Override
    public boolean evaluateInterview(Long id, Integer result, String comments) {
        InterviewRecord record = new InterviewRecord();
        record.setId(id);
        record.setResult(result);
        record.setComments(comments);
        record.setUpdatedTime(new Date());
        return this.interviewRecordDao.update(record) > 0;
    }

}
