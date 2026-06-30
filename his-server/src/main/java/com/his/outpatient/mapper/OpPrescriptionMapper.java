package com.his.outpatient.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.his.outpatient.entity.OpPrescription;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OpPrescriptionMapper extends BaseMapper<OpPrescription> {

    @Select("SELECT p.*, pt.name as patient_name, u.real_name as doctor_name " +
            "FROM op_prescription p " +
            "LEFT JOIN op_patient pt ON p.patient_id = pt.id " +
            "LEFT JOIN sys_user u ON p.doctor_id = u.id " +
            "WHERE p.status = #{status} " +
            "ORDER BY p.create_time DESC")
    List<OpPrescription> selectByStatus(@Param("status") Integer status);
}
