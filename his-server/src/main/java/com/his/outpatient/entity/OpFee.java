package com.his.outpatient.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("op_fee")
public class OpFee {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String feeNo;
    private Long registerId;
    private Long patientId;
    private Long prescriptionId;
    private BigDecimal totalAmount;
    private String payType;
    private Integer payStatus;
    private Long operatorId;
    private LocalDateTime payTime;
    private LocalDateTime refundTime;
    private String remark;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private String patientName;
    @TableField(exist = false)
    private String operatorName;
}
