package com.his.title.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("title_review")
public class TitleReview {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long applicationId;
    private Long reviewerId;
    private String reviewType;
    private Integer score;
    private String opinion;
    private String result;
    private LocalDateTime reviewTime;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
