package com.his.hr.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.his.hr.entity.HrEmployee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface HrEmployeeMapper extends BaseMapper<HrEmployee> {

    @Select("SELECT e.*, d.name AS dept_name FROM hr_employee e LEFT JOIN sys_dept d ON e.dept_id = d.id ORDER BY e.create_time DESC")
    List<HrEmployee> selectAllWithDept();
}
