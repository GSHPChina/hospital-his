package com.his.outpatient.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.his.outpatient.entity.OpDiagnosis;
import com.his.outpatient.mapper.OpDiagnosisMapper;
import com.his.outpatient.service.OpDiagnosisService;
import org.springframework.stereotype.Service;

@Service
public class OpDiagnosisServiceImpl extends ServiceImpl<OpDiagnosisMapper, OpDiagnosis> implements OpDiagnosisService {
}
