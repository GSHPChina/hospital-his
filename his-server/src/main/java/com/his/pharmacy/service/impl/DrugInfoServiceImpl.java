package com.his.pharmacy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.his.pharmacy.entity.DrugInfo;
import com.his.pharmacy.mapper.DrugInfoMapper;
import com.his.pharmacy.service.DrugInfoService;
import org.springframework.stereotype.Service;

@Service
public class DrugInfoServiceImpl extends ServiceImpl<DrugInfoMapper, DrugInfo> implements DrugInfoService {
}
