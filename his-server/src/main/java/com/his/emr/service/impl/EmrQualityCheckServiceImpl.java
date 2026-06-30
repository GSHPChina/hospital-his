package com.his.emr.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.his.emr.entity.EmrQualityCheck;
import com.his.emr.mapper.EmrQualityCheckMapper;
import com.his.emr.service.EmrQualityCheckService;
import org.springframework.stereotype.Service;

/**
 * 病历质量检查服务实现类
 */
@Service
public class EmrQualityCheckServiceImpl extends ServiceImpl<EmrQualityCheckMapper, EmrQualityCheck> implements EmrQualityCheckService {
}
