package com.his.outpatient.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.his.common.exception.BusinessException;
import com.his.outpatient.entity.OpPrescription;
import com.his.outpatient.entity.OpPrescriptionItem;
import com.his.outpatient.mapper.OpPrescriptionItemMapper;
import com.his.outpatient.mapper.OpPrescriptionMapper;
import com.his.outpatient.service.OpPrescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OpPrescriptionServiceImpl extends ServiceImpl<OpPrescriptionMapper, OpPrescription> implements OpPrescriptionService {

    private final OpPrescriptionItemMapper itemMapper;

    @Override
    @Transactional
    public void createPrescription(OpPrescription prescription) {
        prescription.setPrescriptionNo("RX" + IdUtil.getSnowflakeNextIdStr());
        prescription.setStatus(1);

        // 计算总金额
        BigDecimal total = BigDecimal.ZERO;
        if (prescription.getItems() != null) {
            for (OpPrescriptionItem item : prescription.getItems()) {
                item.setAmount(item.getUnitPrice().multiply(new BigDecimal(item.getQuantity())));
                total = total.add(item.getAmount());
            }
        }
        prescription.setTotalAmount(total);

        save(prescription);

        // 保存明细
        if (prescription.getItems() != null) {
            for (OpPrescriptionItem item : prescription.getItems()) {
                item.setPrescriptionId(prescription.getId());
                itemMapper.insert(item);
            }
        }
    }

    @Override
    public void submitPrescription(Long id) {
        OpPrescription prescription = getById(id);
        if (prescription == null) {
            throw new BusinessException("处方不存在");
        }
        if (prescription.getStatus() != 1) {
            throw new BusinessException("当前状态不允许提交");
        }
        // 状态不变，提交后等待收费
    }

    @Override
    public List<OpPrescription> listByStatus(Integer status) {
        return baseMapper.selectByStatus(status);
    }

    @Override
    public void updateStatus(Long id, Integer status) {
        OpPrescription prescription = new OpPrescription();
        prescription.setId(id);
        prescription.setStatus(status);
        updateById(prescription);
    }
}
