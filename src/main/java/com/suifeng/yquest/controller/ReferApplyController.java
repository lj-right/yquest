package com.suifeng.yquest.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.suifeng.yquest.api.common.PageResult;
import com.suifeng.yquest.api.common.Result;
import com.suifeng.yquest.api.req.AdvanceReferApplyReq;
import com.suifeng.yquest.api.req.GetFlowRecordReq;
import com.suifeng.yquest.api.req.GetReferApplyReq;
import com.suifeng.yquest.api.req.SaveReferApplyReq;
import com.suifeng.yquest.api.vo.ReferApplyVO;
import com.suifeng.yquest.api.vo.ReferFlowRecordVO;
import com.suifeng.yquest.service.ReferApplyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 内推申请（状态机流转）
 */
@Slf4j
@RestController
@RequestMapping("/refer/apply")
public class ReferApplyController {

    @Resource
    private ReferApplyService referApplyService;

    /**
     * 求职者提交内推申请
     */
    @PostMapping(value = "/save")
    public Result<Boolean> save(@RequestBody SaveReferApplyReq req) {
        try {
            if (log.isInfoEnabled()) {
                log.info("提交内推申请入参{}", JSON.toJSONString(req));
            }
            Preconditions.checkArgument(Objects.nonNull(req), "参数不能为空！");
            Boolean result = referApplyService.submitApply(req);
            if (log.isInfoEnabled()) {
                log.info("提交内推申请{}", JSON.toJSONString(result));
            }
            return Result.ok(result);
        } catch (IllegalArgumentException e) {
            log.error("参数异常！错误原因{}", e.getMessage(), e);
            return Result.fail(e.getMessage());
        } catch (Exception e) {
            log.error("提交内推申请异常！错误原因{}", e.getMessage(), e);
            return Result.fail("提交内推申请异常！");
        }
    }

    /**
     * 内推申请状态流转（统一入口：受理/拒绝/面试推进/Offer/入职等）
     */
    @PostMapping(value = "/advance")
    public Result<Boolean> advance(@RequestBody AdvanceReferApplyReq req) {
        try {
            if (log.isInfoEnabled()) {
                log.info("内推状态流转入参{}", JSON.toJSONString(req));
            }
            Preconditions.checkArgument(Objects.nonNull(req), "参数不能为空！");
            Preconditions.checkArgument(Objects.nonNull(req.getApplyId()), "申请ID不能为空！");
            Preconditions.checkArgument(Objects.nonNull(req.getEvent()), "事件不能为空！");
            Boolean result = referApplyService.advance(req.getApplyId(), req.getEvent(), req.getRemark());
            if (log.isInfoEnabled()) {
                log.info("内推状态流转{}", JSON.toJSONString(result));
            }
            return Result.ok(result);
        } catch (IllegalArgumentException e) {
            log.error("参数异常！错误原因{}", e.getMessage(), e);
            return Result.fail(e.getMessage());
        } catch (Exception e) {
            log.error("内推状态流转异常！错误原因{}", e.getMessage(), e);
            return Result.fail("内推状态流转异常！");
        }
    }

    /**
     * 求职者分页查询我的内推申请
     */
    @PostMapping(value = "/getMyApplies")
    public Result<PageResult<ReferApplyVO>> getMyApplies(@RequestBody GetReferApplyReq req) {
        try {
            if (log.isInfoEnabled()) {
                log.info("我的内推申请入参{}", JSON.toJSONString(req));
            }
            Preconditions.checkArgument(Objects.nonNull(req), "参数不能为空！");
            PageResult<ReferApplyVO> result = referApplyService.getMyApplies(req);
            if (log.isInfoEnabled()) {
                log.info("我的内推申请出参{}", JSON.toJSONString(result));
            }
            return Result.ok(result);
        } catch (IllegalArgumentException e) {
            log.error("参数异常！错误原因{}", e.getMessage(), e);
            return Result.fail(e.getMessage());
        } catch (Exception e) {
            log.error("查询我的内推申请异常！错误原因{}", e.getMessage(), e);
            return Result.fail("查询我的内推申请异常！");
        }
    }

    /**
     * 内推人分页查询收到的内推申请
     */
    @PostMapping(value = "/getReferrerApplies")
    public Result<PageResult<ReferApplyVO>> getReferrerApplies(@RequestBody GetReferApplyReq req) {
        try {
            if (log.isInfoEnabled()) {
                log.info("收到的内推申请入参{}", JSON.toJSONString(req));
            }
            Preconditions.checkArgument(Objects.nonNull(req), "参数不能为空！");
            PageResult<ReferApplyVO> result = referApplyService.getReferrerApplies(req);
            if (log.isInfoEnabled()) {
                log.info("收到的内推申请出参{}", JSON.toJSONString(result));
            }
            return Result.ok(result);
        } catch (IllegalArgumentException e) {
            log.error("参数异常！错误原因{}", e.getMessage(), e);
            return Result.fail(e.getMessage());
        } catch (Exception e) {
            log.error("查询收到的内推申请异常！错误原因{}", e.getMessage(), e);
            return Result.fail("查询收到的内推申请异常！");
        }
    }

    /**
     * 查询内推申请的流转记录（时间线）
     */
    @PostMapping(value = "/getFlowRecords")
    public Result<List<ReferFlowRecordVO>> getFlowRecords(@RequestBody GetFlowRecordReq req) {
        try {
            if (log.isInfoEnabled()) {
                log.info("内推流转记录入参{}", JSON.toJSONString(req));
            }
            Preconditions.checkArgument(Objects.nonNull(req), "参数不能为空！");
            Preconditions.checkArgument(Objects.nonNull(req.getApplyId()), "申请ID不能为空！");
            List<ReferFlowRecordVO> result = referApplyService.getFlowRecordVOs(req.getApplyId());
            if (log.isInfoEnabled()) {
                log.info("内推流转记录出参{}", JSON.toJSONString(result));
            }
            return Result.ok(result);
        } catch (IllegalArgumentException e) {
            log.error("参数异常！错误原因{}", e.getMessage(), e);
            return Result.fail(e.getMessage());
        } catch (Exception e) {
            log.error("查询内推流转记录异常！错误原因{}", e.getMessage(), e);
            return Result.fail("查询内推流转记录异常！");
        }
    }

}
