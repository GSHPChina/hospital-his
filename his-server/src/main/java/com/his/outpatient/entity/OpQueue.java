package com.his.outpatient.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("op_queue")
public class OpQueue {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String queueNo;
    private Long doctorId;
    private Long patientId;
    private Long appointmentId;
    private Long registerId;
    private LocalDate queueDate;
    private Integer status;
    private LocalDateTime callTime;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private String patientName;
    @TableField(exist = false)
    private String doctorName;
}
