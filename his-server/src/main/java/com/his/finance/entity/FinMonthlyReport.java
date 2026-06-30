package com.his.finance.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("fin_monthly_report")
public class FinMonthlyReport {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String ym;
    private BigDecimal totalIncome;
    private BigDecimal totalExpense;
    private BigDecimal profit;
    private BigDecimal regIncome;
    private BigDecimal drugIncome;
    private BigDecimal examIncome;
    private BigDecimal treatmentIncome;
    private BigDecimal salaryExpense;
    private BigDecimal drugPurchase;
    private BigDecimal equipmentExpense;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
