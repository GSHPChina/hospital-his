package com.his.hr.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("hr_salary")
public class HrSalary {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long empId;
    private String ym;
    private BigDecimal baseSalary;
    private BigDecimal positionSalary;
    private BigDecimal bonus;
    private BigDecimal allowance;
    private BigDecimal overtimePay;
    private BigDecimal deduction;
    private BigDecimal socialInsurance;
    private BigDecimal tax;
    private BigDecimal netSalary;
    private Integer status;
    private LocalDateTime payTime;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private String empName;
}
