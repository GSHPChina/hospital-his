package com.his.outpatient.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("op_prescription_item")
public class OpPrescriptionItem {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long prescriptionId;
    private Long drugId;
    private String drugName;
    private String drugSpec;
    private String unit;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal amount;
    private String dosage;
    private String frequency;
    private String usageMethod;
    private Integer days;
    private String remark;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
