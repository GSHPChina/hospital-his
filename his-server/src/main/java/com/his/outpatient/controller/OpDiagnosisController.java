package com.his.outpatient.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.his.common.result.R;
import com.his.outpatient.entity.OpDiagnosis;
import com.his.outpatient.service.OpDiagnosisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctor/diagnoses")
@RequiredArgsConstructor
public class OpDiagnosisController {

    private final OpDiagnosisService diagnosisService;

    @GetMapping
    public R<List<OpDiagnosis>> listByRegisterId(@RequestParam Long registerId) {
        return R.ok(diagnosisService.list(
                new LambdaQueryWrapper<OpDiagnosis>()
                        .eq(OpDiagnosis::getRegisterId, registerId)));
    }

    @PostMapping
    public R<Void> add(@RequestBody OpDiagnosis diagnosis) {
        diagnosisService.save(diagnosis);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        diagnosisService.removeById(id);
        return R.ok();
    }
}
