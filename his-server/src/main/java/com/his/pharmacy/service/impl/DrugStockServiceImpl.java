package com.his.pharmacy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.his.common.exception.BusinessException;
import com.his.pharmacy.entity.DrugStock;
import com.his.pharmacy.entity.DrugStockLog;
import com.his.pharmacy.mapper.DrugStockLogMapper;
import com.his.pharmacy.mapper.DrugStockMapper;
import com.his.pharmacy.service.DrugStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DrugStockServiceImpl extends ServiceImpl<DrugStockMapper, DrugStock> implements DrugStockService {

    private final DrugStockLogMapper stockLogMapper;

    @Override
    @Transactional
    public void inbound(Long drugId, Integer quantity, String batchNo) {
        DrugStock stock = getOne(new LambdaQueryWrapper<DrugStock>()
                .eq(DrugStock::getDrugId, drugId)
                .eq(DrugStock::getBatchNo, batchNo));

        Long operatorId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (stock == null) {
            stock = new DrugStock();
            stock.setDrugId(drugId);
            stock.setBatchNo(batchNo);
            stock.setQuantity(quantity);
            stock.setSafeStock(10);
            stock.setStatus(1);
            save(stock);
        } else {
            int beforeQty = stock.getQuantity();
            stock.setQuantity(stock.getQuantity() + quantity);
            updateById(stock);

            // 记录日志
            DrugStockLog log = new DrugStockLog();
            log.setDrugId(drugId);
            log.setType("INBOUND");
            log.setQuantity(quantity);
            log.setBeforeQty(beforeQty);
            log.setAfterQty(stock.getQuantity());
            log.setOperatorId(operatorId);
            log.setRemark("入库");
            stockLogMapper.insert(log);
        }
    }

    @Override
    @Transactional
    public void outbound(Long drugId, Integer quantity) {
        DrugStock stock = getOne(new LambdaQueryWrapper<DrugStock>()
                .eq(DrugStock::getDrugId, drugId)
                .eq(DrugStock::getStatus, 1));

        if (stock == null || stock.getQuantity() < quantity) {
            throw new BusinessException("库存不足");
        }

        Long operatorId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int beforeQty = stock.getQuantity();
        stock.setQuantity(stock.getQuantity() - quantity);
        updateById(stock);

        DrugStockLog log = new DrugStockLog();
        log.setDrugId(drugId);
        log.setType("OUTBOUND");
        log.setQuantity(-quantity);
        log.setBeforeQty(beforeQty);
        log.setAfterQty(stock.getQuantity());
        log.setOperatorId(operatorId);
        log.setRemark("出库");
        stockLogMapper.insert(log);
    }

    @Override
    public List<DrugStock> getWarnings() {
        return baseMapper.selectWarnings();
    }
}
