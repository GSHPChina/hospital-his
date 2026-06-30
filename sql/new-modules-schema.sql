-- ============================================
-- 新增模块数据库表
-- 财务系统、人事系统、绩效考核、职称评价、科研管理、不良事件
-- ============================================

USE his_db;

-- ============================================
-- 一、财务系统
-- ============================================

-- 收入记录表
CREATE TABLE fin_income (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    income_no       VARCHAR(30) NOT NULL UNIQUE COMMENT '收入单号',
    type            VARCHAR(20) NOT NULL COMMENT '类型: REGISTRATION-挂号费 DRUG-药品费 EXAM-检查费 TREATMENT-治疗费 BED-床位费',
    amount          DECIMAL(12,2) NOT NULL COMMENT '金额',
    dept_id         BIGINT COMMENT '科室ID',
    source_id       BIGINT COMMENT '来源ID(处方ID/挂号ID等)',
    source_type     VARCHAR(20) COMMENT '来源类型',
    income_date     DATE NOT NULL COMMENT '收入日期',
    remark          VARCHAR(200) COMMENT '备注',
    operator_id     BIGINT COMMENT '操作人ID',
    create_time     DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_type_date (type, income_date),
    INDEX idx_dept_date (dept_id, income_date)
) COMMENT '收入记录表';

-- 支出记录表
CREATE TABLE fin_expense (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    expense_no      VARCHAR(30) NOT NULL UNIQUE COMMENT '支出单号',
    type            VARCHAR(20) NOT NULL COMMENT '类型: SALARY-工资 DRUG_PURCHASE-药品采购 EQUIPMENT-设备 OTHER-其他',
    amount          DECIMAL(12,2) NOT NULL COMMENT '金额',
    dept_id         BIGINT COMMENT '科室ID',
    expense_date    DATE NOT NULL COMMENT '支出日期',
    description     VARCHAR(500) COMMENT '说明',
    status          TINYINT DEFAULT 1 COMMENT '状态 1-待审批 2-已审批 3-已支付 4-已驳回',
    approver_id     BIGINT COMMENT '审批人ID',
    approve_time    DATETIME COMMENT '审批时间',
    operator_id     BIGINT COMMENT '操作人ID',
    create_time     DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_type_date (type, expense_date),
    INDEX idx_status (status)
) COMMENT '支出记录表';

-- 财务报表(月度汇总)
CREATE TABLE fin_monthly_report (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    ``year_month``    VARCHAR(7) NOT NULL COMMENT '年月 YYYY-MM',
    total_income    DECIMAL(12,2) DEFAULT 0 COMMENT '总收入',
    total_expense   DECIMAL(12,2) DEFAULT 0 COMMENT '总支出',
    profit          DECIMAL(12,2) DEFAULT 0 COMMENT '利润',
    reg_income      DECIMAL(12,2) DEFAULT 0 COMMENT '挂号收入',
    drug_income     DECIMAL(12,2) DEFAULT 0 COMMENT '药品收入',
    exam_income     DECIMAL(12,2) DEFAULT 0 COMMENT '检查收入',
    treatment_income DECIMAL(12,2) DEFAULT 0 COMMENT '治疗收入',
    salary_expense  DECIMAL(12,2) DEFAULT 0 COMMENT '工资支出',
    drug_purchase   DECIMAL(12,2) DEFAULT 0 COMMENT '药品采购',
    equipment_expense DECIMAL(12,2) DEFAULT 0 COMMENT '设备支出',
    create_time     DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE INDEX uk_`year_month` (`year_month`)
) COMMENT '月度财务报表';

-- ============================================
-- 二、人事系统
-- ============================================

-- 员工档案表
CREATE TABLE hr_employee (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    emp_no          VARCHAR(30) NOT NULL UNIQUE COMMENT '工号',
    user_id         BIGINT COMMENT '关联系统用户ID',
    name            VARCHAR(50) NOT NULL COMMENT '姓名',
    gender          TINYINT NOT NULL COMMENT '性别 1-男 2-女',
    birth_date      DATE COMMENT '出生日期',
    id_card         VARCHAR(20) COMMENT '身份证号',
    phone           VARCHAR(20) COMMENT '手机号',
    email           VARCHAR(100) COMMENT '邮箱',
    dept_id         BIGINT COMMENT '科室ID',
    position        VARCHAR(50) COMMENT '岗位',
    title           VARCHAR(50) COMMENT '职称',
    education       VARCHAR(20) COMMENT '学历',
    major           VARCHAR(100) COMMENT '专业',
    graduate_school VARCHAR(100) COMMENT '毕业院校',
    work_date       DATE COMMENT '入职日期',
    contract_end    DATE COMMENT '合同到期日',
    status          TINYINT DEFAULT 1 COMMENT '状态 1-在职 2-离职 3-退休 4-休假',
    create_time     DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_dept (dept_id),
    INDEX idx_status (status)
) COMMENT '员工档案表';

-- 考勤记录表
CREATE TABLE hr_attendance (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    emp_id          BIGINT NOT NULL COMMENT '员工ID',
    attendance_date DATE NOT NULL COMMENT '考勤日期',
    check_in_time   DATETIME COMMENT '签到时间',
    check_out_time  DATETIME COMMENT '签退时间',
    status          TINYINT DEFAULT 1 COMMENT '状态 1-正常 2-迟到 3-早退 4-缺勤 5-请假 6-出差',
    remark          VARCHAR(200) COMMENT '备注',
    create_time     DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_emp_date (emp_id, attendance_date),
    INDEX idx_date (attendance_date)
) COMMENT '考勤记录表';

-- 薪资记录表
CREATE TABLE hr_salary (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    emp_id          BIGINT NOT NULL COMMENT '员工ID',
    `year_month`      VARCHAR(7) NOT NULL COMMENT '年月 YYYY-MM',
    base_salary     DECIMAL(10,2) DEFAULT 0 COMMENT '基本工资',
    position_salary DECIMAL(10,2) DEFAULT 0 COMMENT '岗位工资',
    bonus           DECIMAL(10,2) DEFAULT 0 COMMENT '奖金',
    allowance       DECIMAL(10,2) DEFAULT 0 COMMENT '津贴',
    overtime_pay    DECIMAL(10,2) DEFAULT 0 COMMENT '加班费',
    deduction       DECIMAL(10,2) DEFAULT 0 COMMENT '扣款',
    social_insurance DECIMAL(10,2) DEFAULT 0 COMMENT '社保',
    tax             DECIMAL(10,2) DEFAULT 0 COMMENT '个税',
    net_salary      DECIMAL(10,2) DEFAULT 0 COMMENT '实发工资',
    status          TINYINT DEFAULT 1 COMMENT '状态 1-待发放 2-已发放',
    pay_time        DATETIME COMMENT '发放时间',
    create_time     DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_emp_month (emp_id, `year_month`)
) COMMENT '薪资记录表';

-- ============================================
-- 三、绩效考核系统
-- ============================================

-- 考核模板表
CREATE TABLE perf_template (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    name            VARCHAR(100) NOT NULL COMMENT '模板名称',
    type            VARCHAR(20) NOT NULL COMMENT '类型: DOCTOR-医生 NURSE-护士 TECH-医技 ADMIN-行政',
    year            INT NOT NULL COMMENT '考核年度',
    total_score     INT DEFAULT 100 COMMENT '总分',
    status          TINYINT DEFAULT 1 COMMENT '状态 1-启用 0-停用',
    create_time     DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) COMMENT '考核模板表';

-- 考核指标表
CREATE TABLE perf_indicator (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    template_id     BIGINT NOT NULL COMMENT '模板ID',
    name            VARCHAR(100) NOT NULL COMMENT '指标名称',
    category        VARCHAR(50) COMMENT '指标类别',
    score           INT NOT NULL COMMENT '分值',
    description     VARCHAR(500) COMMENT '评分标准',
    sort_order      INT DEFAULT 0 COMMENT '排序',
    create_time     DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_template (template_id)
) COMMENT '考核指标表';

-- 考核记录表
CREATE TABLE perf_record (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    template_id     BIGINT NOT NULL COMMENT '考核模板ID',
    emp_id          BIGINT NOT NULL COMMENT '被考核人ID',
    year            INT NOT NULL COMMENT '考核年度',
    quarter         INT COMMENT '季度 1-4',
    total_score     INT DEFAULT 0 COMMENT '得分',
    level           VARCHAR(10) COMMENT '等级 A-优秀 B-良好 C-合格 D-不合格',
    evaluator_id    BIGINT COMMENT '考核人ID',
    evaluate_time   DATETIME COMMENT '考核时间',
    status          TINYINT DEFAULT 1 COMMENT '状态 1-草稿 2-已提交 3-已确认',
    remark          VARCHAR(500) COMMENT '备注',
    create_time     DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_emp_year (emp_id, year),
    INDEX idx_template (template_id)
) COMMENT '考核记录表';

-- 考核评分明细表
CREATE TABLE perf_score_detail (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    record_id       BIGINT NOT NULL COMMENT '考核记录ID',
    indicator_id    BIGINT NOT NULL COMMENT '指标ID',
    score           INT DEFAULT 0 COMMENT '得分',
    remark          VARCHAR(200) COMMENT '备注',
    create_time     DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_record (record_id)
) COMMENT '考核评分明细表';

-- ============================================
-- 四、职称评价考核系统
-- ============================================

-- 职称等级表
CREATE TABLE title_level (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    name            VARCHAR(50) NOT NULL COMMENT '职称名称',
    level           INT NOT NULL COMMENT '级别 1-初级 2-中级 3-副高 4-正高',
    description     VARCHAR(200) COMMENT '说明',
    create_time     DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) COMMENT '职称等级表';

-- 职称申请表
CREATE TABLE title_application (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    app_no          VARCHAR(30) NOT NULL UNIQUE COMMENT '申请单号',
    emp_id          BIGINT NOT NULL COMMENT '申请人ID',
    current_title   VARCHAR(50) COMMENT '当前职称',
    apply_title     VARCHAR(50) NOT NULL COMMENT '申请职称',
    apply_date      DATE NOT NULL COMMENT '申请日期',
    work_years      INT COMMENT '工作年限',
    education       VARCHAR(20) COMMENT '学历',
    major           VARCHAR(100) COMMENT '专业',
    work_summary    TEXT COMMENT '工作总结',
    achievements    TEXT COMMENT '主要业绩',
    papers          TEXT COMMENT '论文著作',
    status          TINYINT DEFAULT 1 COMMENT '状态 1-待提交 2-已提交 3-科室审核 4-人事审核 5-评审委员会 6-已通过 7-已驳回',
    dept_opinion    VARCHAR(500) COMMENT '科室意见',
    hr_opinion      VARCHAR(500) COMMENT '人事意见',
    committee_opinion VARCHAR(500) COMMENT '评审委员会意见',
    create_time     DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_emp (emp_id),
    INDEX idx_status (status)
) COMMENT '职称申请表';

-- 职称评审记录表
CREATE TABLE title_review (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    application_id  BIGINT NOT NULL COMMENT '申请ID',
    reviewer_id     BIGINT NOT NULL COMMENT '评审人ID',
    review_type     VARCHAR(20) NOT NULL COMMENT '评审类型: DEPT-科室 HR-人事 COMMITTEE-委员会',
    score           INT COMMENT '评分',
    opinion         VARCHAR(500) COMMENT '评审意见',
    result          TINYINT COMMENT '结果 1-通过 2-不通过',
    review_time     DATETIME COMMENT '评审时间',
    create_time     DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_application (application_id)
) COMMENT '职称评审记录表';

-- ============================================
-- 五、科研管理系统
-- ============================================

-- 科研课题表
CREATE TABLE research_project (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    project_no      VARCHAR(30) NOT NULL UNIQUE COMMENT '课题编号',
    name            VARCHAR(200) NOT NULL COMMENT '课题名称',
    type            VARCHAR(20) COMMENT '类型: NATIONAL-国家级 PROVINCIAL-省级 MUNICIPAL-市级 HOSPITAL-院级',
    leader_id       BIGINT NOT NULL COMMENT '负责人ID',
    dept_id         BIGINT COMMENT '科室ID',
    start_date      DATE COMMENT '开始日期',
    end_date        DATE COMMENT '结束日期',
    budget          DECIMAL(12,2) COMMENT '经费预算',
    source          VARCHAR(100) COMMENT '经费来源',
    status          TINYINT DEFAULT 1 COMMENT '状态 1-申报中 2-已立项 3-进行中 4-已结题 5-已终止',
    description     TEXT COMMENT '课题简介',
    create_time     DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_leader (leader_id),
    INDEX idx_status (status)
) COMMENT '科研课题表';

-- 课题成员表
CREATE TABLE research_member (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    project_id      BIGINT NOT NULL COMMENT '课题ID',
    emp_id          BIGINT NOT NULL COMMENT '成员ID',
    role            VARCHAR(20) COMMENT '角色: LEADER-负责人 PARTICIPANT-参与人',
    sort_order      INT DEFAULT 0 COMMENT '排序',
    create_time     DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_project (project_id)
) COMMENT '课题成员表';

-- 论文发表表
CREATE TABLE research_paper (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    title           VARCHAR(500) NOT NULL COMMENT '论文题目',
    authors         VARCHAR(500) COMMENT '作者',
    first_author_id BIGINT COMMENT '第一作者ID',
    journal         VARCHAR(200) COMMENT '期刊名称',
    publish_date    DATE COMMENT '发表日期',
    level           VARCHAR(20) COMMENT '级别: SCI EI CORE GENERAL',
    impact_factor   DECIMAL(5,2) COMMENT '影响因子',
    project_id      BIGINT COMMENT '关联课题ID',
    status          TINYINT DEFAULT 1 COMMENT '状态 1-投稿中 2-已录用 3-已发表',
    create_time     DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_author (first_author_id),
    INDEX idx_project (project_id)
) COMMENT '论文发表表';

-- 成果转化表
CREATE TABLE research_achievement (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    name            VARCHAR(200) NOT NULL COMMENT '成果名称',
    type            VARCHAR(20) COMMENT '类型: PATENT-专利 AWARD-获奖 TECHNOLOGY-技术转化',
    project_id      BIGINT COMMENT '关联课题ID',
    holder_id       BIGINT COMMENT '持有人ID',
    obtain_date     DATE COMMENT '获得日期',
    certificate_no  VARCHAR(100) COMMENT '证书编号',
    description     TEXT COMMENT '成果说明',
    create_time     DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_holder (holder_id)
) COMMENT '成果转化表';

-- ============================================
-- 六、不良事件管理上报系统
-- ============================================

-- 不良事件表
CREATE TABLE adverse_event (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    event_no        VARCHAR(30) NOT NULL UNIQUE COMMENT '事件编号',
    type            VARCHAR(30) NOT NULL COMMENT '类型: MEDICAL-医疗 NURSING-护理 DRUG-药品 INFECTION-感染 FALL-跌倒 PRESSURE-压疮 EQUIPMENT-设备 OTHER-其他',
    level           TINYINT NOT NULL COMMENT '等级 1-警告事件 2-不良后果事件 3-未造成后果事件 4-隐患事件',
    title           VARCHAR(200) NOT NULL COMMENT '事件标题',
    description     TEXT NOT NULL COMMENT '事件描述',
    event_time      DATETIME NOT NULL COMMENT '发生时间',
    event_place     VARCHAR(100) COMMENT '发生地点',
    patient_id      BIGINT COMMENT '涉及患者ID',
    reporter_id     BIGINT NOT NULL COMMENT '上报人ID',
    reporter_dept   BIGINT COMMENT '上报科室',
    report_time     DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '上报时间',
    status          TINYINT DEFAULT 1 COMMENT '状态 1-已上报 2-调查中 3-已处理 4-已关闭',
    handler_id      BIGINT COMMENT '处理人ID',
    handle_time     DATETIME COMMENT '处理时间',
    handle_result   TEXT COMMENT '处理结果',
    cause_analysis  TEXT COMMENT '原因分析',
    improvement     TEXT COMMENT '改进措施',
    create_time     DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_type (type),
    INDEX idx_level (level),
    INDEX idx_status (status),
    INDEX idx_report_time (report_time)
) COMMENT '不良事件表';

-- 不良事件处理记录表
CREATE TABLE adverse_event_log (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    event_id        BIGINT NOT NULL COMMENT '事件ID',
    operator_id     BIGINT NOT NULL COMMENT '操作人ID',
    action          VARCHAR(50) NOT NULL COMMENT '操作',
    content         TEXT COMMENT '内容',
    create_time     DATETIME DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_event (event_id)
) COMMENT '不良事件处理记录表';

SELECT '新模块数据库表创建完成！' AS result;
