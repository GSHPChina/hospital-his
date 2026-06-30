package com.his.pharmacy.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("drug_dispensing")
public class DrugDispensing {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String dispensingNo;
    private Long prescriptionId;
    private Long patientId;
    private Long pharmacistId;
    private Integer status;
    private LocalDateTime dispensingTime;
    private String remark;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private String patientName;
    @TableField(exist = false)
    private String pharmacistName;
}
