package com.his.finance.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("fin_income")
public class FinIncome {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String incomeNo;
    private String type;
    private BigDecimal amount;
    private Long deptId;
    private Long sourceId;
    private String sourceType;
    private LocalDate incomeDate;
    private String remark;
    private Long operatorId;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
