package com.his.research.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("research_paper")
public class ResearchPaper {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String title;
    private String authors;
    private Long firstAuthorId;
    private String journal;
    private LocalDate publishDate;
    private String level;
    private BigDecimal impactFactor;
    private Long projectId;
    private Integer status;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private String firstAuthorName;
}
