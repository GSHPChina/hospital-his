package com.his.outpatient.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.his.outpatient.entity.OpRegister;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OpRegisterMapper extends BaseMapper<OpRegister> {

    @Select("SELECT r.*, p.name as patient_name, d.name as dept_name, u.real_name as doctor_name " +
            "FROM op_register r " +
            "LEFT JOIN op_patient p ON r.patient_id = p.id " +
            "LEFT JOIN sys_dept d ON r.dept_id = d.id " +
            "LEFT JOIN sys_user u ON r.doctor_id = u.id " +
            "ORDER BY r.create_time DESC")
    List<OpRegister> selectAllWithDetails();

    @Select("SELECT r.*, p.name as patient_name, d.name as dept_name, u.real_name as doctor_name " +
            "FROM op_register r " +
            "LEFT JOIN op_patient p ON r.patient_id = p.id " +
            "LEFT JOIN sys_dept d ON r.dept_id = d.id " +
            "LEFT JOIN sys_user u ON r.doctor_id = u.id " +
            "WHERE r.doctor_id = #{doctorId} AND r.status = 1 " +
            "ORDER BY r.register_time")
    List<OpRegister> selectTodayPatients(@Param("doctorId") Long doctorId);
}
