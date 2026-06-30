-- H2 Compatible Schema for HIS System

CREATE TABLE IF NOT EXISTS sys_dept (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    parent_id BIGINT DEFAULT 0,
    name VARCHAR(50) NOT NULL,
    code VARCHAR(30),
    type INT DEFAULT 1,
    sort_order INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS sys_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    real_name VARCHAR(50) NOT NULL,
    gender INT DEFAULT 0,
    phone VARCHAR(20),
    id_card VARCHAR(20),
    dept_id BIGINT,
    post VARCHAR(50),
    avatar VARCHAR(200),
    status INT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS sys_role (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    code VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(200),
    sort_order INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS sys_user_role (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL
);

CREATE TABLE IF NOT EXISTS sys_menu (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    parent_id BIGINT DEFAULT 0,
    name VARCHAR(50) NOT NULL,
    path VARCHAR(200),
    component VARCHAR(200),
    icon VARCHAR(50),
    sort_order INT DEFAULT 0,
    type INT DEFAULT 1,
    permission VARCHAR(100),
    visible INT DEFAULT 1,
    status INT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS sys_role_menu (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    role_id BIGINT NOT NULL,
    menu_id BIGINT NOT NULL
);

CREATE TABLE IF NOT EXISTS sys_dict (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    code VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(200),
    status INT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS sys_dict_item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    dict_id BIGINT NOT NULL,
    label VARCHAR(100) NOT NULL,
    "value" VARCHAR(100) NOT NULL,
    sort_order INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS op_patient (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    gender INT NOT NULL,
    birth_date DATE,
    id_card VARCHAR(20),
    phone VARCHAR(20),
    address VARCHAR(200),
    blood_type VARCHAR(10),
    allergy VARCHAR(500),
    medical_insurance_no VARCHAR(30),
    patient_no VARCHAR(30) NOT NULL UNIQUE,
    status INT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS op_schedule (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    doctor_id BIGINT NOT NULL,
    dept_id BIGINT NOT NULL,
    schedule_date DATE NOT NULL,
    time_slot VARCHAR(20) NOT NULL,
    total_num INT DEFAULT 0,
    used_num INT DEFAULT 0,
    fee_type VARCHAR(20) DEFAULT 'NORMAL',
    status INT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS op_register (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    register_no VARCHAR(30) NOT NULL UNIQUE,
    patient_id BIGINT NOT NULL,
    dept_id BIGINT NOT NULL,
    doctor_id BIGINT,
    schedule_id BIGINT,
    register_type VARCHAR(20) DEFAULT 'NORMAL',
    fee DECIMAL(10,2) DEFAULT 0,
    status INT DEFAULT 1,
    register_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    cancel_time TIMESTAMP,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS op_medical_record (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    register_id BIGINT NOT NULL,
    patient_id BIGINT NOT NULL,
    doctor_id BIGINT NOT NULL,
    chief_complaint TEXT,
    present_illness TEXT,
    past_history TEXT,
    physical_exam TEXT,
    treatment_plan TEXT,
    remark TEXT,
    status INT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS op_diagnosis (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    register_id BIGINT NOT NULL,
    patient_id BIGINT NOT NULL,
    doctor_id BIGINT NOT NULL,
    icd_code VARCHAR(30),
    diagnosis_name VARCHAR(200) NOT NULL,
    diagnosis_type INT DEFAULT 1,
    is_primary INT DEFAULT 0,
    remark VARCHAR(500),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS op_prescription (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    prescription_no VARCHAR(30) NOT NULL UNIQUE,
    register_id BIGINT NOT NULL,
    patient_id BIGINT NOT NULL,
    doctor_id BIGINT NOT NULL,
    type VARCHAR(20) DEFAULT 'DRUG',
    total_amount DECIMAL(10,2) DEFAULT 0,
    status INT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS op_prescription_item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    prescription_id BIGINT NOT NULL,
    drug_id BIGINT,
    drug_name VARCHAR(100) NOT NULL,
    drug_spec VARCHAR(100),
    unit VARCHAR(20),
    quantity INT DEFAULT 1,
    unit_price DECIMAL(10,2) DEFAULT 0,
    amount DECIMAL(10,2) DEFAULT 0,
    dosage VARCHAR(50),
    frequency VARCHAR(50),
    usage_method VARCHAR(50),
    days INT DEFAULT 1,
    remark VARCHAR(200),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS op_fee (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fee_no VARCHAR(30) NOT NULL UNIQUE,
    register_id BIGINT NOT NULL,
    patient_id BIGINT NOT NULL,
    prescription_id BIGINT,
    total_amount DECIMAL(10,2) NOT NULL,
    pay_type VARCHAR(20) DEFAULT 'CASH',
    pay_status INT DEFAULT 0,
    operator_id BIGINT,
    pay_time TIMESTAMP,
    refund_time TIMESTAMP,
    remark VARCHAR(200),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS drug_info (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    drug_code VARCHAR(30) NOT NULL UNIQUE,
    drug_name VARCHAR(100) NOT NULL,
    generic_name VARCHAR(100),
    spec VARCHAR(100),
    unit VARCHAR(20),
    manufacturer VARCHAR(100),
    category VARCHAR(50),
    unit_price DECIMAL(10,2) DEFAULT 0,
    status INT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS drug_stock (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    drug_id BIGINT NOT NULL,
    batch_no VARCHAR(50),
    quantity INT DEFAULT 0,
    safe_stock INT DEFAULT 10,
    expire_date DATE,
    status INT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS drug_dispensing (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    dispensing_no VARCHAR(30) NOT NULL UNIQUE,
    prescription_id BIGINT NOT NULL,
    patient_id BIGINT NOT NULL,
    pharmacist_id BIGINT,
    status INT DEFAULT 1,
    dispensing_time TIMESTAMP,
    remark VARCHAR(200),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS drug_stock_log (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    drug_id BIGINT NOT NULL,
    type VARCHAR(20) NOT NULL,
    quantity INT NOT NULL,
    before_qty INT,
    after_qty INT,
    operator_id BIGINT,
    remark VARCHAR(200),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
