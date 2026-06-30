package com.his.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.his.system.entity.SysDept;
import com.his.system.mapper.SysDeptMapper;
import com.his.system.service.SysDeptService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

    @Override
    public List<SysDept> listTree() {
        List<SysDept> allDepts = list();
        return buildTree(allDepts, 0L);
    }

    private List<SysDept> buildTree(List<SysDept> depts, Long parentId) {
        return depts.stream()
                .filter(d -> parentId.equals(d.getParentId()))
                .peek(d -> d.setChildren(buildTree(depts, d.getId())))
                .collect(Collectors.toList());
    }
}
