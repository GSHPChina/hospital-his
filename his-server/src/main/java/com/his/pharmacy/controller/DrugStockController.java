package com.his.pharmacy.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.his.common.result.R;
import com.his.pharmacy.entity.DrugInfo;
import com.his.pharmacy.entity.DrugStock;
import com.his.pharmacy.service.DrugInfoService;
import com.his.pharmacy.service.DrugStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pharmacy/stocks")
@RequiredArgsConstructor
public class DrugStockController {

    private final DrugStockService stockService;
    private final DrugInfoService drugInfoService;

    @GetMapping
    public R<List<DrugStock>> list() {
        List<DrugStock> stocks = stockService.list();
        stocks.forEach(s -> {
            DrugInfo drug = drugInfoService.getById(s.getDrugId());
            if (drug != null) {
                s.setDrugName(drug.getDrugName());
                s.setDrugSpec(drug.getSpec());
            }
        });
        return R.ok(stocks);
    }

    @GetMapping("/warnings")
    public R<List<DrugStock>> warnings() {
        return R.ok(stockService.getWarnings());
    }

    @PostMapping("/inbound")
    public R<Void> inbound(@RequestParam Long drugId,
                           @RequestParam Integer quantity,
                           @RequestParam String batchNo) {
        stockService.inbound(drugId, quantity, batchNo);
        return R.ok();
    }
}
