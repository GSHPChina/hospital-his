package com.his.outpatient.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.his.outpatient.entity.OpSchedule;

import java.time.LocalDate;
import java.util.List;

public interface OpScheduleService extends IService<OpSchedule> {
    List<OpSchedule> listByDeptAndDate(Long deptId, LocalDate date);
}
