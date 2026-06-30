package com.his.outpatient.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.his.outpatient.entity.OpPatient;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OpPatientMapper extends BaseMapper<OpPatient> {
}
