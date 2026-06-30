package com.his.outpatient.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.his.common.result.R;
import com.his.outpatient.entity.OpPatient;
import com.his.outpatient.service.OpPatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/outpatient/patients")
@RequiredArgsConstructor
public class OpPatientController {

    private final OpPatientService patientService;

    @GetMapping
    public R<List<OpPatient>> list(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String idCard,
            @RequestParam(required = false) String phone) {
        LambdaQueryWrapper<OpPatient> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(idCard)) {
            wrapper.eq(OpPatient::getIdCard, idCard);
        } else if (StringUtils.hasText(phone)) {
            wrapper.eq(OpPatient::getPhone, phone);
        } else if (StringUtils.hasText(keyword)) {
            wrapper.like(OpPatient::getName, keyword)
                    .or().like(OpPatient::getPhone, keyword)
                    .or().like(OpPatient::getIdCard, keyword);
        }
        wrapper.orderByDesc(OpPatient::getCreateTime);
        return R.ok(patientService.list(wrapper));
    }

    @GetMapping("/{id}")
    public R<OpPatient> getById(@PathVariable Long id) {
        return R.ok(patientService.getById(id));
    }

    @PostMapping
    public R<Void> add(@RequestBody OpPatient patient) {
        patient.setPatientNo(patientService.generatePatientNo());
        patient.setStatus(1);
        patientService.save(patient);
        return R.ok();
    }

    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @RequestBody OpPatient patient) {
        patient.setId(id);
        patientService.updateById(patient);
        return R.ok();
    }
}
