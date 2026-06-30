package com.his.outpatient.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.his.common.exception.BusinessException;
import com.his.outpatient.entity.OpFee;
import com.his.outpatient.entity.OpPrescription;
import com.his.outpatient.mapper.OpFeeMapper;
import com.his.outpatient.mapper.OpPrescriptionMapper;
import com.his.outpatient.service.OpFeeService;
import com.his.outpatient.service.OpPrescriptionService;
import com.his.pharmacy.entity.DrugDispensing;
import com.his.pharmacy.mapper.DrugDispensingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OpFeeServiceImpl extends ServiceImpl<OpFeeMapper, OpFee> implements OpFeeService {

    private final OpPrescriptionService prescriptionService;
    private final OpPrescriptionMapper prescriptionMapper;
    private final DrugDispensingMapper dispensingMapper;

    @Override
    @Transactional
    public void charge(OpFee fee) {
        fee.setFeeNo("FEE" + IdUtil.getSnowflakeNextIdStr());
        fee.setPayStatus(1);
        fee.setPayTime(LocalDateTime.now());

        // 设置收费员
        Long operatorId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        fee.setOperatorId(operatorId);

        // 更新处方状态为已收费
        if (fee.getPrescriptionId() != null) {
            prescriptionService.updateStatus(fee.getPrescriptionId(), 2);

            // 自动创建发药记录
            DrugDispensing dispensing = new DrugDispensing();
            dispensing.setDispensingNo("DSP" + IdUtil.getSnowflakeNextIdStr());
            dispensing.setPrescriptionId(fee.getPrescriptionId());
            dispensing.setPatientId(fee.getPatientId());
            dispensing.setStatus(1); // 待发药
            dispensingMapper.insert(dispensing);
        }

        save(fee);
    }

    @Override
    @Transactional
    public void refund(Long id) {
        OpFee fee = getById(id);
        if (fee == null) {
            throw new BusinessException("收费记录不存在");
        }
        if (fee.getPayStatus() != 1) {
            throw new BusinessException("当前状态不允许退费");
        }

        fee.setPayStatus(2);
        fee.setRefundTime(LocalDateTime.now());
        updateById(fee);

        // 更新处方状态为已退费
        if (fee.getPrescriptionId() != null) {
            prescriptionService.updateStatus(fee.getPrescriptionId(), 4);
        }
    }

    @Override
    public List<OpFee> listByPayStatus(Integer payStatus) {
        return baseMapper.selectByPayStatus(payStatus);
    }
}
