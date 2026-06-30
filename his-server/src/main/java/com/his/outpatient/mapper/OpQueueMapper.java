package com.his.outpatient.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.his.outpatient.entity.OpQueue;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface OpQueueMapper extends BaseMapper<OpQueue> {

    @Select("SELECT q.*, p.name as patient_name, u.real_name as doctor_name " +
            "FROM op_queue q " +
            "LEFT JOIN op_patient p ON q.patient_id = p.id " +
            "LEFT JOIN sys_user u ON q.doctor_id = u.id " +
            "WHERE q.queue_date = #{date} " +
            "ORDER BY q.queue_no")
    List<OpQueue> selectByDate(@Param("date") LocalDate date);

    @Select("SELECT q.*, p.name as patient_name, u.real_name as doctor_name " +
            "FROM op_queue q " +
            "LEFT JOIN op_patient p ON q.patient_id = p.id " +
            "LEFT JOIN sys_user u ON q.doctor_id = u.id " +
            "WHERE q.doctor_id = #{doctorId} AND q.queue_date = #{date} " +
            "ORDER BY q.queue_no")
    List<OpQueue> selectByDoctorAndDate(@Param("doctorId") Long doctorId, @Param("date") LocalDate date);
}
