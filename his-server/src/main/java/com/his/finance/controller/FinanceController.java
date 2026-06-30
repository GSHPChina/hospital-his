package com.his.finance.controller;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.his.common.exception.BusinessException;
import com.his.common.result.R;
import com.his.finance.entity.FinExpense;
import com.his.finance.entity.FinIncome;
import com.his.finance.entity.FinMonthlyReport;
import com.his.finance.mapper.FinMonthlyReportMapper;
import com.his.finance.service.FinExpenseService;
import com.his.finance.service.FinIncomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/finance")
@RequiredArgsConstructor
public class FinanceController {

    private final FinIncomeService incomeService;
    private final FinExpenseService expenseService;
    private final FinMonthlyReportMapper monthlyReportMapper;

    @GetMapping("/incomes")
    public R<List<FinIncome>> listIncomes(@RequestParam(required = false) String type,
                                          @RequestParam(required = false) LocalDate date) {
        LambdaQueryWrapper<FinIncome> wrapper = new LambdaQueryWrapper<>();
        if (type != null) {
            wrapper.eq(FinIncome::getType, type);
        }
        if (date != null) {
            wrapper.eq(FinIncome::getIncomeDate, date);
        }
        wrapper.orderByDesc(FinIncome::getCreateTime);
        return R.ok(incomeService.list(wrapper));
    }

    @PostMapping("/incomes")
    public R<Void> addIncome(@RequestBody FinIncome income) {
        income.setIncomeNo("INC" + IdUtil.getSnowflakeNextIdStr());
        Long operatorId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        income.setOperatorId(operatorId);
        if (income.getIncomeDate() == null) {
            income.setIncomeDate(LocalDate.now());
        }
        incomeService.save(income);
        return R.ok();
    }

    @GetMapping("/expenses")
    public R<List<FinExpense>> listExpenses(@RequestParam(required = false) String type,
                                            @RequestParam(required = false) Integer status) {
        LambdaQueryWrapper<FinExpense> wrapper = new LambdaQueryWrapper<>();
        if (type != null) {
            wrapper.eq(FinExpense::getType, type);
        }
        if (status != null) {
            wrapper.eq(FinExpense::getStatus, status);
        }
        wrapper.orderByDesc(FinExpense::getCreateTime);
        return R.ok(expenseService.list(wrapper));
    }

    @PostMapping("/expenses")
    public R<Void> addExpense(@RequestBody FinExpense expense) {
        expense.setExpenseNo("EXP" + IdUtil.getSnowflakeNextIdStr());
        expense.setStatus(0);
        Long operatorId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        expense.setOperatorId(operatorId);
        if (expense.getExpenseDate() == null) {
            expense.setExpenseDate(LocalDate.now());
        }
        expenseService.save(expense);
        return R.ok();
    }

    @PutMapping("/expenses/{id}/approve")
    public R<Void> approveExpense(@PathVariable Long id) {
        FinExpense expense = expenseService.getById(id);
        if (expense == null) {
            throw new BusinessException("支出记录不存在");
        }
        if (expense.getStatus() != 0) {
            throw new BusinessException("当前状态不允许审批");
        }
        Long approverId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        expense.setStatus(1);
        expense.setApproverId(approverId);
        expense.setApproveTime(LocalDateTime.now());
        expenseService.updateById(expense);
        return R.ok();
    }

    @GetMapping("/report/monthly")
    public R<FinMonthlyReport> getMonthlyReport(@RequestParam String ym) {
        LambdaQueryWrapper<FinMonthlyReport> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FinMonthlyReport::getYm, ym);
        FinMonthlyReport report = monthlyReportMapper.selectOne(wrapper);
        return R.ok(report);
    }

    @PostMapping("/report/generate")
    public R<FinMonthlyReport> generateMonthlyReport(@RequestParam String ym) {
        YearMonth yearMonth = YearMonth.parse(ym, DateTimeFormatter.ofPattern("yyyy-MM"));
        LocalDate start = yearMonth.atDay(1);
        LocalDate end = yearMonth.atEndOfMonth();

        // 查询收入
        LambdaQueryWrapper<FinIncome> incomeWrapper = new LambdaQueryWrapper<>();
        incomeWrapper.ge(FinIncome::getIncomeDate, start)
                     .le(FinIncome::getIncomeDate, end);
        List<FinIncome> incomes = incomeService.list(incomeWrapper);

        BigDecimal totalIncome = BigDecimal.ZERO;
        BigDecimal regIncome = BigDecimal.ZERO;
        BigDecimal drugIncome = BigDecimal.ZERO;
        BigDecimal examIncome = BigDecimal.ZERO;
        BigDecimal treatmentIncome = BigDecimal.ZERO;
        for (FinIncome income : incomes) {
            totalIncome = totalIncome.add(income.getAmount());
            String t = income.getType();
            if ("REG".equals(t)) {
                regIncome = regIncome.add(income.getAmount());
            } else if ("DRUG".equals(t)) {
                drugIncome = drugIncome.add(income.getAmount());
            } else if ("EXAM".equals(t)) {
                examIncome = examIncome.add(income.getAmount());
            } else if ("TREATMENT".equals(t)) {
                treatmentIncome = treatmentIncome.add(income.getAmount());
            }
        }

        // 查询已审批支出
        LambdaQueryWrapper<FinExpense> expenseWrapper = new LambdaQueryWrapper<>();
        expenseWrapper.ge(FinExpense::getExpenseDate, start)
                      .le(FinExpense::getExpenseDate, end)
                      .eq(FinExpense::getStatus, 1);
        List<FinExpense> expenses = expenseService.list(expenseWrapper);

        BigDecimal totalExpense = BigDecimal.ZERO;
        BigDecimal salaryExpense = BigDecimal.ZERO;
        BigDecimal drugPurchase = BigDecimal.ZERO;
        BigDecimal equipmentExpense = BigDecimal.ZERO;
        for (FinExpense expense : expenses) {
            totalExpense = totalExpense.add(expense.getAmount());
            String t = expense.getType();
            if ("SALARY".equals(t)) {
                salaryExpense = salaryExpense.add(expense.getAmount());
            } else if ("DRUG_PURCHASE".equals(t)) {
                drugPurchase = drugPurchase.add(expense.getAmount());
            } else if ("EQUIPMENT".equals(t)) {
                equipmentExpense = equipmentExpense.add(expense.getAmount());
            }
        }

        // 删除已有同月报表后重新生成
        LambdaQueryWrapper<FinMonthlyReport> deleteWrapper = new LambdaQueryWrapper<>();
        deleteWrapper.eq(FinMonthlyReport::getYm, ym);
        monthlyReportMapper.delete(deleteWrapper);

        FinMonthlyReport report = new FinMonthlyReport();
        report.setYm(ym);
        report.setTotalIncome(totalIncome);
        report.setTotalExpense(totalExpense);
        report.setProfit(totalIncome.subtract(totalExpense));
        report.setRegIncome(regIncome);
        report.setDrugIncome(drugIncome);
        report.setExamIncome(examIncome);
        report.setTreatmentIncome(treatmentIncome);
        report.setSalaryExpense(salaryExpense);
        report.setDrugPurchase(drugPurchase);
        report.setEquipmentExpense(equipmentExpense);
        monthlyReportMapper.insert(report);

        return R.ok(report);
    }
}
