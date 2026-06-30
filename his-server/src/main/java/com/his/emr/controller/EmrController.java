package com.his.emr.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.his.common.result.R;
import com.his.emr.entity.EmrQualityCheck;
import com.his.emr.entity.EmrRecord;
import com.his.emr.entity.EmrTemplate;
import com.his.emr.service.EmrQualityCheckService;
import com.his.emr.service.EmrRecordService;
import com.his.emr.service.EmrTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 电子病历控制器
 */
@RestController
@RequestMapping("/api/emr")
public class EmrController {

    @Autowired
    private EmrTemplateService emrTemplateService;

    @Autowired
    private EmrRecordService emrRecordService;

    @Autowired
    private EmrQualityCheckService emrQualityCheckService;

    /**
     * 获取病历模板列表
     */
    @GetMapping("/templates")
    public R<Page<EmrTemplate>> listTemplates(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String category) {

        LambdaQueryWrapper<EmrTemplate> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(category)) {
            wrapper.eq(EmrTemplate::getCategory, category);
        }
        wrapper.eq(EmrTemplate::getStatus, 1);
        wrapper.orderByDesc(EmrTemplate::getCreateTime);

        Page<EmrTemplate> page = emrTemplateService.page(new Page<>(pageNum, pageSize), wrapper);
        return R.ok(page);
    }

    /**
     * 添加病历模板
     */
    @PostMapping("/templates")
    public R<Void> addTemplate(@RequestBody EmrTemplate template) {
        template.setCreateTime(LocalDateTime.now());
        template.setUpdateTime(LocalDateTime.now());
        template.setStatus(1);
        emrTemplateService.save(template);
        return R.ok();
    }

    /**
     * 获取病历记录列表
     */
    @GetMapping("/records")
    public R<Page<EmrRecord>> listRecords(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Integer status) {

        LambdaQueryWrapper<EmrRecord> wrapper = new LambdaQueryWrapper<>();
        if (status != null) {
            wrapper.eq(EmrRecord::getStatus, status);
        }
        wrapper.orderByDesc(EmrRecord::getCreateTime);

        Page<EmrRecord> page = emrRecordService.page(new Page<>(pageNum, pageSize), wrapper);
        return R.ok(page);
    }

    /**
     * 添加病历记录
     */
    @PostMapping("/records")
    public R<EmrRecord> addRecord(@RequestBody EmrRecord record) {
        // 生成病历号：EMR + 时间戳 + 随机数
        String recordNo = "EMR" + System.currentTimeMillis() + UUID.randomUUID().toString().substring(0, 4);
        record.setRecordNo(recordNo);
        record.setStatus(0); // 草稿状态
        record.setCreateTime(LocalDateTime.now());
        record.setUpdateTime(LocalDateTime.now());
        emrRecordService.save(record);
        return R.ok(record);
    }

    /**
     * 更新病历记录
     */
    @PutMapping("/records/{id}")
    public R<Void> updateRecord(@PathVariable Long id, @RequestBody EmrRecord record) {
        record.setId(id);
        record.setUpdateTime(LocalDateTime.now());
        emrRecordService.updateById(record);
        return R.ok();
    }

    /**
     * 提交病历
     */
    @PutMapping("/records/{id}/submit")
    public R<Void> submitRecord(@PathVariable Long id) {
        boolean success = emrRecordService.submit(id);
        return success ? R.ok() : R.fail("提交失败，病历状态不正确");
    }

    /**
     * 审核病历
     */
    @PutMapping("/records/{id}/audit")
    public R<Void> auditRecord(@PathVariable Long id, @RequestParam Long auditBy) {
        boolean success = emrRecordService.audit(id, auditBy);
        return success ? R.ok() : R.fail("审核失败，病历状态不正确");
    }

    /**
     * 根据挂号ID获取病历记录
     */
    @GetMapping("/records/register/{registerId}")
    public R<EmrRecord> getRecordByRegister(@PathVariable Long registerId) {
        EmrRecord record = emrRecordService.getByRegisterId(registerId);
        return record != null ? R.ok(record) : R.fail("未找到病历记录");
    }

    /**
     * 添加质量检查
     */
    @PostMapping("/quality/check")
    public R<Void> addQualityCheck(@RequestBody EmrQualityCheck qualityCheck) {
        qualityCheck.setCheckTime(LocalDateTime.now());
        qualityCheck.setCreateTime(LocalDateTime.now());
        qualityCheck.setUpdateTime(LocalDateTime.now());
        emrQualityCheckService.save(qualityCheck);
        return R.ok();
    }

    /**
     * 获取质量检查列表
     */
    @GetMapping("/quality/list")
    public R<Page<EmrQualityCheck>> listQualityChecks(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Long recordId) {

        LambdaQueryWrapper<EmrQualityCheck> wrapper = new LambdaQueryWrapper<>();
        if (recordId != null) {
            wrapper.eq(EmrQualityCheck::getRecordId, recordId);
        }
        wrapper.orderByDesc(EmrQualityCheck::getCreateTime);

        Page<EmrQualityCheck> page = emrQualityCheckService.page(new Page<>(pageNum, pageSize), wrapper);
        return R.ok(page);
    }
}
