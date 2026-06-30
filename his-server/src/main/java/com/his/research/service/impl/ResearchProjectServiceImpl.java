package com.his.research.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.his.research.entity.ResearchProject;
import com.his.research.mapper.ResearchProjectMapper;
import com.his.research.service.ResearchProjectService;
import org.springframework.stereotype.Service;

@Service
public class ResearchProjectServiceImpl extends ServiceImpl<ResearchProjectMapper, ResearchProject> implements ResearchProjectService {
}
