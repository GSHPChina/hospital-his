package com.his.outpatient.controller;

import com.his.common.result.R;
import com.his.outpatient.entity.OpRegister;
import com.his.outpatient.service.OpRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/doctor/workstation")
@RequiredArgsConstructor
public class DoctorWorkstationController {

    private final OpRegisterService registerService;

    @GetMapping("/today-patients")
    public R<List<OpRegister>> getTodayPatients() {
        Long doctorId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return R.ok(registerService.getTodayPatients(doctorId));
    }
}
