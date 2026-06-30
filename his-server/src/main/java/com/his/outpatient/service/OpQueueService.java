package com.his.outpatient.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.his.outpatient.entity.OpQueue;

import java.util.List;

public interface OpQueueService extends IService<OpQueue> {
    List<OpQueue> listToday();
    void addToQueue(Long appointmentId, Long registerId);
    void callNext(Long doctorId);
    void callPatient(Long id);
    void completePatient(Long id);
    void skipPatient(Long id);
}
