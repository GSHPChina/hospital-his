package com.his.emr.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.his.emr.entity.EmrQualityCheck;
import org.apache.ibatis.annotations.Mapper;

/**
 * 病历质量检查Mapper接口
 */
@Mapper
public interface EmrQualityCheckMapper extends BaseMapper<EmrQualityCheck> {
}
