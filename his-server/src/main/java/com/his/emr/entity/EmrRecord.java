package com.his.emr.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 病历记录实体
 */
@Data
@TableName("emr_record")
public class EmrRecord {

    /**
     * 记录ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 病历号
     */
    private String recordNo;

    /**
     * 挂号ID
     */
    private Long registerId;

    /**
     * 患者ID
     */
    private Long patientId;

    /**
     * 医生ID
     */
    private Long doctorId;

    /**
     * 模板ID
     */
    private Long templateId;

    /**
     * 主诉
     */
    private String chiefComplaint;

    /**
     * 现病史
     */
    private String presentIllness;

    /**
     * 既往史
     */
    private String pastHistory;

    /**
     * 个人史
     */
    private String personalHistory;

    /**
     * 家族史
     */
    private String familyHistory;

    /**
     * 体格检查
     */
    private String physicalExam;

    /**
     * 生命体征
     */
    private String vitalSigns;

    /**
     * 辅助检查
     */
    private String auxiliaryExam;

    /**
     * 诊断
     */
    private String diagnosis;

    /**
     * 治疗方案
     */
    private String treatmentPlan;

    /**
     * 处方
     */
    private String prescription;

    /**
     * 医嘱
     */
    private String doctorAdvice;

    /**
     * 随访计划
     */
    private String followUp;

    /**
     * 状态（0-草稿 1-已提交 2-已审核 3-驳回）
     */
    private Integer status;

    /**
     * 提交时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime submitTime;

    /**
     * 审核时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime auditTime;

    /**
     * 审核人ID
     */
    private Long auditBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 患者姓名（非数据库字段）
     */
    @TableField(exist = false)
    private String patientName;

    /**
     * 医生姓名（非数据库字段）
     */
    @TableField(exist = false)
    private String doctorName;
}
