USE his_db;

-- 财务系统
CREATE TABLE IF NOT EXISTS fin_income (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    income_no VARCHAR(30) NOT NULL UNIQUE,
    type VARCHAR(20) NOT NULL,
    amount DECIMAL(12,2) NOT NULL,
    dept_id BIGINT,
    source_id BIGINT,
    source_type VARCHAR(20),
    income_date DATE NOT NULL,
    remark VARCHAR(200),
    operator_id BIGINT,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_type_date (type, income_date),
    INDEX idx_dept_date (dept_id, income_date)
);

CREATE TABLE IF NOT EXISTS fin_expense (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    expense_no VARCHAR(30) NOT NULL UNIQUE,
    type VARCHAR(20) NOT NULL,
    amount DECIMAL(12,2) NOT NULL,
    dept_id BIGINT,
    expense_date DATE NOT NULL,
    description VARCHAR(500),
    status TINYINT DEFAULT 1,
    approver_id BIGINT,
    approve_time DATETIME,
    operator_id BIGINT,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_type_date (type, expense_date),
    INDEX idx_status (status)
);

CREATE TABLE IF NOT EXISTS fin_monthly_report (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    ym VARCHAR(7) NOT NULL,
    total_income DECIMAL(12,2) DEFAULT 0,
    total_expense DECIMAL(12,2) DEFAULT 0,
    profit DECIMAL(12,2) DEFAULT 0,
    reg_income DECIMAL(12,2) DEFAULT 0,
    drug_income DECIMAL(12,2) DEFAULT 0,
    exam_income DECIMAL(12,2) DEFAULT 0,
    treatment_income DECIMAL(12,2) DEFAULT 0,
    salary_expense DECIMAL(12,2) DEFAULT 0,
    drug_purchase DECIMAL(12,2) DEFAULT 0,
    equipment_expense DECIMAL(12,2) DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE INDEX uk_ym (ym)
);

-- 人事系统
CREATE TABLE IF NOT EXISTS hr_employee (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    emp_no VARCHAR(30) NOT NULL UNIQUE,
    user_id BIGINT,
    name VARCHAR(50) NOT NULL,
    gender TINYINT NOT NULL,
    birth_date DATE,
    id_card VARCHAR(20),
    phone VARCHAR(20),
    email VARCHAR(100),
    dept_id BIGINT,
    position VARCHAR(50),
    title VARCHAR(50),
    education VARCHAR(20),
    major VARCHAR(100),
    graduate_school VARCHAR(100),
    work_date DATE,
    contract_end DATE,
    status TINYINT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_dept (dept_id),
    INDEX idx_status (status)
);

CREATE TABLE IF NOT EXISTS hr_attendance (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    emp_id BIGINT NOT NULL,
    attendance_date DATE NOT NULL,
    check_in_time DATETIME,
    check_out_time DATETIME,
    status TINYINT DEFAULT 1,
    remark VARCHAR(200),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_emp_date (emp_id, attendance_date),
    INDEX idx_date (attendance_date)
);

CREATE TABLE IF NOT EXISTS hr_salary (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    emp_id BIGINT NOT NULL,
    ym VARCHAR(7) NOT NULL,
    base_salary DECIMAL(10,2) DEFAULT 0,
    position_salary DECIMAL(10,2) DEFAULT 0,
    bonus DECIMAL(10,2) DEFAULT 0,
    allowance DECIMAL(10,2) DEFAULT 0,
    overtime_pay DECIMAL(10,2) DEFAULT 0,
    deduction DECIMAL(10,2) DEFAULT 0,
    social_insurance DECIMAL(10,2) DEFAULT 0,
    tax DECIMAL(10,2) DEFAULT 0,
    net_salary DECIMAL(10,2) DEFAULT 0,
    status TINYINT DEFAULT 1,
    pay_time DATETIME,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_emp_month (emp_id, ym)
);

-- 绩效考核
CREATE TABLE IF NOT EXISTS perf_template (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(20) NOT NULL,
    year INT NOT NULL,
    total_score INT DEFAULT 100,
    status TINYINT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS perf_indicator (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    template_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    category VARCHAR(50),
    score INT NOT NULL,
    description VARCHAR(500),
    sort_order INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_template (template_id)
);

CREATE TABLE IF NOT EXISTS perf_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    template_id BIGINT NOT NULL,
    emp_id BIGINT NOT NULL,
    year INT NOT NULL,
    quarter INT,
    total_score INT DEFAULT 0,
    level VARCHAR(10),
    evaluator_id BIGINT,
    evaluate_time DATETIME,
    status TINYINT DEFAULT 1,
    remark VARCHAR(500),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_emp_year (emp_id, year),
    INDEX idx_template (template_id)
);

CREATE TABLE IF NOT EXISTS perf_score_detail (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    record_id BIGINT NOT NULL,
    indicator_id BIGINT NOT NULL,
    score INT DEFAULT 0,
    remark VARCHAR(200),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_record (record_id)
);

-- 职称评价
CREATE TABLE IF NOT EXISTS title_level (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    level INT NOT NULL,
    description VARCHAR(200),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS title_application (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    app_no VARCHAR(30) NOT NULL UNIQUE,
    emp_id BIGINT NOT NULL,
    current_title VARCHAR(50),
    apply_title VARCHAR(50) NOT NULL,
    apply_date DATE NOT NULL,
    work_years INT,
    education VARCHAR(20),
    major VARCHAR(100),
    work_summary TEXT,
    achievements TEXT,
    papers TEXT,
    status TINYINT DEFAULT 1,
    dept_opinion VARCHAR(500),
    hr_opinion VARCHAR(500),
    committee_opinion VARCHAR(500),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_emp (emp_id),
    INDEX idx_status (status)
);

CREATE TABLE IF NOT EXISTS title_review (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    application_id BIGINT NOT NULL,
    reviewer_id BIGINT NOT NULL,
    review_type VARCHAR(20) NOT NULL,
    score INT,
    opinion VARCHAR(500),
    result TINYINT,
    review_time DATETIME,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_application (application_id)
);

-- 科研管理
CREATE TABLE IF NOT EXISTS research_project (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    project_no VARCHAR(30) NOT NULL UNIQUE,
    name VARCHAR(200) NOT NULL,
    type VARCHAR(20),
    leader_id BIGINT NOT NULL,
    dept_id BIGINT,
    start_date DATE,
    end_date DATE,
    budget DECIMAL(12,2),
    source VARCHAR(100),
    status TINYINT DEFAULT 1,
    description TEXT,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_leader (leader_id),
    INDEX idx_status (status)
);

CREATE TABLE IF NOT EXISTS research_member (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    project_id BIGINT NOT NULL,
    emp_id BIGINT NOT NULL,
    role VARCHAR(20),
    sort_order INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_project (project_id)
);

CREATE TABLE IF NOT EXISTS research_paper (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(500) NOT NULL,
    authors VARCHAR(500),
    first_author_id BIGINT,
    journal VARCHAR(200),
    publish_date DATE,
    level VARCHAR(20),
    impact_factor DECIMAL(5,2),
    project_id BIGINT,
    status TINYINT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_author (first_author_id),
    INDEX idx_project (project_id)
);

CREATE TABLE IF NOT EXISTS research_achievement (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(200) NOT NULL,
    type VARCHAR(20),
    project_id BIGINT,
    holder_id BIGINT,
    obtain_date DATE,
    certificate_no VARCHAR(100),
    description TEXT,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_holder (holder_id)
);

-- 不良事件
CREATE TABLE IF NOT EXISTS adverse_event (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    event_no VARCHAR(30) NOT NULL UNIQUE,
    type VARCHAR(30) NOT NULL,
    level TINYINT NOT NULL,
    title VARCHAR(200) NOT NULL,
    description TEXT NOT NULL,
    event_time DATETIME NOT NULL,
    event_place VARCHAR(100),
    patient_id BIGINT,
    reporter_id BIGINT NOT NULL,
    reporter_dept BIGINT,
    report_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    status TINYINT DEFAULT 1,
    handler_id BIGINT,
    handle_time DATETIME,
    handle_result TEXT,
    cause_analysis TEXT,
    improvement TEXT,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_type (type),
    INDEX idx_level (level),
    INDEX idx_status (status),
    INDEX idx_report_time (report_time)
);

CREATE TABLE IF NOT EXISTS adverse_event_log (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    event_id BIGINT NOT NULL,
    operator_id BIGINT NOT NULL,
    action VARCHAR(50) NOT NULL,
    content TEXT,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_event (event_id)
);

SELECT 'All tables created successfully!' AS result;
