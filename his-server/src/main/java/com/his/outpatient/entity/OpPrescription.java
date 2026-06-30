package com.his.outpatient.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@TableName("op_prescription")
public class OpPrescription {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String prescriptionNo;
    private Long registerId;
    private Long patientId;
    private Long doctorId;
    private String type;
    private BigDecimal totalAmount;
    private Integer status;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private List<OpPrescriptionItem> items;
    @TableField(exist = false)
    private String patientName;
    @TableField(exist = false)
    private String doctorName;
}
