package com.his.research.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("research_member")
public class ResearchMember {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long projectId;
    private Long empId;
    private String role;
    private Integer sortOrder;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
