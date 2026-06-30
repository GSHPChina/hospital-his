package com.his.outpatient.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.his.common.result.R;
import com.his.outpatient.entity.OpMedicalRecord;
import com.his.outpatient.service.OpMedicalRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/doctor/medical-records")
@RequiredArgsConstructor
public class OpMedicalRecordController {

    private final OpMedicalRecordService medicalRecordService;

    @GetMapping
    public R<OpMedicalRecord> getByRegisterId(@RequestParam Long registerId) {
        OpMedicalRecord record = medicalRecordService.getOne(
                new LambdaQueryWrapper<OpMedicalRecord>()
                        .eq(OpMedicalRecord::getRegisterId, registerId));
        return R.ok(record);
    }

    @PostMapping
    public R<Void> save(@RequestBody OpMedicalRecord record) {
        record.setStatus(1);
        medicalRecordService.save(record);
        return R.ok();
    }

    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @RequestBody OpMedicalRecord record) {
        record.setId(id);
        medicalRecordService.updateById(record);
        return R.ok();
    }
}
