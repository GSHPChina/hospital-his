package com.his.pharmacy.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("drug_info")
public class DrugInfo {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String drugCode;
    private String drugName;
    private String genericName;
    private String spec;
    private String unit;
    private String manufacturer;
    private String category;
    private BigDecimal unitPrice;
    private Integer status;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
