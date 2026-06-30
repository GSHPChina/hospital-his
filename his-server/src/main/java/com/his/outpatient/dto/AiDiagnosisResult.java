package com.his.outpatient.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AiDiagnosisResult {
    private String icdCode;           // ICD编码
    private String diagnosisName;     // 诊断名称
    private String title;             // 诊断标题
    private String chiefComplaint;    // 主诉
    private String presentIllness;    // 现病史
    private String pastHistory;       // 既往史
    private String physicalExam;      // 体格检查
    private String treatmentPlan;     // 治疗方案
    private String guideline;         // 临床指南推荐意见
    private List<Map<String, Object>> drugs;  // 推荐药品
    private double confidence;        // 置信度 0-1
}
