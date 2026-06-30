package com.his.pharmacy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.his.pharmacy.entity.DrugDispensing;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DrugDispensingMapper extends BaseMapper<DrugDispensing> {

    @Select("SELECT d.*, p.name as patient_name, u.real_name as pharmacist_name " +
            "FROM drug_dispensing d " +
            "LEFT JOIN op_patient p ON d.patient_id = p.id " +
            "LEFT JOIN sys_user u ON d.pharmacist_id = u.id " +
            "WHERE d.status = #{status} " +
            "ORDER BY d.create_time DESC")
    List<DrugDispensing> selectByStatus(@Param("status") Integer status);
}
