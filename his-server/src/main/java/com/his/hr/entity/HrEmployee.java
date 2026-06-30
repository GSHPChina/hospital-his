package com.his.hr.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("hr_employee")
public class HrEmployee {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String empNo;
    private Long userId;
    private String name;
    private Integer gender;
    private LocalDate birthDate;
    private String idCard;
    private String phone;
    private String email;
    private Long deptId;
    private String position;
    private String title;
    private String education;
    private String major;
    private String graduateSchool;
    private LocalDate workDate;
    private LocalDate contractEnd;
    private Integer status;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private String deptName;
}
