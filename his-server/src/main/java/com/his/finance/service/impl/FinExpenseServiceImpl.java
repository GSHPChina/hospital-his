package com.his.finance.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.his.finance.entity.FinExpense;
import com.his.finance.mapper.FinExpenseMapper;
import com.his.finance.service.FinExpenseService;
import org.springframework.stereotype.Service;

@Service
public class FinExpenseServiceImpl extends ServiceImpl<FinExpenseMapper, FinExpense> implements FinExpenseService {
}
