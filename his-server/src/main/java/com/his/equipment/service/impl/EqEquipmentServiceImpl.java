package com.his.equipment.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.his.equipment.entity.EqCategory;
import com.his.equipment.entity.EqEquipment;
import com.his.equipment.mapper.EqCategoryMapper;
import com.his.equipment.mapper.EqEquipmentMapper;
import com.his.equipment.service.EqEquipmentService;
import com.his.system.entity.SysDept;
import com.his.system.mapper.SysDeptMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EqEquipmentServiceImpl extends ServiceImpl<EqEquipmentMapper, EqEquipment> implements EqEquipmentService {

    private final EqCategoryMapper categoryMapper;
    private final SysDeptMapper sysDeptMapper;

    @Override
    public List<EqEquipment> listWithCategory() {
        List<EqEquipment> list = baseMapper.selectList(null);
        if (list.isEmpty()) {
            return list;
        }
        Map<Long, String> categoryMap = categoryMapper.selectList(null)
                .stream()
                .collect(Collectors.toMap(EqCategory::getId, EqCategory::getName));
        Map<Long, String> deptMap = sysDeptMapper.selectList(null)
                .stream()
                .collect(Collectors.toMap(SysDept::getId, SysDept::getName));
        for (EqEquipment eq : list) {
            if (eq.getCategoryId() != null) {
                eq.setCategoryName(categoryMap.get(eq.getCategoryId()));
            }
            if (eq.getDeptId() != null) {
                eq.setDeptName(deptMap.get(eq.getDeptId()));
            }
        }
        return list;
    }
}
