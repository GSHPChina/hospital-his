package com.his.outpatient.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.his.outpatient.entity.OpMedicalRecord;
import com.his.outpatient.mapper.OpMedicalRecordMapper;
import com.his.outpatient.service.OpMedicalRecordService;
import org.springframework.stereotype.Service;

@Service
public class OpMedicalRecordServiceImpl extends ServiceImpl<OpMedicalRecordMapper, OpMedicalRecord> implements OpMedicalRecordService {
}
