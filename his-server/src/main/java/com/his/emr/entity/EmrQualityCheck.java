package com.his.emr.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 病历质量检查实体
 */
@Data
@TableName("emr_quality_check")
public class EmrQualityCheck {

    /**
     * 检查ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 病历记录ID
     */
    private Long recordId;

    /**
     * 检查人ID
     */
    private Long checkerId;

    /**
     * 检查类型
     */
    private String checkType;

    /**
     * 评分
     */
    private Integer score;

    /**
     * 存在问题
     */
    private String issues;

    /**
     * 状态（0-不合格 1-合格）
     */
    private Integer status;

    /**
     * 检查时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime checkTime;

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
}
