package com.his.outpatient.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.his.outpatient.entity.OpPatient;
import com.his.outpatient.mapper.OpPatientMapper;
import com.his.outpatient.service.OpPatientService;
import org.springframework.stereotype.Service;

@Service
public class OpPatientServiceImpl extends ServiceImpl<OpPatientMapper, OpPatient> implements OpPatientService {

    @Override
    public OpPatient getByIdCard(String idCard) {
        return getOne(new LambdaQueryWrapper<OpPatient>().eq(OpPatient::getIdCard, idCard));
    }

    @Override
    public OpPatient getByPhone(String phone) {
        return getOne(new LambdaQueryWrapper<OpPatient>().eq(OpPatient::getPhone, phone));
    }

    @Override
    public String generatePatientNo() {
        return "P" + IdUtil.getSnowflakeNextIdStr();
    }
}
