package com.his.pharmacy.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("drug_stock")
public class DrugStock {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long drugId;
    private String batchNo;
    private Integer quantity;
    private Integer safeStock;
    private LocalDate expireDate;
    private Integer status;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private String drugName;
    @TableField(exist = false)
    private String drugSpec;
}
