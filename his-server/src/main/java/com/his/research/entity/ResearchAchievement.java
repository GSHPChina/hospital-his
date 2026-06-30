package com.his.research.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("research_achievement")
public class ResearchAchievement {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String type;
    private Long projectId;
    private Long holderId;
    private LocalDate obtainDate;
    private String certificateNo;
    private String description;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private String holderName;
}
