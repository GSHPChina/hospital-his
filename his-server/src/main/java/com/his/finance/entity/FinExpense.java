package com.his.finance.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("fin_expense")
public class FinExpense {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String expenseNo;
    private String type;
    private BigDecimal amount;
    private Long deptId;
    private LocalDate expenseDate;
    private String description;
    private Integer status;
    private Long approverId;
    private LocalDateTime approveTime;
    private Long operatorId;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
