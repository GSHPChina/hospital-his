package com.his.equipment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.his.equipment.entity.EqEquipment;

import java.util.List;

public interface EqEquipmentService extends IService<EqEquipment> {
    List<EqEquipment> listWithCategory();
}
