package com.his.emr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.his.emr.entity.EmrRecord;

/**
 * 病历记录服务接口
 */
public interface EmrRecordService extends IService<EmrRecord> {

    /**
     * 提交病历
     *
     * @param id 病历记录ID
     * @return 是否成功
     */
    boolean submit(Long id);

    /**
     * 审核病历
     *
     * @param id      病历记录ID
     * @param auditBy 审核人ID
     * @return 是否成功
     */
    boolean audit(Long id, Long auditBy);

    /**
     * 根据挂号ID获取病历记录
     *
     * @param registerId 挂号ID
     * @return 病历记录
     */
    EmrRecord getByRegisterId(Long registerId);
}
