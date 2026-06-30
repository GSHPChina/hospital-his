package com.his.pharmacy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.his.pharmacy.entity.DrugDispensing;

import java.util.List;

public interface DrugDispensingService extends IService<DrugDispensing> {
    List<DrugDispensing> listByStatus(Integer status);
    void dispense(Long id);
}
