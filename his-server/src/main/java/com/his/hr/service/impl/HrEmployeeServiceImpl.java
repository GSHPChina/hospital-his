package com.his.hr.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.his.hr.entity.HrEmployee;
import com.his.hr.mapper.HrEmployeeMapper;
import com.his.hr.service.HrEmployeeService;
import org.springframework.stereotype.Service;

@Service
public class HrEmployeeServiceImpl extends ServiceImpl<HrEmployeeMapper, HrEmployee> implements HrEmployeeService {
}
