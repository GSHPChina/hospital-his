package com.his.outpatient.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("op_patient")
public class OpPatient {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private Integer gender;
    private LocalDate birthDate;
    private String idCard;
    private String phone;
    private String address;
    private String bloodType;
    private String allergy;
    private String medicalInsuranceNo;
    private String patientNo;
    private Integer status;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
