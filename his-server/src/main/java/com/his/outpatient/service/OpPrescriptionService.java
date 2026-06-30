package com.his.outpatient.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.his.outpatient.entity.OpPrescription;

import java.util.List;

public interface OpPrescriptionService extends IService<OpPrescription> {
    void createPrescription(OpPrescription prescription);
    void submitPrescription(Long id);
    List<OpPrescription> listByStatus(Integer status);
    void updateStatus(Long id, Integer status);
}
