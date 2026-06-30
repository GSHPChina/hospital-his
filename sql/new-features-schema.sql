USE his_db;

-- ============================================
-- 预约挂号系统
-- ============================================

-- 预约挂号表
CREATE TABLE IF NOT EXISTS op_appointment (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    appointment_no VARCHAR(30) NOT NULL UNIQUE,
    patient_id BIGINT NOT NULL,
    dept_id BIGINT NOT NULL,
    doctor_id BIGINT NOT NULL,
    schedule_id BIGINT,
    appointment_date DATE NOT NULL,
    time_slot VARCHAR(20) NOT NULL,
    queue_no INT DEFAULT 0,
    fee_type VARCHAR(20) DEFAULT 'NORMAL',
    fee DECIMAL(10,2) DEFAULT 0,
    status TINYINT DEFAULT 1 COMMENT '1-已预约 2-已签到 3-已就诊 4-已取消 5-爽约',
    cancel_reason VARCHAR(200),
    remark VARCHAR(200),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_patient (patient_id),
    INDEX idx_doctor_date (doctor_id, appointment_date),
    INDEX idx_dept_date (dept_id, appointment_date),
    INDEX idx_status (status)
);

-- 排队叫号表
CREATE TABLE IF NOT EXISTS op_queue (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    queue_no VARCHAR(20) NOT NULL,
    doctor_id BIGINT NOT NULL,
    patient_id BIGINT NOT NULL,
    appointment_id BIGINT,
    register_id BIGINT,
    queue_date DATE NOT NULL,
    status TINYINT DEFAULT 1 COMMENT '1-等待中 2-叫号中 3-就诊中 4-已完成 5-过号',
    call_time DATETIME,
    start_time DATETIME,
    end_time DATETIME,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_doctor_date (doctor_id, queue_date),
    INDEX idx_status (status)
);

-- ============================================
-- 电子病历系统
-- ============================================

-- 病历模板表
CREATE TABLE IF NOT EXISTS emr_template (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    dept_id BIGINT,
    category VARCHAR(50) COMMENT '模板分类',
    content TEXT COMMENT '模板内容JSON',
    creator_id BIGINT,
    status TINYINT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_dept (dept_id),
    INDEX idx_category (category)
);

-- 电子病历表
CREATE TABLE IF NOT EXISTS emr_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    record_no VARCHAR(30) NOT NULL UNIQUE,
    register_id BIGINT NOT NULL,
    patient_id BIGINT NOT NULL,
    doctor_id BIGINT NOT NULL,
    template_id BIGINT,
    chief_complaint TEXT COMMENT '主诉',
    present_illness TEXT COMMENT '现病史',
    past_history TEXT COMMENT '既往史',
    personal_history TEXT COMMENT '个人史',
    family_history TEXT COMMENT '家族史',
    physical_exam TEXT COMMENT '体格检查',
    vital_signs TEXT COMMENT '生命体征JSON',
    auxiliary_exam TEXT COMMENT '辅助检查',
    diagnosis TEXT COMMENT '诊断JSON',
    treatment_plan TEXT COMMENT '治疗方案',
    prescription TEXT COMMENT '处方JSON',
    doctor_advice TEXT COMMENT '医嘱',
    follow_up VARCHAR(200) COMMENT '随访要求',
    status TINYINT DEFAULT 1 COMMENT '1-草稿 2-已提交 3-已审核 4-已归档',
    submit_time DATETIME,
    audit_time DATETIME,
    audit_by BIGINT,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_register (register_id),
    INDEX idx_patient (patient_id),
    INDEX idx_doctor (doctor_id),
    INDEX idx_status (status)
);

-- 病历质控表
CREATE TABLE IF NOT EXISTS emr_quality_check (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    record_id BIGINT NOT NULL,
    checker_id BIGINT NOT NULL,
    check_type VARCHAR(20) COMMENT 'AUTO-自动 MANUAL-手动',
    score INT DEFAULT 100,
    issues TEXT COMMENT '问题JSON',
    status TINYINT DEFAULT 1 COMMENT '1-合格 2-不合格 3-整改中',
    check_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_record (record_id)
);

-- ============================================
-- 设备管理系统
-- ============================================

-- 设备分类表
CREATE TABLE IF NOT EXISTS eq_category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    parent_id BIGINT DEFAULT 0,
    name VARCHAR(100) NOT NULL,
    code VARCHAR(30),
    description VARCHAR(200),
    sort_order INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 设备台账表
CREATE TABLE IF NOT EXISTS eq_equipment (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    equip_no VARCHAR(30) NOT NULL UNIQUE,
    name VARCHAR(200) NOT NULL,
    category_id BIGINT,
    brand VARCHAR(100),
    model VARCHAR(100),
    manufacturer VARCHAR(200),
    serial_no VARCHAR(100),
    dept_id BIGINT,
    location VARCHAR(200),
    purchase_date DATE,
    purchase_price DECIMAL(12,2),
    warranty_date DATE,
    depreciation_years INT DEFAULT 5,
    current_value DECIMAL(12,2),
    status TINYINT DEFAULT 1 COMMENT '1-正常 2-维修中 3-报废 4-闲置',
    responsible_person VARCHAR(50),
    responsible_phone VARCHAR(20),
    remark TEXT,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_category (category_id),
    INDEX idx_dept (dept_id),
    INDEX idx_status (status)
);

-- 设备维修记录表
CREATE TABLE IF NOT EXISTS eq_maintenance (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    equip_id BIGINT NOT NULL,
    type VARCHAR(20) COMMENT 'REPAIR-维修 MAINTENANCE-保养 INSPECTION-巡检',
    description TEXT NOT NULL,
    cost DECIMAL(10,2) DEFAULT 0,
    technician VARCHAR(50),
    start_time DATETIME,
    end_time DATETIME,
    status TINYINT DEFAULT 1 COMMENT '1-待处理 2-进行中 3-已完成',
    result TEXT,
    operator_id BIGINT,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_equip (equip_id),
    INDEX idx_type (type),
    INDEX idx_status (status)
);

-- 设备折旧记录表
CREATE TABLE IF NOT EXISTS eq_depreciation (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    equip_id BIGINT NOT NULL,
    ym VARCHAR(7) NOT NULL,
    original_value DECIMAL(12,2),
    depreciation_amount DECIMAL(10,2),
    accumulated_depreciation DECIMAL(12,2),
    net_value DECIMAL(12,2),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_equip_month (equip_id, ym)
);

SELECT 'New features tables created!' AS result;
