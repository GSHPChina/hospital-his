package com.his.emr.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.his.emr.entity.EmrTemplate;
import com.his.emr.mapper.EmrTemplateMapper;
import com.his.emr.service.EmrTemplateService;
import org.springframework.stereotype.Service;

/**
 * 病历模板服务实现类
 */
@Service
public class EmrTemplateServiceImpl extends ServiceImpl<EmrTemplateMapper, EmrTemplate> implements EmrTemplateService {
}
