package com.his.equipment.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("eq_equipment")
public class EqEquipment {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String equipNo;
    private String name;
    private Long categoryId;
    private String brand;
    private String model;
    private String manufacturer;
    private String serialNo;
    private Long deptId;
    private String location;
    private LocalDate purchaseDate;
    private BigDecimal purchasePrice;
    private LocalDate warrantyDate;
    private Integer depreciationYears;
    private BigDecimal currentValue;
    private Integer status;
    private String responsiblePerson;
    private String responsiblePhone;
    private String remark;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private String categoryName;
    @TableField(exist = false)
    private String deptName;
}
