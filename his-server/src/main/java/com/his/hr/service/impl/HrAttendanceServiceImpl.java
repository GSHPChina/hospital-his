package com.his.hr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.his.hr.entity.HrAttendance;
import com.his.hr.mapper.HrAttendanceMapper;
import com.his.hr.service.HrAttendanceService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class HrAttendanceServiceImpl extends ServiceImpl<HrAttendanceMapper, HrAttendance> implements HrAttendanceService {

    @Override
    public List<HrAttendance> listByDate(Long empId, String date) {
        LambdaQueryWrapper<HrAttendance> wrapper = new LambdaQueryWrapper<>();
        if (empId != null) {
            wrapper.eq(HrAttendance::getEmpId, empId);
        }
        if (date != null && !date.isEmpty()) {
            wrapper.eq(HrAttendance::getAttendanceDate, LocalDate.parse(date));
        }
        wrapper.orderByDesc(HrAttendance::getAttendanceDate);
        return list(wrapper);
    }
}
