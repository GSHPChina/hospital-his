package com.his.title.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("title_application")
public class TitleApplication {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String appNo;
    private Long empId;
    private String currentTitle;
    private String applyTitle;
    private LocalDate applyDate;
    private Integer workYears;
    private String education;
    private String major;
    private String workSummary;
    private String achievements;
    private String papers;
    private Integer status;
    private String deptOpinion;
    private String hrOpinion;
    private String committeeOpinion;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private String empName;
}
