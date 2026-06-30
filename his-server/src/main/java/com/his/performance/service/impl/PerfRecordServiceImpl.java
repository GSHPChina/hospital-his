package com.his.performance.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.his.common.exception.BusinessException;
import com.his.performance.entity.PerfRecord;
import com.his.performance.entity.PerfScoreDetail;
import com.his.performance.mapper.PerfRecordMapper;
import com.his.performance.mapper.PerfScoreDetailMapper;
import com.his.performance.service.PerfRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PerfRecordServiceImpl extends ServiceImpl<PerfRecordMapper, PerfRecord> implements PerfRecordService {

    private final PerfScoreDetailMapper scoreDetailMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void evaluate(Long templateId, Long empId, Integer year, Integer quarter, List<PerfScoreDetail> details) {
        if (details == null || details.isEmpty()) {
            throw new BusinessException("Score details cannot be empty");
        }

        int totalScore = 0;
        for (PerfScoreDetail detail : details) {
            if (detail.getScore() == null) {
                throw new BusinessException("Score cannot be null");
            }
            totalScore += detail.getScore();
        }

        PerfRecord record = new PerfRecord();
        record.setTemplateId(templateId);
        record.setEmpId(empId);
        record.setYear(year);
        record.setQuarter(quarter);
        record.setTotalScore(totalScore);
        record.setLevel(resolveLevel(totalScore));
        record.setEvaluateTime(LocalDateTime.now());
        record.setStatus(1);
        this.save(record);

        for (PerfScoreDetail detail : details) {
            detail.setRecordId(record.getId());
            scoreDetailMapper.insert(detail);
        }
    }

    private String resolveLevel(int totalScore) {
        if (totalScore >= 90) {
            return "A";
        } else if (totalScore >= 80) {
            return "B";
        } else if (totalScore >= 70) {
            return "C";
        } else if (totalScore >= 60) {
            return "D";
        } else {
            return "E";
        }
    }
}
