package com.his.outpatient.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.his.outpatient.entity.OpFee;

import java.util.List;

public interface OpFeeService extends IService<OpFee> {
    void charge(OpFee fee);
    void refund(Long id);
    List<OpFee> listByPayStatus(Integer payStatus);
}
