package com.his.outpatient.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("op_appointment")
public class OpAppointment {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String appointmentNo;
    private Long patientId;
    private Long deptId;
    private Long doctorId;
    private Long scheduleId;
    private LocalDate appointmentDate;
    private String timeSlot;
    private Integer queueNo;
    private String feeType;
    private BigDecimal fee;
    private Integer status;
    private String cancelReason;
    private String remark;
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
