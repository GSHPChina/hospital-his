package com.his.outpatient.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("op_diagnosis")
public class OpDiagnosis {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long registerId;
    private Long patientId;
    private Long doctorId;
    private String icdCode;
    private String diagnosisName;
    private Integer diagnosisType;
    private Integer isPrimary;
    private String remark;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
