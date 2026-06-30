package com.his.outpatient.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.his.outpatient.entity.OpRegister;

import java.util.List;

public interface OpRegisterService extends IService<OpRegister> {
    List<OpRegister> listWithDetails();
    List<OpRegister> getTodayPatients(Long doctorId);
    void register(OpRegister register);
    void cancelRegister(Long id);
}
