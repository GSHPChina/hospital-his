-- ============================================
-- 医院HIS系统 - 数据库初始化脚本
-- ============================================

CREATE DATABASE IF NOT EXISTS his_db DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE his_db;

-- ============================================
-- 一、系统管理模块
-- ============================================

-- 科室表
CREATE TABLE sys_dept (
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    parent_id   BIGINT DEFAULT 0 COMMENT '父科室ID',
    name        VARCHAR(50) NOT NULL COMMENT '科室名称',
    code        VARCHAR(30) COMMENT '科室编码',
    type        TINYINT DEFAULT 1 COMMENT '类型 1-门诊 2-住院 3-医技 4-行政',
    sort_order  INT DEFAULT 0 COMMENT '排序',
    status      TINYINT DEFAULT 1 COMMENT '状态 1-启用 0-停用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_parent_id (parent_id)
) COMMENT '科室表';

-- 用户表
CREATE TABLE sys_user (
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    username    VARCHAR(50) NOT NULL UNIQUE COMMENT '登录账号',
    password    VARCHAR(100) NOT NULL COMMENT '密码(BCrypt)',
    real_name   VARCHAR(50) NOT NULL COMMENT '真实姓名',
    gender      TINYINT DEFAULT 0 COMMENT '性别 0-未知 1-男 2-女',
    phone       VARCHAR(20) COMMENT '手机号',
    id_card     VARCHAR(20) COMMENT '身份证号',
    dept_id     BIGINT COMMENT '所属科室ID',
    post        VARCHAR(50) COMMENT '职务',
    avatar      VARCHAR(200) COMMENT '头像URL',
    status      TINYINT DEFAULT 1 COMMENT '状态 1-启用 0-停用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_dept_id (dept_id),
    UNIQUE INDEX uk_username (username)
) COMMENT '系统用户表';

-- 角色表
CREATE TABLE sys_role (
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(50) NOT NULL COMMENT '角色名称',
    code        VARCHAR(50) NOT NULL UNIQUE COMMENT '角色编码',
    description VARCHAR(200) COMMENT '描述',
    sort_order  INT DEFAULT 0 COMMENT '排序',
    status      TINYINT DEFAULT 1 COMMENT '状态 1-启用 0-停用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) COMMENT '角色表';

-- 用户-角色关联表
CREATE TABLE sys_user_role (
    id      BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    UNIQUE INDEX uk_user_role (user_id, role_id)
) COMMENT '用户角色关联表';

-- 菜单表
CREATE TABLE sys_menu (
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    parent_id   BIGINT DEFAULT 0 COMMENT '父菜单ID',
    name        VARCHAR(50) NOT NULL COMMENT '菜单名称',
    path        VARCHAR(200) COMMENT '路由路径',
    component   VARCHAR(200) COMMENT '组件路径',
    icon        VARCHAR(50) COMMENT '图标',
    sort_order  INT DEFAULT 0 COMMENT '排序',
    type        TINYINT DEFAULT 1 COMMENT '类型 1-目录 2-菜单 3-按钮',
    permission  VARCHAR(100) COMMENT '权限标识',
    visible     TINYINT DEFAULT 1 COMMENT '是否可见',
    status      TINYINT DEFAULT 1 COMMENT '状态 1-启用 0-停用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_parent_id (parent_id)
) COMMENT '菜单权限表';

-- 角色-菜单关联表
CREATE TABLE sys_role_menu (
    id      BIGINT PRIMARY KEY AUTO_INCREMENT,
    role_id BIGINT NOT NULL,
    menu_id BIGINT NOT NULL,
    UNIQUE INDEX uk_role_menu (role_id, menu_id)
) COMMENT '角色菜单关联表';

-- 数据字典分类表
CREATE TABLE sys_dict (
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(100) NOT NULL COMMENT '字典名称',
    code        VARCHAR(100) NOT NULL UNIQUE COMMENT '字典编码',
    description VARCHAR(200) COMMENT '描述',
    status      TINYINT DEFAULT 1 COMMENT '状态 1-启用 0-停用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) COMMENT '数据字典分类表';

-- 数据字典项表
CREATE TABLE sys_dict_item (
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    dict_id     BIGINT NOT NULL COMMENT '字典分类ID',
    label       VARCHAR(100) NOT NULL COMMENT '显示文本',
    value       VARCHAR(100) NOT NULL COMMENT '字典值',
    sort_order  INT DEFAULT 0 COMMENT '排序',
    status      TINYINT DEFAULT 1 COMMENT '状态 1-启用 0-停用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_dict_id (dict_id)
) COMMENT '数据字典项表';

-- ============================================
-- 二、门诊模块
-- ============================================

-- 患者信息表
CREATE TABLE op_patient (
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(50) NOT NULL COMMENT '患者姓名',
    gender      TINYINT NOT NULL COMMENT '性别 1-男 2-女',
    birth_date  DATE COMMENT '出生日期',
    id_card     VARCHAR(20) COMMENT '身份证号',
    phone       VARCHAR(20) COMMENT '手机号',
    address     VARCHAR(200) COMMENT '住址',
    blood_type  VARCHAR(10) COMMENT '血型',
    allergy     VARCHAR(500) COMMENT '过敏史',
    medical_insurance_no VARCHAR(30) COMMENT '医保卡号',
    patient_no  VARCHAR(30) NOT NULL UNIQUE COMMENT '患者编号',
    status      TINYINT DEFAULT 1 COMMENT '状态 1-有效 0-无效',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_id_card (id_card),
    INDEX idx_phone (phone),
    INDEX idx_name (name)
) COMMENT '患者信息表';

-- 医生排班表
CREATE TABLE op_schedule (
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    doctor_id   BIGINT NOT NULL COMMENT '医生ID',
    dept_id     BIGINT NOT NULL COMMENT '科室ID',
    schedule_date DATE NOT NULL COMMENT '排班日期',
    time_slot   VARCHAR(20) NOT NULL COMMENT '时段 AM-上午 PM-下午',
    total_num   INT DEFAULT 0 COMMENT '总号源数',
    used_num    INT DEFAULT 0 COMMENT '已用号源数',
    fee_type    VARCHAR(20) DEFAULT 'NORMAL' COMMENT 'NORMAL-普通 EXPERT-专家 EMERGENCY-急诊',
    status      TINYINT DEFAULT 1 COMMENT '状态 1-正常 0-停诊',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_doctor_date (doctor_id, schedule_date),
    INDEX idx_dept_date (dept_id, schedule_date)
) COMMENT '医生排班表';

-- 挂号记录表
CREATE TABLE op_register (
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    register_no VARCHAR(30) NOT NULL UNIQUE COMMENT '挂号单号',
    patient_id  BIGINT NOT NULL COMMENT '患者ID',
    dept_id     BIGINT NOT NULL COMMENT '科室ID',
    doctor_id   BIGINT COMMENT '医生ID',
    schedule_id BIGINT COMMENT '排班ID',
    register_type VARCHAR(20) DEFAULT 'NORMAL' COMMENT 'NORMAL-普通 EXPERT-专家 EMERGENCY-急诊',
    fee         DECIMAL(10,2) DEFAULT 0 COMMENT '挂号费',
    status      TINYINT DEFAULT 1 COMMENT '状态 1-已挂号 2-已就诊 3-已退号',
    register_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '挂号时间',
    cancel_time DATETIME COMMENT '退号时间',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_patient_id (patient_id),
    INDEX idx_dept_date (dept_id, register_time),
    INDEX idx_doctor_date (doctor_id, register_time)
) COMMENT '挂号记录表';

-- 门诊病历表
CREATE TABLE op_medical_record (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    register_id     BIGINT NOT NULL COMMENT '挂号ID',
    patient_id      BIGINT NOT NULL COMMENT '患者ID',
    doctor_id       BIGINT NOT NULL COMMENT '医生ID',
    chief_complaint TEXT COMMENT '主诉',
    present_illness TEXT COMMENT '现病史',
    past_history    TEXT COMMENT '既往史',
    physical_exam   TEXT COMMENT '体格检查',
    treatment_plan  TEXT COMMENT '处理方案',
    remark          TEXT COMMENT '备注',
    status          TINYINT DEFAULT 1 COMMENT '状态 1-草稿 2-已提交',
    create_time     DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_register_id (register_id),
    INDEX idx_patient_id (patient_id)
) COMMENT '门诊病历表';

-- 诊断记录表
CREATE TABLE op_diagnosis (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    register_id     BIGINT NOT NULL COMMENT '挂号ID',
    patient_id      BIGINT NOT NULL COMMENT '患者ID',
    doctor_id       BIGINT NOT NULL COMMENT '医生ID',
    icd_code        VARCHAR(30) COMMENT 'ICD编码',
    diagnosis_name  VARCHAR(200) NOT NULL COMMENT '诊断名称',
    diagnosis_type  TINYINT DEFAULT 1 COMMENT '类型 1-西医 2-中医',
    is_primary      TINYINT DEFAULT 0 COMMENT '是否主诊断 1-是 0-否',
    remark          VARCHAR(500) COMMENT '备注',
    create_time     DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_register_id (register_id)
) COMMENT '诊断记录表';

-- 处方头表
CREATE TABLE op_prescription (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    prescription_no VARCHAR(30) NOT NULL UNIQUE COMMENT '处方号',
    register_id     BIGINT NOT NULL COMMENT '挂号ID',
    patient_id      BIGINT NOT NULL COMMENT '患者ID',
    doctor_id       BIGINT NOT NULL COMMENT '开方医生ID',
    type            VARCHAR(20) DEFAULT 'DRUG' COMMENT 'DRUG-药品 EXAM-检查 LAB-检验',
    total_amount    DECIMAL(10,2) DEFAULT 0 COMMENT '总金额',
    status          TINYINT DEFAULT 1 COMMENT '状态 1-已开立 2-已收费 3-已发药 4-已退费 5-已作废',
    create_time     DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_register_id (register_id),
    INDEX idx_patient_id (patient_id)
) COMMENT '处方头表';

-- 处方明细表
CREATE TABLE op_prescription_item (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    prescription_id BIGINT NOT NULL COMMENT '处方ID',
    drug_id         BIGINT COMMENT '药品ID',
    drug_name       VARCHAR(100) NOT NULL COMMENT '药品名称',
    drug_spec       VARCHAR(100) COMMENT '规格',
    unit            VARCHAR(20) COMMENT '单位',
    quantity        INT DEFAULT 1 COMMENT '数量',
    unit_price      DECIMAL(10,2) DEFAULT 0 COMMENT '单价',
    amount          DECIMAL(10,2) DEFAULT 0 COMMENT '金额',
    dosage          VARCHAR(50) COMMENT '用量',
    frequency       VARCHAR(50) COMMENT '频次',
    usage_method    VARCHAR(50) COMMENT '用法',
    days            INT DEFAULT 1 COMMENT '天数',
    remark          VARCHAR(200) COMMENT '备注',
    create_time     DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_prescription_id (prescription_id)
) COMMENT '处方明细表';

-- 门诊收费表
CREATE TABLE op_fee (
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    fee_no      VARCHAR(30) NOT NULL UNIQUE COMMENT '收费单号',
    register_id BIGINT NOT NULL COMMENT '挂号ID',
    patient_id  BIGINT NOT NULL COMMENT '患者ID',
    prescription_id BIGINT COMMENT '处方ID',
    total_amount DECIMAL(10,2) NOT NULL COMMENT '总金额',
    pay_type    VARCHAR(20) DEFAULT 'CASH' COMMENT 'CASH-现金 MEDICAL_INSURANCE-医保 WECHAT-微信 ALIPAY-支付宝',
    pay_status  TINYINT DEFAULT 0 COMMENT '0-未支付 1-已支付 2-已退费',
    operator_id BIGINT COMMENT '收费员ID',
    pay_time    DATETIME COMMENT '支付时间',
    refund_time DATETIME COMMENT '退费时间',
    remark      VARCHAR(200) COMMENT '备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_register_id (register_id),
    INDEX idx_fee_no (fee_no)
) COMMENT '门诊收费表';

-- ============================================
-- 三、药房模块
-- ============================================

-- 药品信息表
CREATE TABLE drug_info (
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    drug_code   VARCHAR(30) NOT NULL UNIQUE COMMENT '药品编码',
    drug_name   VARCHAR(100) NOT NULL COMMENT '药品名称',
    generic_name VARCHAR(100) COMMENT '通用名',
    spec        VARCHAR(100) COMMENT '规格',
    unit        VARCHAR(20) COMMENT '单位',
    manufacturer VARCHAR(100) COMMENT '生产厂家',
    category    VARCHAR(50) COMMENT '药品分类',
    unit_price  DECIMAL(10,2) DEFAULT 0 COMMENT '单价',
    status      TINYINT DEFAULT 1 COMMENT '状态 1-启用 0-停用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_drug_name (drug_name)
) COMMENT '药品信息表';

-- 药品库存表
CREATE TABLE drug_stock (
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    drug_id     BIGINT NOT NULL COMMENT '药品ID',
    batch_no    VARCHAR(50) COMMENT '批次号',
    quantity    INT DEFAULT 0 COMMENT '库存数量',
    safe_stock  INT DEFAULT 10 COMMENT '安全库存',
    expire_date DATE COMMENT '有效期',
    status      TINYINT DEFAULT 1 COMMENT '状态 1-正常 2-临期 3-过期',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_drug_id (drug_id),
    INDEX idx_expire_date (expire_date)
) COMMENT '药品库存表';

-- 发药记录表
CREATE TABLE drug_dispensing (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    dispensing_no   VARCHAR(30) NOT NULL UNIQUE COMMENT '发药单号',
    prescription_id BIGINT NOT NULL COMMENT '处方ID',
    patient_id      BIGINT NOT NULL COMMENT '患者ID',
    pharmacist_id   BIGINT COMMENT '药剂师ID',
    status          TINYINT DEFAULT 1 COMMENT '状态 1-待发药 2-已发药 3-已退药',
    dispensing_time DATETIME COMMENT '发药时间',
    remark          VARCHAR(200) COMMENT '备注',
    create_time     DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_prescription_id (prescription_id)
) COMMENT '发药记录表';

-- 库存变动日志表
CREATE TABLE drug_stock_log (
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    drug_id     BIGINT NOT NULL COMMENT '药品ID',
    type        VARCHAR(20) NOT NULL COMMENT 'INBOUND-入库 OUTBOUND-出库 ADJUST-调整',
    quantity    INT NOT NULL COMMENT '变动数量(正数入库 负数出库)',
    before_qty  INT COMMENT '变动前数量',
    after_qty   INT COMMENT '变动后数量',
    operator_id BIGINT COMMENT '操作人ID',
    remark      VARCHAR(200) COMMENT '备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_drug_id (drug_id)
) COMMENT '库存变动日志表';
