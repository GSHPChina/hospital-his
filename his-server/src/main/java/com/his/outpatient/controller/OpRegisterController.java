package com.his.outpatient.controller;

import com.his.common.result.R;
import com.his.outpatient.entity.OpRegister;
import com.his.outpatient.service.OpRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/outpatient/registers")
@RequiredArgsConstructor
public class OpRegisterController {

    private final OpRegisterService registerService;

    @GetMapping
    public R<List<OpRegister>> list() {
        return R.ok(registerService.listWithDetails());
    }

    @GetMapping("/{id}")
    public R<OpRegister> getById(@PathVariable Long id) {
        return R.ok(registerService.getById(id));
    }

    @PostMapping
    public R<Void> register(@RequestBody OpRegister register) {
        registerService.register(register);
        return R.ok();
    }

    @PutMapping("/{id}/cancel")
    public R<Void> cancel(@PathVariable Long id) {
        registerService.cancelRegister(id);
        return R.ok();
    }
}
