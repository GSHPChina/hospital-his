package com.his.emr.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 病历模板实体
 */
@Data
@TableName("emr_template")
public class EmrTemplate {

    /**
     * 模板ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 模板名称
     */
    private String name;

    /**
     * 科室ID
     */
    private Long deptId;

    /**
     * 模板分类
     */
    private String category;

    /**
     * 模板内容
     */
    private String content;

    /**
     * 创建人ID
     */
    private Long creatorId;

    /**
     * 状态（0-禁用 1-启用）
     */
    private Integer status;

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
     * 创建人姓名（非数据库字段）
     */
    @TableField(exist = false)
    private String creatorName;
}
