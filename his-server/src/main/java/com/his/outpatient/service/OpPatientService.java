package com.his.outpatient.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.his.outpatient.entity.OpPatient;

public interface OpPatientService extends IService<OpPatient> {
    OpPatient getByIdCard(String idCard);
    OpPatient getByPhone(String phone);
    String generatePatientNo();
}
