package com.his.performance.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("perf_score_detail")
public class PerfScoreDetail {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long recordId;
    private Long indicatorId;
    private Integer score;
    private String remark;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
