package com.his.performance.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.his.performance.entity.PerfTemplate;
import com.his.performance.mapper.PerfTemplateMapper;
import com.his.performance.service.PerfTemplateService;
import org.springframework.stereotype.Service;

@Service
public class PerfTemplateServiceImpl extends ServiceImpl<PerfTemplateMapper, PerfTemplate> implements PerfTemplateService {
}
