package com.his.outpatient.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.his.outpatient.entity.OpAppointment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface OpAppointmentMapper extends BaseMapper<OpAppointment> {

    @Select("SELECT a.*, p.name as patient_name, d.name as dept_name, u.real_name as doctor_name " +
            "FROM op_appointment a " +
            "LEFT JOIN op_patient p ON a.patient_id = p.id " +
            "LEFT JOIN sys_dept d ON a.dept_id = d.id " +
            "LEFT JOIN sys_user u ON a.doctor_id = u.id " +
            "ORDER BY a.create_time DESC")
    List<OpAppointment> selectAllWithDetails();

    @Select("SELECT a.*, p.name as patient_name, d.name as dept_name, u.real_name as doctor_name " +
            "FROM op_appointment a " +
            "LEFT JOIN op_patient p ON a.patient_id = p.id " +
            "LEFT JOIN sys_dept d ON a.dept_id = d.id " +
            "LEFT JOIN sys_user u ON a.doctor_id = u.id " +
            "WHERE a.doctor_id = #{doctorId} AND a.appointment_date = #{date} " +
            "ORDER BY a.queue_no")
    List<OpAppointment> selectByDoctorAndDate(@Param("doctorId") Long doctorId, @Param("date") LocalDate date);
}
