package com.his.finance.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.his.finance.entity.FinIncome;
import com.his.finance.mapper.FinIncomeMapper;
import com.his.finance.service.FinIncomeService;
import org.springframework.stereotype.Service;

@Service
public class FinIncomeServiceImpl extends ServiceImpl<FinIncomeMapper, FinIncome> implements FinIncomeService {
}
