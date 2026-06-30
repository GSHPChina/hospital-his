package com.his.outpatient.controller;

import com.his.common.result.R;
import com.his.outpatient.entity.OpFee;
import com.his.outpatient.service.OpFeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/charge/fees")
@RequiredArgsConstructor
public class OpFeeController {

    private final OpFeeService feeService;

    @GetMapping("/pending")
    public R<List<OpFee>> pending() {
        return R.ok(feeService.listByPayStatus(0));
    }

    @GetMapping
    public R<List<OpFee>> list() {
        return R.ok(feeService.list());
    }

    @PostMapping
    public R<Void> charge(@RequestBody OpFee fee) {
        feeService.charge(fee);
        return R.ok();
    }

    @PostMapping("/{id}/refund")
    public R<Void> refund(@PathVariable Long id) {
        feeService.refund(id);
        return R.ok();
    }
}
