package com.his.outpatient.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.his.outpatient.entity.OpFee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OpFeeMapper extends BaseMapper<OpFee> {

    @Select("SELECT f.*, p.name as patient_name, u.real_name as operator_name " +
            "FROM op_fee f " +
            "LEFT JOIN op_patient p ON f.patient_id = p.id " +
            "LEFT JOIN sys_user u ON f.operator_id = u.id " +
            "WHERE f.pay_status = #{payStatus} " +
            "ORDER BY f.create_time DESC")
    List<OpFee> selectByPayStatus(@Param("payStatus") Integer payStatus);
}
