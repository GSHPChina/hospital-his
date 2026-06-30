package com.his.adverse.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.his.adverse.entity.AdverseEvent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AdverseEventMapper extends BaseMapper<AdverseEvent> {

    @Select("SELECT ae.*, " +
            "ru.real_name AS reporter_name, " +
            "hu.real_name AS handler_name, " +
            "op.name AS patient_name " +
            "FROM adverse_event ae " +
            "LEFT JOIN sys_user ru ON ae.reporter_id = ru.id " +
            "LEFT JOIN sys_user hu ON ae.handler_id = hu.id " +
            "LEFT JOIN op_patient op ON ae.patient_id = op.id " +
            "WHERE ae.type = COALESCE(#{type}, ae.type) " +
            "AND ae.level = COALESCE(#{level}, ae.level) " +
            "AND ae.status = COALESCE(#{status}, ae.status) " +
            "ORDER BY ae.create_time DESC")
    List<AdverseEvent> selectAllWithNames(@Param("type") String type,
                                          @Param("level") Integer level,
                                          @Param("status") Integer status);

    @Select("SELECT ae.*, " +
            "ru.real_name AS reporter_name, " +
            "hu.real_name AS handler_name, " +
            "op.name AS patient_name " +
            "FROM adverse_event ae " +
            "LEFT JOIN sys_user ru ON ae.reporter_id = ru.id " +
            "LEFT JOIN sys_user hu ON ae.handler_id = hu.id " +
            "LEFT JOIN op_patient op ON ae.patient_id = op.id " +
            "WHERE ae.id = #{id}")
    AdverseEvent selectOneWithNames(@Param("id") Long id);
}
