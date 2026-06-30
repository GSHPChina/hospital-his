package com.his.adverse.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("adverse_event")
public class AdverseEvent {
    @TableId(type = IdType.AUTO)
    private Long id;
    @TableField("event_no")
    private String eventNo;
    private String type;
    private Integer level;
    private String title;
    private String description;
    private LocalDateTime eventTime;
    private String eventPlace;
    private Long patientId;
    private Long reporterId;
    private String reporterDept;
    private LocalDateTime reportTime;
    private Integer status;
    private Long handlerId;
    private LocalDateTime handleTime;
    private String handleResult;
    private String causeAnalysis;
    private String improvement;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private String reporterName;
    @TableField(exist = false)
    private String handlerName;
    @TableField(exist = false)
    private String patientName;
}
