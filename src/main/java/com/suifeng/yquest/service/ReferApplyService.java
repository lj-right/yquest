package com.suifeng.yquest.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.suifeng.yquest.api.common.PageResult;
import com.suifeng.yquest.api.enums.ReferApplyEventEnum;
import com.suifeng.yquest.api.req.GetReferApplyReq;
import com.suifeng.yquest.api.req.SaveReferApplyReq;
import com.suifeng.yquest.api.vo.ReferApplyVO;
import com.suifeng.yquest.api.vo.ReferFlowRecordVO;
import com.suifeng.yquest.entity.ReferApply;
import com.suifeng.yquest.entity.ReferFlowRecord;

import java.util.List;

/**
 * 内推申请 服务类（状态机统一流转入口）
 */
public interface ReferApplyService extends IService<ReferApply> {

    /**
     * 求职者提交内推申请（操作人从登录上下文解析）
     *
     * @param req 提交入参
     * @return 是否提交成功
     */
    Boolean submitApply(SaveReferApplyReq req);

    /**
     * 状态机统一流转入口：校验「当前状态+事件+操作角色」合法性 -> 执行事件副作用 -> 条件更新状态 -> 落流转审计记录
     *
     * @param applyId      内推申请ID
     * @param event        触发事件
     * @param operatorId   操作人用户ID
     * @param operatorName 操作人姓名
     * @param operatorRole 操作时角色（auth_role.role_key）
     * @param remark       备注（拒绝原因等）
     * @return 是否流转成功
     */
    Boolean advance(Long applyId, ReferApplyEventEnum event, Long operatorId, String operatorName, String operatorRole, String remark);

    /**
     * 状态机流转（操作人从登录上下文解析，事件名不区分大小写）
     *
     * @param applyId   内推申请ID
     * @param eventName 触发事件名（如 ACCEPT / reject）
     * @param remark    备注（拒绝类事件必填）
     * @return 是否流转成功
     */
    Boolean advance(Long applyId, String eventName, String remark);

    /**
     * 求职者查询我的内推申请（分页）
     *
     * @param req 查询入参
     * @return 分页结果
     */
    PageResult<ReferApplyVO> getMyApplies(GetReferApplyReq req);

    /**
     * 内推人查询收到的内推申请（分页）
     *
     * @param req 查询入参
     * @return 分页结果
     */
    PageResult<ReferApplyVO> getReferrerApplies(GetReferApplyReq req);

    /**
     * 查询指定申请的全部流转记录（按时间正序）
     *
     * @param applyId 内推申请ID
     * @return 流转记录列表
     */
    List<ReferFlowRecord> getFlowRecords(Long applyId);

    /**
     * 查询指定申请的全部流转记录VO（按时间正序）
     *
     * @param applyId 内推申请ID
     * @return 流转记录VO列表
     */
    List<ReferFlowRecordVO> getFlowRecordVOs(Long applyId);
}
