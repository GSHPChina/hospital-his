package com.his.title.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("title_level")
public class TitleLevel {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private Integer level;
    private String description;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
