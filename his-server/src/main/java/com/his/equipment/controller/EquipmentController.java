package com.his.equipment.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.his.common.result.R;
import com.his.equipment.entity.EqCategory;
import com.his.equipment.entity.EqEquipment;
import com.his.equipment.entity.EqMaintenance;
import com.his.equipment.mapper.EqCategoryMapper;
import com.his.equipment.mapper.EqMaintenanceMapper;
import com.his.equipment.service.EqEquipmentService;
import com.his.system.entity.SysDept;
import com.his.system.mapper.SysDeptMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/equipment")
@RequiredArgsConstructor
public class EquipmentController {

    private final EqCategoryMapper categoryMapper;
    private final EqEquipmentService equipmentService;
    private final EqMaintenanceMapper maintenanceMapper;
    private final SysDeptMapper sysDeptMapper;

    // ==================== Category ====================

    @GetMapping("/categories/tree")
    public R<List<EqCategory>> categoryTree() {
        List<EqCategory> all = categoryMapper.selectList(null);
        Map<Long, List<EqCategory>> groupByParent = all.stream()
                .filter(c -> c.getParentId() != null)
                .collect(Collectors.groupingBy(EqCategory::getParentId));
        for (EqCategory category : all) {
            category.setChildren(groupByParent.getOrDefault(category.getId(), new ArrayList<>()));
        }
        List<EqCategory> roots = all.stream()
                .filter(c -> c.getParentId() == null || c.getParentId() == 0)
                .collect(Collectors.toList());
        return R.ok(roots);
    }

    @PostMapping("/categories")
    public R<Void> addCategory(@RequestBody EqCategory category) {
        categoryMapper.insert(category);
        return R.ok();
    }

    // ==================== Equipment ====================

    @GetMapping("/equipments")
    public R<List<EqEquipment>> listEquipments(@RequestParam(required = false) Long categoryId,
                                                @RequestParam(required = false) Integer status) {
        LambdaQueryWrapper<EqEquipment> wrapper = new LambdaQueryWrapper<>();
        if (categoryId != null) {
            wrapper.eq(EqEquipment::getCategoryId, categoryId);
        }
        if (status != null) {
            wrapper.eq(EqEquipment::getStatus, status);
        }
        wrapper.orderByDesc(EqEquipment::getCreateTime);
        List<EqEquipment> list = equipmentService.list(wrapper);
        if (!list.isEmpty()) {
            Map<Long, String> categoryMap = categoryMapper.selectList(null).stream()
                    .collect(Collectors.toMap(EqCategory::getId, EqCategory::getName));
            Map<Long, String> deptMap = sysDeptMapper.selectList(null).stream()
                    .collect(Collectors.toMap(SysDept::getId, SysDept::getName));
            for (EqEquipment eq : list) {
                if (eq.getCategoryId() != null) {
                    eq.setCategoryName(categoryMap.get(eq.getCategoryId()));
                }
                if (eq.getDeptId() != null) {
                    eq.setDeptName(deptMap.get(eq.getDeptId()));
                }
            }
        }
        return R.ok(list);
    }

    @PostMapping("/equipments")
    public R<Void> addEquipment(@RequestBody EqEquipment equipment) {
        equipment.setStatus(1);
        equipmentService.save(equipment);
        return R.ok();
    }

    @PutMapping("/equipments/{id}")
    public R<Void> updateEquipment(@PathVariable Long id, @RequestBody EqEquipment equipment) {
        equipment.setId(id);
        equipmentService.updateById(equipment);
        return R.ok();
    }

    // ==================== Maintenance ====================

    @GetMapping("/maintenances")
    public R<List<EqMaintenance>> listMaintenances(@RequestParam(required = false) Long equipId,
                                                    @RequestParam(required = false) String type) {
        LambdaQueryWrapper<EqMaintenance> wrapper = new LambdaQueryWrapper<>();
        if (equipId != null) {
            wrapper.eq(EqMaintenance::getEquipId, equipId);
        }
        if (StringUtils.hasText(type)) {
            wrapper.eq(EqMaintenance::getType, type);
        }
        wrapper.orderByDesc(EqMaintenance::getCreateTime);
        List<EqMaintenance> list = maintenanceMapper.selectList(wrapper);
        if (!list.isEmpty()) {
            List<Long> equipIds = list.stream()
                    .map(EqMaintenance::getEquipId)
                    .distinct()
                    .collect(Collectors.toList());
            Map<Long, String> equipNameMap = equipmentService.listByIds(equipIds).stream()
                    .collect(Collectors.toMap(EqEquipment::getId, EqEquipment::getName));
            for (EqMaintenance m : list) {
                m.setEquipName(equipNameMap.get(m.getEquipId()));
            }
        }
        return R.ok(list);
    }

    @PostMapping("/maintenances")
    public R<Void> addMaintenance(@RequestBody EqMaintenance maintenance) {
        maintenance.setStatus(0);
        maintenanceMapper.insert(maintenance);
        return R.ok();
    }

    @PutMapping("/maintenances/{id}/complete")
    public R<Void> completeMaintenance(@PathVariable Long id, @RequestBody(required = false) EqMaintenance body) {
        EqMaintenance maintenance = maintenanceMapper.selectById(id);
        if (maintenance == null) {
            return R.fail("维护记录不存在");
        }
        maintenance.setStatus(1);
        maintenance.setEndTime(LocalDateTime.now());
        if (body != null && StringUtils.hasText(body.getResult())) {
            maintenance.setResult(body.getResult());
        }
        maintenanceMapper.updateById(maintenance);
        return R.ok();
    }
}
