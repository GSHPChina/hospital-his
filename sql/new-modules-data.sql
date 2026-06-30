USE his_db;

-- ============================================
-- 新增模块初始数据
-- ============================================

-- 职称等级数据
INSERT INTO title_level (name, level, description) VALUES
('住院医师', 1, '初级职称'),
('主治医师', 2, '中级职称'),
('副主任医师', 3, '副高级职称'),
('主任医师', 4, '正高级职称'),
('护士', 1, '初级职称'),
('主管护师', 2, '中级职称'),
('副主任护师', 3, '副高级职称'),
('主任护师', 4, '正高级职称'),
('药士', 1, '初级职称'),
('药师', 2, '中级职称'),
('主管药师', 3, '副高级职称'),
('副主任药师', 4, '正高级职称');

-- 考核模板数据
INSERT INTO perf_template (name, type, year, total_score) VALUES
('医生年度考核', 'DOCTOR', 2026, 100),
('护士年度考核', 'NURSE', 2026, 100),
('医技人员考核', 'TECH', 2026, 100),
('行政人员考核', 'ADMIN', 2026, 100);

-- 医生考核指标
INSERT INTO perf_indicator (template_id, name, category, score, description, sort_order) VALUES
(1, '医疗质量', '业务能力', 30, '病历质量、诊断准确率、治愈率等', 1),
(1, '服务态度', '患者满意度', 15, '患者投诉率、表扬次数', 2),
(1, '工作量', '业务能力', 20, '门诊量、手术量、值班次数', 3),
(1, '学术科研', '科研能力', 15, '论文发表、课题参与、继续教育', 4),
(1, '团队协作', '综合素质', 10, '科室协作、带教指导', 5),
(1, '医德医风', '职业道德', 10, '廉洁行医、规章制度执行', 6);

-- 护士考核指标
INSERT INTO perf_indicator (template_id, name, category, score, description, sort_order) VALUES
(2, '护理质量', '业务能力', 30, '护理文书、操作规范、患者安全', 1),
(2, '服务态度', '患者满意度', 20, '患者满意度、投诉率', 2),
(2, '工作量', '业务能力', 20, '护理时数、值班次数', 3),
(2, '继续教育', '学习提升', 15, '培训参与、技能考核', 4),
(2, '团队协作', '综合素质', 15, '科室配合、交接班规范', 5);

-- 员工档案数据(关联现有医生)
INSERT INTO hr_employee (emp_no, user_id, name, gender, birth_date, phone, dept_id, position, title, education, major, graduate_school, work_date, status) VALUES
('EMP001', 2, '张伟', 1, '1975-03-15', '13800001001', 11, '主任医师', '主任医师', '博士', '呼吸内科', '北京大学医学部', '2000-07-01', 1),
('EMP002', 3, '刘娜', 2, '1980-06-20', '13800001002', 11, '副主任医师', '副主任医师', '硕士', '呼吸内科', '首都医科大学', '2005-07-01', 1),
('EMP003', 4, '王芳', 2, '1985-09-10', '13800001003', 11, '主治医师', '主治医师', '硕士', '呼吸内科', '北京大学医学部', '2010-07-01', 1),
('EMP004', 5, '陈明', 1, '1972-11-25', '13800001011', 12, '主任医师', '主任医师', '博士', '心血管内科', '北京协和医学院', '1998-07-01', 1),
('EMP005', 6, '杨华', 1, '1978-04-18', '13800001012', 12, '副主任医师', '副主任医师', '博士', '心血管内科', '首都医科大学', '2003-07-01', 1),
('EMP006', 14, '徐建', 1, '1970-08-30', '13800002001', 21, '主任医师', '主任医师', '博士', '普通外科', '北京大学医学部', '1995-07-01', 1),
('EMP007', 17, '李强', 1, '1973-05-12', '13800002011', 22, '主任医师', '主任医师', '博士', '骨科', '北京积水潭医院', '1999-07-01', 1),
('EMP008', 22, '陈英', 2, '1976-12-08', '13800003001', 31, '主任医师', '主任医师', '博士', '妇科', '北京大学医学部', '2001-07-01', 1),
('EMP009', 25, '张敏', 2, '1979-02-28', '13800004001', 42, '主任医师', '主任医师', '博士', '儿内科', '首都医科大学', '2004-07-01', 1),
('EMP010', 27, '黄磊', 1, '1974-10-15', '13800005001', 51, '主任医师', '主任医师', '博士', '眼科', '北京大学医学部', '2000-07-01', 1);

-- 科研课题数据
INSERT INTO research_project (project_no, name, type, leader_id, dept_id, start_date, end_date, budget, source, status, description) VALUES
('PRJ2026001', '新型呼吸系统疾病早期诊断技术研究', 'NATIONAL', 2, 11, '2026-01-01', '2028-12-31', 500000.00, '国家自然科学基金', 3, '本课题旨在开发新型呼吸系统疾病早期诊断技术'),
('PRJ2026002', '心血管疾病精准治疗方案研究', 'PROVINCIAL', 5, 12, '2026-03-01', '2028-02-28', 300000.00, '省自然科学基金', 3, '研究心血管疾病精准治疗方案'),
('PRJ2026003', '微创外科手术技术创新', 'MUNICIPAL', 14, 21, '2026-01-01', '2027-12-31', 200000.00, '市科技局', 2, '微创外科手术技术创新研究'),
('PRJ2026004', '儿童哮喘发病机制研究', 'HOSPITAL', 25, 42, '2026-06-01', '2027-05-31', 100000.00, '院内基金', 1, '探索儿童哮喘发病机制');

-- 论文数据
INSERT INTO research_paper (title, authors, first_author_id, journal, publish_date, level, impact_factor, project_id, status) VALUES
('A Novel Approach for Early Diagnosis of COPD', 'Zhang Wei, Liu Na, Wang Fang', 2, 'Lancet Respiratory Medicine', '2026-03-15', 'SCI', 35.800, 1, 3),
('精准医疗在心血管疾病中的应用进展', '杨华, 陈明', 6, '中华心血管病杂志', '2026-02-01', 'CORE', NULL, 2, 3),
('腹腔镜手术治疗胆囊结石的临床研究', '徐建, 何兰', 14, '中华外科杂志', '2026-04-15', 'CORE', NULL, 3, 3);

-- 不良事件数据
INSERT INTO adverse_event (event_no, type, level, title, description, event_time, event_place, patient_id, reporter_id, reporter_dept, status) VALUES
('AE2026001', 'MEDICAL', 3, '处方剂量错误', '医生开具阿莫西林剂量为1g，应为0.5g，药房发现后及时纠正', '2026-06-20 10:30:00', '门诊药房', 1, 38, 76, 3),
('AE2026002', 'FALL', 2, '住院患者跌倒', '住院患者夜间起床如厕时跌倒，造成轻微擦伤', '2026-06-22 02:15:00', '内科病房', 2, 3, 11, 2),
('AE2026003', 'NURSING', 4, '输液外渗隐患', '护士巡视发现输液外渗风险，及时处理避免不良后果', '2026-06-25 14:00:00', '输液室', 3, 4, 11, 3);

-- 新增菜单
INSERT INTO sys_menu (id, parent_id, name, path, component, icon, sort_order, type, permission) VALUES
-- 财务系统
(60, 0, '财务系统', '/finance', NULL, 'Money', 6, 1, NULL),
(61, 60, '收入管理', '/finance/income', 'finance/income', 'Wallet', 1, 2, 'finance:income'),
(62, 60, '支出管理', '/finance/expense', 'finance/expense', 'CreditCard', 2, 2, 'finance:expense'),
(63, 60, '财务报表', '/finance/report', 'finance/report', 'DataAnalysis', 3, 2, 'finance:report'),
-- 人事系统
(70, 0, '人事系统', '/hr', NULL, 'UserFilled', 7, 1, NULL),
(71, 70, '员工档案', '/hr/employee', 'hr/employee', 'User', 1, 2, 'hr:employee'),
(72, 70, '考勤管理', '/hr/attendance', 'hr/attendance', 'Clock', 2, 2, 'hr:attendance'),
(73, 70, '薪资管理', '/hr/salary', 'hr/salary', 'Coin', 3, 2, 'hr:salary'),
-- 绩效考核
(80, 0, '绩效考核', '/performance', NULL, 'Trophy', 8, 1, NULL),
(81, 80, '考核模板', '/performance/template', 'performance/template', 'Document', 1, 2, 'perf:template'),
(82, 80, '绩效考核', '/performance/evaluate', 'performance/evaluate', 'EditPen', 2, 2, 'perf:evaluate'),
(83, 80, '考核结果', '/performance/result', 'performance/result', 'DataLine', 3, 2, 'perf:result'),
-- 职称评价
(90, 0, '职称评价', '/title', NULL, 'Medal', 9, 1, NULL),
(91, 90, '职称申请', '/title/apply', 'title/apply', 'Document', 1, 2, 'title:apply'),
(92, 90, '职称评审', '/title/review', 'title/review', 'Stamp', 2, 2, 'title:review'),
(93, 90, '职称管理', '/title/manage', 'title/manage', 'Setting', 3, 2, 'title:manage'),
-- 科研管理
(100, 0, '科研管理', '/research', NULL, 'DataBoard', 10, 1, NULL),
(101, 100, '课题管理', '/research/project', 'research/project', 'Folder', 1, 2, 'research:project'),
(102, 100, '论文管理', '/research/paper', 'research/paper', 'Notebook', 2, 2, 'research:paper'),
(103, 100, '成果管理', '/research/achievement', 'research/achievement', 'Present', 3, 2, 'research:achievement'),
-- 不良事件
(110, 0, '不良事件', '/adverse', NULL, 'Warning', 11, 1, NULL),
(111, 110, '事件上报', '/adverse/report', 'adverse/report', 'EditPen', 1, 2, 'adverse:report'),
(112, 110, '事件处理', '/adverse/handle', 'adverse/handle', 'Finished', 2, 2, 'adverse:handle'),
(113, 110, '事件统计', '/adverse/statistics', 'adverse/statistics', 'DataAnalysis', 3, 2, 'adverse:statistics');

-- 管理员角色添加新菜单权限
INSERT INTO sys_role_menu (role_id, menu_id) VALUES
(1,60),(1,61),(1,62),(1,63),
(1,70),(1,71),(1,72),(1,73),
(1,80),(1,81),(1,82),(1,83),
(1,90),(1,91),(1,92),(1,93),
(1,100),(1,101),(1,102),(1,103),
(1,110),(1,111),(1,112),(1,113);

SELECT '新模块初始数据导入完成！' AS result;
