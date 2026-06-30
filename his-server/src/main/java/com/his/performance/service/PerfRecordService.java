package com.his.performance.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.his.performance.entity.PerfRecord;
import com.his.performance.entity.PerfScoreDetail;

import java.util.List;

public interface PerfRecordService extends IService<PerfRecord> {
    void evaluate(Long templateId, Long empId, Integer year, Integer quarter, List<PerfScoreDetail> details);
}
