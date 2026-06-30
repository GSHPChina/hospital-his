package com.his.outpatient.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.his.outpatient.entity.OpSchedule;
import com.his.outpatient.mapper.OpScheduleMapper;
import com.his.outpatient.service.OpScheduleService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OpScheduleServiceImpl extends ServiceImpl<OpScheduleMapper, OpSchedule> implements OpScheduleService {

    @Override
    public List<OpSchedule> listByDeptAndDate(Long deptId, LocalDate date) {
        return list(new LambdaQueryWrapper<OpSchedule>()
                .eq(deptId != null, OpSchedule::getDeptId, deptId)
                .eq(date != null, OpSchedule::getScheduleDate, date)
                .eq(OpSchedule::getStatus, 1)
                .orderByAsc(OpSchedule::getScheduleDate));
    }
}
