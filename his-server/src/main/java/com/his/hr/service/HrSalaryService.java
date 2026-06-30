package com.his.hr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.his.hr.entity.HrSalary;

import java.util.List;

public interface HrSalaryService extends IService<HrSalary> {
    List<HrSalary> listByMonth(String ym);
}
