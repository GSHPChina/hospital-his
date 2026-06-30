package com.his.pharmacy.controller;

import com.his.common.result.R;
import com.his.pharmacy.entity.DrugDispensing;
import com.his.pharmacy.service.DrugDispensingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pharmacy/dispensings")
@RequiredArgsConstructor
public class DrugDispensingController {

    private final DrugDispensingService dispensingService;

    @GetMapping("/pending")
    public R<List<DrugDispensing>> pending() {
        return R.ok(dispensingService.listByStatus(1));
    }

    @GetMapping
    public R<List<DrugDispensing>> list() {
        return R.ok(dispensingService.list());
    }

    @PostMapping("/{id}/dispense")
    public R<Void> dispense(@PathVariable Long id) {
        dispensingService.dispense(id);
        return R.ok();
    }
}
