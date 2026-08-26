package com.suifeng.yquest.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Preconditions;
import com.suifeng.yquest.api.common.PageInfo;
import com.suifeng.yquest.api.common.PageResult;
import com.suifeng.yquest.api.enums.IsDeletedFlagEnum;
import com.suifeng.yquest.api.enums.ReferApplyEventEnum;
import com.suifeng.yquest.api.enums.ReferApplyStatusEnum;
import com.suifeng.yquest.api.req.GetReferApplyReq;
import com.suifeng.yquest.api.req.SaveReferApplyReq;
import com.suifeng.yquest.api.vo.ReferApplyVO;
import com.suifeng.yquest.api.vo.ReferFlowRecordVO;
import com.suifeng.yquest.constants.ReferConstant;
import com.suifeng.yquest.dao.ReferApplyMapper;
import com.suifeng.yquest.entity.AuthUser;
import com.suifeng.yquest.entity.Job;
import com.suifeng.yquest.entity.Refer;
import com.suifeng.yquest.entity.ReferApply;
import com.suifeng.yquest.entity.ReferFlowRecord;
import com.suifeng.yquest.entity.Resume;
import com.suifeng.yquest.handler.refer.ReferEventHandler;
import com.suifeng.yquest.handler.refer.ReferEventHandlerFactory;
import com.suifeng.yquest.service.AuthUserService;
import com.suifeng.yquest.service.JobService;
import com.suifeng.yquest.service.ReferApplyService;
import com.suifeng.yquest.service.ReferFlowRecordService;
import com.suifeng.yquest.service.ReferService;
import com.suifeng.yquest.service.ResumeService;
import com.suifeng.yquest.utils.LoginUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 内推申请 服务实现类（状态机统一流转入口）
 */
@Slf4j
@Service
public class ReferApplyServiceImpl extends ServiceImpl<ReferApplyMapper, ReferApply> implements ReferApplyService {

    @Resource
    private ReferFlowRecordService referFlowRecordService;

    @Resource
    private ReferEventHandlerFactory referEventHandlerFactory;

    @Resource
    private AuthUserService authUserService;

    @Resource
    private ReferService referService;

    @Resource
    private JobService jobService;

    @Resource
    private ResumeService resumeService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean submitApply(SaveReferApplyReq req) {
        Preconditions.checkArgument(Objects.nonNull(req), "参数不能为空！");
        Preconditions.checkArgument(Objects.nonNull(req.getJobId()), "职位ID不能为空！");
        Preconditions.checkArgument(Objects.nonNull(req.getResumeId()), "简历ID不能为空！");
        // 1. 解析当前登录用户
        AuthUser currentUser = getCurrentUser();
        // 2. 校验内推码（职位大厅投递可不带内推码）
        Refer refer = null;
        if (Objects.nonNull(req.getReferId())) {
            refer = referService.queryById(req.getReferId());
            // refer.isDeleted 兼作审核状态：0=已审核上架，1=待审核/下架
            Preconditions.checkArgument(Objects.nonNull(refer)
                    && Objects.equals(refer.getIsDeleted(), IsDeletedFlagEnum.UN_DELETED.getCode()), "内推码不存在或未通过审核！");
        }
        // 3. 校验职位（须为已发布状态）
        Job job = jobService.queryById(req.getJobId());
        Preconditions.checkArgument(Objects.nonNull(job)
                && Objects.equals(job.getIsDeleted(), IsDeletedFlagEnum.UN_DELETED.getCode()), "职位不存在！");
        Preconditions.checkArgument(Objects.equals(job.getStatus(), 1), "职位未开放投递！");
        // 内推码与职位归属公司必须一致（防止用A公司内推码投B公司职位导致内推人归属错乱）
        if (Objects.nonNull(refer) && Objects.nonNull(refer.getCompanyId())
                && !Objects.equals(refer.getCompanyId(), job.getCompanyId())) {
            Preconditions.checkArgument(false, "该内推码与投递职位不属于同一公司，请直接投递职位！");
        }
        // 4. 校验简历归属
        Resume resume = resumeService.queryById(req.getResumeId());
        Preconditions.checkArgument(Objects.nonNull(resume), "简历不存在！");
        Preconditions.checkArgument(Objects.equals(resume.getUserId(), currentUser.getId()), "只能使用自己的简历投递！");
        // 5. 重复投递校验
        int count = super.count(Wrappers.<ReferApply>lambdaQuery()
                .eq(ReferApply::getJobSeekerId, currentUser.getId())
                .eq(ReferApply::getJobId, req.getJobId())
                .eq(ReferApply::getIsDeleted, IsDeletedFlagEnum.UN_DELETED.getCode()));
        Preconditions.checkArgument(count == 0, "已投递过该职位，请勿重复投递！");
        // 6. 确定内推人：携带内推码时优先内推码归属人，否则走职位发布人
        Long referrerId = Objects.nonNull(refer) && Objects.nonNull(refer.getReferUserId())
                ? refer.getReferUserId() : job.getPublishUserId();
        Preconditions.checkArgument(Objects.nonNull(referrerId), "内推人信息缺失，无法投递！");
        // 不能内推自己（自己的内推码或自己发布的职位）
        //Preconditions.checkArgument(!Objects.equals(referrerId, currentUser.getId()), "不能使用自己的内推码或投递自己发布的职位！");
        // 7. 落库
        ReferApply apply = new ReferApply();
        apply.setReferId(req.getReferId());
        apply.setCompanyId(job.getCompanyId());
        apply.setJobId(req.getJobId());
        apply.setResumeId(req.getResumeId());
        apply.setJobSeekerId(currentUser.getId());
        apply.setJobSeekerName(currentUser.getNickName());
        apply.setJobSeekerEmail(currentUser.getEmail());
        apply.setReferrerId(referrerId);
        apply.setCurrentStatus(ReferApplyStatusEnum.SUBMITTED.getCode());
        apply.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        super.save(apply);
        // 8. 首条流转记录
        saveFlowRecord(apply.getId(), null, ReferApplyStatusEnum.SUBMITTED, "submit",
                currentUser.getId(), currentUser.getNickName(), null, null);
        // 9. 职位申请次数+1
        jobService.incrementApplyCount(req.getJobId());
        if (log.isInfoEnabled()) {
            log.info("内推申请提交成功，申请ID{}，职位ID{}", apply.getId(), req.getJobId());
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean advance(Long applyId, ReferApplyEventEnum event, Long operatorId, String operatorName, String operatorRole, String remark) {
        // 1. 校验事件
        Preconditions.checkArgument(Objects.nonNull(event), "事件不能为空！");
        // 2. 查询申请
        ReferApply apply = super.getById(applyId);
        Preconditions.checkArgument(Objects.nonNull(apply)
                && Objects.equals(apply.getIsDeleted(), IsDeletedFlagEnum.UN_DELETED.getCode()), "内推申请不存在！");
        ReferApplyStatusEnum currentStatus = ReferApplyStatusEnum.getByCode(apply.getCurrentStatus());
        Preconditions.checkArgument(Objects.nonNull(currentStatus), "当前状态非法！");
        // 3. 权限校验：求职者事件（撤回/Offer决定）以归属校验为准——任何用户都可投递成为申请人，
        //    故不要求持有求职者角色标签，仅限申请人本人操作；内推人事件须角色+归属双校验
        boolean isSeekerEvent = Arrays.equals(event.getAllowedRoles(), ReferConstant.ROLES_SEEKER);
        if (!isSeekerEvent) {
            Preconditions.checkArgument(event.allowRole(operatorRole), "当前角色无权执行该操作！");
        }
        // 3.5 归属校验：求职者事件须申请人本人操作；内推人事件须该申请的内推人操作（管理员放行）
        if (isSeekerEvent) {
            Preconditions.checkArgument(Objects.equals(operatorId, apply.getJobSeekerId()), "只能操作本人的内推申请！");
        } else if (event.allowRole(ReferConstant.ROLE_REFERRER)
                && !Objects.equals(operatorRole, ReferConstant.ROLE_ADMIN)) {
            Preconditions.checkArgument(Objects.equals(operatorId, apply.getReferrerId()), "只能处理自己收到的内推申请！");
        }
        // 4. 校验状态转移合法性
        Preconditions.checkArgument(event.canFrom(currentStatus),
                "当前状态[" + currentStatus.getDesc() + "]不允许执行操作[" + event.name() + "]！");
        // 5. 校验拒绝类事件必须填写原因
        Preconditions.checkArgument(!(event.isNeedReason() && StringUtils.isBlank(remark)), "请填写原因！");
        // 6. 计算目标状态
        ReferApplyStatusEnum toStatus = event.getToStatus(currentStatus);
        Preconditions.checkArgument(Objects.nonNull(toStatus), "状态流转目标非法！");
        // 7. 执行事件副作用（同事务，失败回滚）
        ReferEventHandler handler = referEventHandlerFactory.getHandler(event);
        if (Objects.nonNull(handler)) {
            handler.apply(apply, currentStatus);
        }
        // 8. 条件更新状态（乐观锁，防止并发重复流转）
        String rejectReason = event.isNeedReason() ? remark : apply.getRejectReason();
        int rows = super.getBaseMapper().casUpdateStatus(applyId, currentStatus.getCode(), toStatus.getCode(), rejectReason);
        Preconditions.checkArgument(rows > 0, "状态流转失败，申请状态已变更，请刷新后重试！");
        // 9. 落流转审计记录
        saveFlowRecord(applyId, currentStatus, toStatus, event.name(), operatorId, operatorName, operatorRole, remark);
        if (log.isInfoEnabled()) {
            log.info("内推状态流转成功{} -> {}，申请ID{}", currentStatus.getDesc(), toStatus.getDesc(), applyId);
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean advance(Long applyId, String eventName, String remark) {
        ReferApplyEventEnum event = ReferApplyEventEnum.getByName(eventName);
        Preconditions.checkArgument(Objects.nonNull(event), "事件非法！");
        AuthUser operator = getCurrentUser();
        String operatorRole = resolveOperatorRole(event);
        return this.advance(applyId, event, operator.getId(), operator.getNickName(), operatorRole, remark);
    }

    @Override
    public PageResult<ReferApplyVO> getMyApplies(GetReferApplyReq req) {
        Preconditions.checkArgument(Objects.nonNull(req), "参数不能为空！");
        AuthUser currentUser = getCurrentUser();
        LambdaQueryWrapper<ReferApply> query = Wrappers.<ReferApply>lambdaQuery()
                .eq(ReferApply::getJobSeekerId, currentUser.getId())
                .eq(ReferApply::getIsDeleted, IsDeletedFlagEnum.UN_DELETED.getCode())
                .eq(Objects.nonNull(req.getCurrentStatus()), ReferApply::getCurrentStatus, req.getCurrentStatus())
                .orderByDesc(ReferApply::getId);
        return pageQuery(req.getPageInfo(), query);
    }

    @Override
    public PageResult<ReferApplyVO> getReferrerApplies(GetReferApplyReq req) {
        Preconditions.checkArgument(Objects.nonNull(req), "参数不能为空！");
        AuthUser currentUser = getCurrentUser();
        LambdaQueryWrapper<ReferApply> query = Wrappers.<ReferApply>lambdaQuery()
                .eq(ReferApply::getReferrerId, currentUser.getId())
                .eq(ReferApply::getIsDeleted, IsDeletedFlagEnum.UN_DELETED.getCode())
                .eq(Objects.nonNull(req.getCurrentStatus()), ReferApply::getCurrentStatus, req.getCurrentStatus())
                .orderByDesc(ReferApply::getId);
        return pageQuery(req.getPageInfo(), query);
    }

    @Override
    public List<ReferFlowRecord> getFlowRecords(Long applyId) {
        return referFlowRecordService.list(Wrappers.<ReferFlowRecord>lambdaQuery()
                .eq(ReferFlowRecord::getReferApplyId, applyId)
                .eq(ReferFlowRecord::getIsDeleted, IsDeletedFlagEnum.UN_DELETED.getCode())
                .orderByAsc(ReferFlowRecord::getCreatedTime)
                .orderByAsc(ReferFlowRecord::getId));
    }

    @Override
    public List<ReferFlowRecordVO> getFlowRecordVOs(Long applyId) {
        List<ReferFlowRecord> records = getFlowRecords(applyId);
        return records.stream().map(item -> {
            ReferFlowRecordVO vo = new ReferFlowRecordVO();
            vo.setId(item.getId());
            vo.setFromStatus(item.getFromStatus());
            vo.setFromStatusDesc(getStatusDesc(item.getFromStatus()));
            vo.setToStatus(item.getToStatus());
            vo.setToStatusDesc(getStatusDesc(item.getToStatus()));
            vo.setEvent(item.getEvent());
            vo.setOperatorName(item.getOperatorName());
            vo.setOperatorRole(item.getOperatorRole());
            vo.setRemark(item.getRemark());
            vo.setCreatedTime(Objects.nonNull(item.getCreatedTime()) ? item.getCreatedTime().getTime() : null);
            return vo;
        }).collect(Collectors.toList());
    }

    /**
     * 分页查询并组装VO
     */
    private PageResult<ReferApplyVO> pageQuery(PageInfo pageInfo, LambdaQueryWrapper<ReferApply> query) {
        if (Objects.isNull(pageInfo)) {
            pageInfo = new PageInfo();
        }
        Page<ReferApply> page = new Page<>(pageInfo.getPageNo(), pageInfo.getPageSize());
        Page<ReferApply> pageRes = super.page(page, query);
        PageResult<ReferApplyVO> result = new PageResult<>();
        List<ReferApplyVO> list = pageRes.getRecords().stream().map(this::buildVO).collect(Collectors.toList());
        result.setRecords(list);
        result.setTotal((int) pageRes.getTotal());
        result.setPageSize(pageInfo.getPageSize());
        result.setPageNo(pageInfo.getPageNo());
        return result;
    }

    /**
     * 实体转VO（补充职位名称与公司名称）
     */
    private ReferApplyVO buildVO(ReferApply apply) {
        ReferApplyVO vo = new ReferApplyVO();
        vo.setId(apply.getId());
        vo.setReferId(apply.getReferId());
        vo.setCompanyId(apply.getCompanyId());
        vo.setJobId(apply.getJobId());
        vo.setResumeId(apply.getResumeId());
        vo.setJobSeekerId(apply.getJobSeekerId());
        vo.setJobSeekerName(apply.getJobSeekerName());
        vo.setJobSeekerEmail(apply.getJobSeekerEmail());
        vo.setReferrerId(apply.getReferrerId());
        vo.setCurrentStatus(apply.getCurrentStatus());
        vo.setCurrentStatusDesc(getStatusDesc(apply.getCurrentStatus()));
        vo.setRejectReason(apply.getRejectReason());
        vo.setCreatedTime(Objects.nonNull(apply.getCreatedTime()) ? apply.getCreatedTime().getTime() : null);
        Job job = jobService.queryById(apply.getJobId());
        if (Objects.nonNull(job)) {
            vo.setJobTitle(job.getJobTitle());
            vo.setCompanyName(job.getCompanyName());
        }
        return vo;
    }

    /**
     * 保存流转记录
     */
    private void saveFlowRecord(Long applyId, ReferApplyStatusEnum fromStatus, ReferApplyStatusEnum toStatus,
                                String event, Long operatorId, String operatorName, String operatorRole, String remark) {
        ReferFlowRecord flowRecord = new ReferFlowRecord();
        flowRecord.setReferApplyId(applyId);
        flowRecord.setFromStatus(Objects.nonNull(fromStatus) ? fromStatus.getCode() : null);
        flowRecord.setToStatus(toStatus.getCode());
        flowRecord.setEvent(event.toLowerCase());
        flowRecord.setOperatorId(operatorId);
        flowRecord.setOperatorName(operatorName);
        flowRecord.setOperatorRole(operatorRole);
        flowRecord.setRemark(remark);
        flowRecord.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        referFlowRecordService.save(flowRecord);
    }

    /**
     * 解析当前登录用户
     */
    private AuthUser getCurrentUser() {
        AuthUser query = new AuthUser();
        query.setUserName(LoginUtil.getLoginId());
        AuthUser user = authUserService.queryByName(query);
        Preconditions.checkArgument(Objects.nonNull(user), "当前用户不存在！");
        return user;
    }

    /**
     * 从Sa-Token角色列表中解析出满足事件权限的角色
     * 角色缓存未命中时回源数据库（兼容SQL导入用户/缓存丢失场景）
     */
    private String resolveOperatorRole(ReferApplyEventEnum event) {
        List<String> roleList = StpUtil.getRoleList();
        if (CollectionUtils.isEmpty(roleList)) {
            roleList = authUserService.queryMyRoleKeys();
        }
        if (CollectionUtils.isEmpty(roleList)) {
            return StringUtils.EMPTY;
        }
        for (String allowedRole : event.getAllowedRoles()) {
            if (roleList.contains(allowedRole)) {
                return allowedRole;
            }
        }
        return roleList.get(0);
    }

    /**
     * 获取状态描述
     */
    private String getStatusDesc(Integer statusCode) {
        if (Objects.isNull(statusCode)) {
            return null;
        }
        ReferApplyStatusEnum statusEnum = ReferApplyStatusEnum.getByCode(statusCode);
        return Objects.nonNull(statusEnum) ? statusEnum.getDesc() : null;
    }
}
