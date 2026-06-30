package com.his.outpatient.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("op_medical_record")
public class OpMedicalRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long registerId;
    private Long patientId;
    private Long doctorId;
    private String chiefComplaint;
    private String presentIllness;
    private String pastHistory;
    private String physicalExam;
    private String treatmentPlan;
    private String remark;
    private Integer status;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
