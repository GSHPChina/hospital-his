package com.his.adverse.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("adverse_event_log")
public class AdverseEventLog {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long eventId;
    private Long operatorId;
    private String action;
    private String content;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
