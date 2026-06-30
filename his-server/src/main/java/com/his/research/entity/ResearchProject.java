package com.his.research.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("research_project")
public class ResearchProject {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String projectNo;
    private String name;
    private String type;
    private Long leaderId;
    private Long deptId;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal budget;
    private String source;
    private Integer status;
    private String description;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private String leaderName;
    @TableField(exist = false)
    private String deptName;
}
