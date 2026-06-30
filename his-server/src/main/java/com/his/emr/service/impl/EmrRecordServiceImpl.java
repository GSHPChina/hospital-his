package com.his.emr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.his.emr.entity.EmrRecord;
import com.his.emr.mapper.EmrRecordMapper;
import com.his.emr.service.EmrRecordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 病历记录服务实现类
 */
@Service
public class EmrRecordServiceImpl extends ServiceImpl<EmrRecordMapper, EmrRecord> implements EmrRecordService {

    /**
     * 提交病历
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean submit(Long id) {
        EmrRecord record = getById(id);
        if (record == null) {
            return false;
        }
        // 只有草稿状态的病历才能提交
        if (record.getStatus() != 0) {
            return false;
        }
        record.setStatus(1);
        record.setSubmitTime(LocalDateTime.now());
        return updateById(record);
    }

    /**
     * 审核病历
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean audit(Long id, Long auditBy) {
        EmrRecord record = getById(id);
        if (record == null) {
            return false;
        }
        // 只有已提交状态的病历才能审核
        if (record.getStatus() != 1) {
            return false;
        }
        record.setStatus(2);
        record.setAuditBy(auditBy);
        record.setAuditTime(LocalDateTime.now());
        return updateById(record);
    }

    /**
     * 根据挂号ID获取病历记录
     */
    @Override
    public EmrRecord getByRegisterId(Long registerId) {
        LambdaQueryWrapper<EmrRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EmrRecord::getRegisterId, registerId);
        wrapper.orderByDesc(EmrRecord::getCreateTime);
        wrapper.last("LIMIT 1");
        return getOne(wrapper);
    }
}
