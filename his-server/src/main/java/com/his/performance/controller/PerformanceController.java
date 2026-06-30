package com.his.performance.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.his.common.result.R;
import com.his.performance.entity.PerfIndicator;
import com.his.performance.entity.PerfRecord;
import com.his.performance.entity.PerfScoreDetail;
import com.his.performance.entity.PerfTemplate;
import com.his.performance.mapper.PerfIndicatorMapper;
import com.his.performance.mapper.PerfScoreDetailMapper;
import com.his.performance.service.PerfRecordService;
import com.his.performance.service.PerfTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/performance")
@RequiredArgsConstructor
public class PerformanceController {

    private final PerfTemplateService templateService;
    private final PerfIndicatorMapper indicatorMapper;
    private final PerfRecordService recordService;
    private final PerfScoreDetailMapper scoreDetailMapper;

    @GetMapping("/templates")
    public R<List<PerfTemplate>> listTemplates() {
        return R.ok(templateService.list());
    }

    @GetMapping("/templates/{id}/indicators")
    public R<List<PerfIndicator>> listIndicatorsByTemplate(@PathVariable Long id) {
        return R.ok(indicatorMapper.selectList(
                new LambdaQueryWrapper<PerfIndicator>()
                        .eq(PerfIndicator::getTemplateId, id)
                        .orderByAsc(PerfIndicator::getSortOrder)));
    }

    @PostMapping("/templates")
    public R<Void> addTemplate(@RequestBody PerfTemplate template) {
        templateService.save(template);
        return R.ok();
    }

    @PostMapping("/indicators")
    public R<Void> addIndicator(@RequestBody PerfIndicator indicator) {
        indicatorMapper.insert(indicator);
        return R.ok();
    }

    @GetMapping("/records")
    public R<List<PerfRecord>> listRecords(@RequestParam(required = false) Integer year,
                                           @RequestParam(required = false) Long empId) {
        LambdaQueryWrapper<PerfRecord> wrapper = new LambdaQueryWrapper<>();
        if (year != null) {
            wrapper.eq(PerfRecord::getYear, year);
        }
        if (empId != null) {
            wrapper.eq(PerfRecord::getEmpId, empId);
        }
        wrapper.orderByDesc(PerfRecord::getEvaluateTime);
        return R.ok(recordService.list(wrapper));
    }

    @PostMapping("/evaluate")
    public R<Void> evaluate(@RequestParam Long templateId,
                            @RequestParam Long empId,
                            @RequestParam Integer year,
                            @RequestParam Integer quarter,
                            @RequestBody List<PerfScoreDetail> scores) {
        recordService.evaluate(templateId, empId, year, quarter, scores);
        return R.ok();
    }

    @GetMapping("/results")
    public R<Map<String, Object>> listResults(@RequestParam(required = false) Integer year) {
        LambdaQueryWrapper<PerfRecord> wrapper = new LambdaQueryWrapper<>();
        if (year != null) {
            wrapper.eq(PerfRecord::getYear, year);
        }
        wrapper.eq(PerfRecord::getStatus, 1);
        List<PerfRecord> records = recordService.list(wrapper);

        Map<String, Long> levelStats = new HashMap<>();
        levelStats.put("A", records.stream().filter(r -> "A".equals(r.getLevel())).count());
        levelStats.put("B", records.stream().filter(r -> "B".equals(r.getLevel())).count());
        levelStats.put("C", records.stream().filter(r -> "C".equals(r.getLevel())).count());
        levelStats.put("D", records.stream().filter(r -> "D".equals(r.getLevel())).count());
        levelStats.put("E", records.stream().filter(r -> "E".equals(r.getLevel())).count());

        Map<String, Object> result = new HashMap<>();
        result.put("records", records);
        result.put("levelStats", levelStats);
        return R.ok(result);
    }
}
