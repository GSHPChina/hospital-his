package com.his.hr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.his.hr.entity.HrSalary;
import com.his.hr.mapper.HrSalaryMapper;
import com.his.hr.service.HrSalaryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HrSalaryServiceImpl extends ServiceImpl<HrSalaryMapper, HrSalary> implements HrSalaryService {

    @Override
    public List<HrSalary> listByMonth(String ym) {
        LambdaQueryWrapper<HrSalary> wrapper = new LambdaQueryWrapper<>();
        if (ym != null && !ym.isEmpty()) {
            wrapper.eq(HrSalary::getYm, ym);
        }
        wrapper.orderByDesc(HrSalary::getYm);
        return list(wrapper);
    }
}
