package com.his.emr.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.his.emr.entity.EmrRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * 病历记录Mapper接口
 */
@Mapper
public interface EmrRecordMapper extends BaseMapper<EmrRecord> {
}
