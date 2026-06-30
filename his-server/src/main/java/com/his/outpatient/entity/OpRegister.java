package com.his.outpatient.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("op_register")
public class OpRegister {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String registerNo;
    private Long patientId;
    private Long deptId;
    private Long doctorId;
    private Long scheduleId;
    private String registerType;
    private BigDecimal fee;
    private Integer status;
    private LocalDateTime registerTime;
    private LocalDateTime cancelTime;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private String patientName;
    @TableField(exist = false)
    private String deptName;
    @TableField(exist = false)
    private String doctorName;
}
