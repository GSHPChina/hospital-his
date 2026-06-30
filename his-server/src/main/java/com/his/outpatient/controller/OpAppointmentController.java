package com.his.outpatient.controller;

import com.his.common.result.R;
import com.his.outpatient.entity.OpAppointment;
import com.his.outpatient.service.OpAppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/outpatient/appointments")
@RequiredArgsConstructor
public class OpAppointmentController {

    private final OpAppointmentService appointmentService;

    @PostMapping
    public R<Void> book(@RequestBody OpAppointment appointment) {
        appointmentService.book(appointment);
        return R.ok();
    }

    @GetMapping
    public R<List<OpAppointment>> list(
            @RequestParam(required = false) Long doctorId,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        if (doctorId != null && date != null) {
            return R.ok(appointmentService.listByDoctorAndDate(doctorId, date));
        }
        return R.ok(appointmentService.listWithDetails());
    }

    @PutMapping("/{id}/checkin")
    public R<Void> checkIn(@PathVariable Long id) {
        appointmentService.checkIn(id);
        return R.ok();
    }

    @PutMapping("/{id}/cancel")
    public R<Void> cancel(@PathVariable Long id, @RequestParam(required = false) String reason) {
        appointmentService.cancel(id, reason);
        return R.ok();
    }
}
