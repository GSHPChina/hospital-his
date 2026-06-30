package com.his.title.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.his.title.entity.TitleLevel;
import com.his.title.mapper.TitleLevelMapper;
import com.his.title.service.TitleLevelService;
import org.springframework.stereotype.Service;

@Service
public class TitleLevelServiceImpl extends ServiceImpl<TitleLevelMapper, TitleLevel> implements TitleLevelService {
}
