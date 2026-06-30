package com.his.pharmacy.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.his.common.result.R;
import com.his.pharmacy.entity.DrugInfo;
import com.his.pharmacy.service.DrugInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pharmacy/drugs")
@RequiredArgsConstructor
public class DrugInfoController {

    private final DrugInfoService drugInfoService;

    @GetMapping
    public R<List<DrugInfo>> list(@RequestParam(required = false) String keyword) {
        LambdaQueryWrapper<DrugInfo> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(keyword)) {
            wrapper.like(DrugInfo::getDrugName, keyword)
                    .or().like(DrugInfo::getDrugCode, keyword);
        }
        wrapper.orderByDesc(DrugInfo::getCreateTime);
        return R.ok(drugInfoService.list(wrapper));
    }

    @GetMapping("/{id}")
    public R<DrugInfo> getById(@PathVariable Long id) {
        return R.ok(drugInfoService.getById(id));
    }

    @PostMapping
    public R<Void> add(@RequestBody DrugInfo drug) {
        drug.setStatus(1);
        drugInfoService.save(drug);
        return R.ok();
    }

    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @RequestBody DrugInfo drug) {
        drug.setId(id);
        drugInfoService.updateById(drug);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        drugInfoService.removeById(id);
        return R.ok();
    }
}
