package com.his.outpatient.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("op_schedule")
public class OpSchedule {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long doctorId;
    private Long deptId;
    private LocalDate scheduleDate;
    private String timeSlot;
    private Integer totalNum;
    private Integer usedNum;
    private String feeType;
    private Integer status;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private String doctorName;
    @TableField(exist = false)
    private String deptName;
}
