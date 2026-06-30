package com.his.pharmacy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.his.common.exception.BusinessException;
import com.his.outpatient.entity.OpPrescriptionItem;
import com.his.outpatient.mapper.OpPrescriptionItemMapper;
import com.his.outpatient.service.OpPrescriptionService;
import com.his.pharmacy.entity.DrugDispensing;
import com.his.pharmacy.mapper.DrugDispensingMapper;
import com.his.pharmacy.service.DrugDispensingService;
import com.his.pharmacy.service.DrugStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DrugDispensingServiceImpl extends ServiceImpl<DrugDispensingMapper, DrugDispensing> implements DrugDispensingService {

    private final OpPrescriptionItemMapper prescriptionItemMapper;
    private final OpPrescriptionService prescriptionService;
    private final DrugStockService drugStockService;

    @Override
    public List<DrugDispensing> listByStatus(Integer status) {
        return baseMapper.selectByStatus(status);
    }

    @Override
    @Transactional
    public void dispense(Long id) {
        DrugDispensing dispensing = getById(id);
        if (dispensing == null) {
            throw new BusinessException("发药记录不存在");
        }
        if (dispensing.getStatus() != 1) {
            throw new BusinessException("当前状态不允许发药");
        }

        // 扣减库存
        List<OpPrescriptionItem> items = prescriptionItemMapper.selectList(
                new LambdaQueryWrapper<OpPrescriptionItem>()
                        .eq(OpPrescriptionItem::getPrescriptionId, dispensing.getPrescriptionId()));

        for (OpPrescriptionItem item : items) {
            if (item.getDrugId() != null) {
                drugStockService.outbound(item.getDrugId(), item.getQuantity());
            }
        }

        // 更新发药状态
        Long pharmacistId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        dispensing.setStatus(2);
        dispensing.setPharmacistId(pharmacistId);
        dispensing.setDispensingTime(LocalDateTime.now());
        updateById(dispensing);

        // 更新处方状态为已发药
        prescriptionService.updateStatus(dispensing.getPrescriptionId(), 3);
    }
}
