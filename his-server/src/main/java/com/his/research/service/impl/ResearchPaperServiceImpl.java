package com.his.research.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.his.research.entity.ResearchPaper;
import com.his.research.mapper.ResearchPaperMapper;
import com.his.research.service.ResearchPaperService;
import org.springframework.stereotype.Service;

@Service
public class ResearchPaperServiceImpl extends ServiceImpl<ResearchPaperMapper, ResearchPaper> implements ResearchPaperService {
}
