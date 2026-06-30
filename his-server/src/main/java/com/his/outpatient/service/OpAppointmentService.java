package com.his.outpatient.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.his.outpatient.entity.OpAppointment;

import java.time.LocalDate;
import java.util.List;

public interface OpAppointmentService extends IService<OpAppointment> {
    List<OpAppointment> listWithDetails();
    void book(OpAppointment appointment);
    void checkIn(Long id);
    void cancel(Long id, String reason);
    List<OpAppointment> listByDoctorAndDate(Long doctorId, LocalDate date);
}
