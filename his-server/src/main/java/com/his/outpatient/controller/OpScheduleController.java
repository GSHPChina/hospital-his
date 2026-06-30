package com.his.outpatient.controller;

import com.his.common.result.R;
import com.his.outpatient.entity.OpSchedule;
import com.his.outpatient.service.OpScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/outpatient/schedules")
@RequiredArgsConstructor
public class OpScheduleController {

    private final OpScheduleService scheduleService;

    @GetMapping
    public R<List<OpSchedule>> list(
            @RequestParam(required = false) Long deptId,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return R.ok(scheduleService.listByDeptAndDate(deptId, date));
    }

    @PostMapping
    public R<Void> add(@RequestBody OpSchedule schedule) {
        schedule.setUsedNum(0);
        schedule.setStatus(1);
        scheduleService.save(schedule);
        return R.ok();
    }

    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @RequestBody OpSchedule schedule) {
        schedule.setId(id);
        scheduleService.updateById(schedule);
        return R.ok();
    }
}
