package com.his.performance.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("perf_record")
public class PerfRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long templateId;
    private Long empId;
    private Integer year;
    private Integer quarter;
    private Integer totalScore;
    private String level;
    private Long evaluatorId;
    private LocalDateTime evaluateTime;
    private Integer status;
    private String remark;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private String empName;
    @TableField(exist = false)
    private String templateName;
}
