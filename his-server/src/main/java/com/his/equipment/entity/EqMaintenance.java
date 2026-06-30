package com.his.equipment.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("eq_maintenance")
public class EqMaintenance {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long equipId;
    private String type;
    private String description;
    private BigDecimal cost;
    private String technician;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer status;
    private String result;
    private Long operatorId;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private String equipName;
}
