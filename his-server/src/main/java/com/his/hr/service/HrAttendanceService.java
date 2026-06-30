package com.his.hr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.his.hr.entity.HrAttendance;

import java.util.List;

public interface HrAttendanceService extends IService<HrAttendance> {
    List<HrAttendance> listByDate(Long empId, String date);
}
