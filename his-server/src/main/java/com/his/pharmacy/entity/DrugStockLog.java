package com.his.pharmacy.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("drug_stock_log")
public class DrugStockLog {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long drugId;
    private String type;
    private Integer quantity;
    private Integer beforeQty;
    private Integer afterQty;
    private Long operatorId;
    private String remark;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
