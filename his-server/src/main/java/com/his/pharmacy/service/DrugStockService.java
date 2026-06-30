package com.his.pharmacy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.his.pharmacy.entity.DrugStock;

import java.util.List;

public interface DrugStockService extends IService<DrugStock> {
    void inbound(Long drugId, Integer quantity, String batchNo);
    void outbound(Long drugId, Integer quantity);
    List<DrugStock> getWarnings();
}
