package com.his.performance.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("perf_indicator")
public class PerfIndicator {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long templateId;
    private String name;
    private String category;
    private Integer score;
    private String description;
    private Integer sortOrder;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
