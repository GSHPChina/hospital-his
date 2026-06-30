package com.his.outpatient.controller;

import com.his.common.result.R;
import com.his.outpatient.entity.OpQueue;
import com.his.outpatient.service.OpQueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/outpatient/queue")
@RequiredArgsConstructor
public class OpQueueController {

    private final OpQueueService queueService;

    @GetMapping
    public R<List<OpQueue>> listToday() {
        return R.ok(queueService.listToday());
    }

    @PostMapping("/add")
    public R<Void> addToQueue(@RequestBody Map<String, Long> params) {
        queueService.addToQueue(params.get("appointmentId"), params.get("registerId"));
        return R.ok();
    }

    @PutMapping("/{id}/call")
    public R<Void> callPatient(@PathVariable Long id) {
        queueService.callPatient(id);
        return R.ok();
    }

    @PutMapping("/{id}/complete")
    public R<Void> completePatient(@PathVariable Long id) {
        queueService.completePatient(id);
        return R.ok();
    }

    @PutMapping("/{id}/skip")
    public R<Void> skipPatient(@PathVariable Long id) {
        queueService.skipPatient(id);
        return R.ok();
    }
}
