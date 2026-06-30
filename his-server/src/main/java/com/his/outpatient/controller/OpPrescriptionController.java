package com.his.outpatient.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.his.common.result.R;
import com.his.outpatient.entity.OpPrescription;
import com.his.outpatient.entity.OpPrescriptionItem;
import com.his.outpatient.mapper.OpPrescriptionItemMapper;
import com.his.outpatient.service.OpPrescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctor/prescriptions")
@RequiredArgsConstructor
public class OpPrescriptionController {

    private final OpPrescriptionService prescriptionService;
    private final OpPrescriptionItemMapper itemMapper;

    @GetMapping
    public R<List<OpPrescription>> list(
            @RequestParam(required = false) Long registerId,
            @RequestParam(required = false) Integer status) {
        if (status != null) {
            return R.ok(prescriptionService.listByStatus(status));
        }
        if (registerId != null) {
            return R.ok(prescriptionService.list(
                    new LambdaQueryWrapper<OpPrescription>()
                            .eq(OpPrescription::getRegisterId, registerId)));
        }
        return R.ok(prescriptionService.list());
    }

    @GetMapping("/{id}")
    public R<OpPrescription> getById(@PathVariable Long id) {
        OpPrescription prescription = prescriptionService.getById(id);
        if (prescription != null) {
            prescription.setItems(itemMapper.selectList(
                    new LambdaQueryWrapper<OpPrescriptionItem>()
                            .eq(OpPrescriptionItem::getPrescriptionId, id)));
        }
        return R.ok(prescription);
    }

    @PostMapping
    public R<Void> create(@RequestBody OpPrescription prescription) {
        prescriptionService.createPrescription(prescription);
        return R.ok();
    }

    @PutMapping("/{id}/submit")
    public R<Void> submit(@PathVariable Long id) {
        prescriptionService.submitPrescription(id);
        return R.ok();
    }
}
