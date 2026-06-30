USE his_db;

-- 职称等级
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

-- 考核模板
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

-- 员工档案
INSERT INTO hr_employee (emp_no, user_id, name, gender, birth_date, phone, dept_id, position, title, education, major, graduate_school, work_date, status) VALUES
('EMP001', 2, 'Zhang Wei', 1, '1975-03-15', '13800001001', 11, 'Chief Physician', 'Chief Physician', 'PhD', 'Respiratory', 'Peking University', '2000-07-01', 1),
('EMP002', 3, 'Liu Na', 2, '1980-06-20', '13800001002', 11, 'Associate Chief Physician', 'Associate Chief Physician', 'Master', 'Respiratory', 'Capital Medical University', '2005-07-01', 1),
('EMP003', 4, 'Wang Fang', 2, '1985-09-10', '13800001003', 11, 'Attending Physician', 'Attending Physician', 'Master', 'Respiratory', 'Peking University', '2010-07-01', 1),
('EMP004', 5, 'Chen Ming', 1, '1972-11-25', '13800001011', 12, 'Chief Physician', 'Chief Physician', 'PhD', 'Cardiology', 'Peking Union Medical College', '1998-07-01', 1),
('EMP005', 6, 'Yang Hua', 1, '1978-04-18', '13800001012', 12, 'Associate Chief Physician', 'Associate Chief Physician', 'PhD', 'Cardiology', 'Capital Medical University', '2003-07-01', 1),
('EMP006', 14, 'Xu Jian', 1, '1970-08-30', '13800002001', 21, 'Chief Physician', 'Chief Physician', 'PhD', 'General Surgery', 'Peking University', '1995-07-01', 1),
('EMP007', 17, 'Li Qiang', 1, '1973-05-12', '13800002011', 22, 'Chief Physician', 'Chief Physician', 'PhD', 'Orthopedics', 'Beijing Jishuitan Hospital', '1999-07-01', 1),
('EMP008', 22, 'Chen Ying', 2, '1976-12-08', '13800003001', 31, 'Chief Physician', 'Chief Physician', 'PhD', 'Gynecology', 'Peking University', '2001-07-01', 1),
('EMP009', 25, 'Zhang Min', 2, '1979-02-28', '13800004001', 42, 'Chief Physician', 'Chief Physician', 'PhD', 'Pediatrics', 'Capital Medical University', '2004-07-01', 1),
('EMP010', 27, 'Huang Lei', 1, '1974-10-15', '13800005001', 51, 'Chief Physician', 'Chief Physician', 'PhD', 'Ophthalmology', 'Peking University', '2000-07-01', 1);

-- 科研课题
INSERT INTO research_project (project_no, name, type, leader_id, dept_id, start_date, end_date, budget, source, status, description) VALUES
('PRJ2026001', 'Novel Early Diagnosis Technology for Respiratory Diseases', 'NATIONAL', 2, 11, '2026-01-01', '2028-12-31', 500000.00, 'NSFC', 3, 'Research on novel early diagnosis technology for respiratory diseases'),
('PRJ2026002', 'Precision Treatment for Cardiovascular Diseases', 'PROVINCIAL', 5, 12, '2026-03-01', '2028-02-28', 300000.00, 'Provincial Science Fund', 3, 'Research on precision treatment for cardiovascular diseases'),
('PRJ2026003', 'Innovation in Minimally Invasive Surgery', 'MUNICIPAL', 14, 21, '2026-01-01', '2027-12-31', 200000.00, 'Municipal Science Bureau', 2, 'Innovation in minimally invasive surgery techniques'),
('PRJ2026004', 'Pathogenesis of Childhood Asthma', 'HOSPITAL', 25, 42, '2026-06-01', '2027-05-31', 100000.00, 'Hospital Fund', 1, 'Exploring the pathogenesis of childhood asthma');

-- 论文
INSERT INTO research_paper (title, authors, first_author_id, journal, publish_date, level, impact_factor, project_id, status) VALUES
('A Novel Approach for Early Diagnosis of COPD', 'Zhang Wei, Liu Na, Wang Fang', 2, 'Lancet Respiratory Medicine', '2026-03-15', 'SCI', 35.800, 1, 3),
('Progress in Precision Medicine for Cardiovascular Diseases', 'Yang Hua, Chen Ming', 6, 'Chinese Journal of Cardiology', '2026-02-01', 'CORE', NULL, 2, 3),
('Clinical Study on Laparoscopic Cholecystectomy', 'Xu Jian, He Lan', 14, 'Chinese Journal of Surgery', '2026-04-15', 'CORE', NULL, 3, 3);

-- 不良事件
INSERT INTO adverse_event (event_no, type, level, title, description, event_time, event_place, patient_id, reporter_id, reporter_dept, status) VALUES
('AE2026001', 'MEDICAL', 3, 'Prescription Dosage Error', 'Doctor prescribed Amoxicillin 1g instead of 0.5g, pharmacy caught and corrected', '2026-06-20 10:30:00', 'Outpatient Pharmacy', 1, 38, 76, 3),
('AE2026002', 'FALL', 2, 'Inpatient Fall', 'Inpatient fell while getting up at night, minor abrasion', '2026-06-22 02:15:00', 'Medical Ward', 2, 3, 11, 2),
('AE2026003', 'NURSING', 4, 'IV Infiltration Risk', 'Nurse found IV infiltration risk during rounds, timely intervention prevented adverse outcome', '2026-06-25 14:00:00', 'Infusion Room', 3, 4, 11, 3);

-- 新增菜单
INSERT INTO sys_menu (id, parent_id, name, path, component, icon, sort_order, type, permission) VALUES
(60, 0, 'Finance System', '/finance', NULL, 'Money', 6, 1, NULL),
(61, 60, 'Income Management', '/finance/income', 'finance/income', 'Wallet', 1, 2, 'finance:income'),
(62, 60, 'Expense Management', '/finance/expense', 'finance/expense', 'CreditCard', 2, 2, 'finance:expense'),
(63, 60, 'Financial Report', '/finance/report', 'finance/report', 'DataAnalysis', 3, 2, 'finance:report'),
(70, 0, 'HR System', '/hr', NULL, 'UserFilled', 7, 1, NULL),
(71, 70, 'Employee Files', '/hr/employee', 'hr/employee', 'User', 1, 2, 'hr:employee'),
(72, 70, 'Attendance', '/hr/attendance', 'hr/attendance', 'Clock', 2, 2, 'hr:attendance'),
(73, 70, 'Salary', '/hr/salary', 'hr/salary', 'Coin', 3, 2, 'hr:salary'),
(80, 0, 'Performance', '/performance', NULL, 'Trophy', 8, 1, NULL),
(81, 80, 'Templates', '/performance/template', 'performance/template', 'Document', 1, 2, 'perf:template'),
(82, 80, 'Evaluation', '/performance/evaluate', 'performance/evaluate', 'EditPen', 2, 2, 'perf:evaluate'),
(83, 80, 'Results', '/performance/result', 'performance/result', 'DataLine', 3, 2, 'perf:result'),
(90, 0, 'Title Evaluation', '/title', NULL, 'Medal', 9, 1, NULL),
(91, 90, 'Application', '/title/apply', 'title/apply', 'Document', 1, 2, 'title:apply'),
(92, 90, 'Review', '/title/review', 'title/review', 'Stamp', 2, 2, 'title:review'),
(93, 90, 'Management', '/title/manage', 'title/manage', 'Setting', 3, 2, 'title:manage'),
(100, 0, 'Research', '/research', NULL, 'DataBoard', 10, 1, NULL),
(101, 100, 'Projects', '/research/project', 'research/project', 'Folder', 1, 2, 'research:project'),
(102, 100, 'Papers', '/research/paper', 'research/paper', 'Notebook', 2, 2, 'research:paper'),
(103, 100, 'Achievements', '/research/achievement', 'research/achievement', 'Present', 3, 2, 'research:achievement'),
(110, 0, 'Adverse Events', '/adverse', NULL, 'Warning', 11, 1, NULL),
(111, 110, 'Report Event', '/adverse/report', 'adverse/report', 'EditPen', 1, 2, 'adverse:report'),
(112, 110, 'Handle Events', '/adverse/handle', 'adverse/handle', 'Finished', 2, 2, 'adverse:handle'),
(113, 110, 'Statistics', '/adverse/statistics', 'adverse/statistics', 'DataAnalysis', 3, 2, 'adverse:statistics');

-- 管理员权限
INSERT INTO sys_role_menu (role_id, menu_id) VALUES
(1,60),(1,61),(1,62),(1,63),
(1,70),(1,71),(1,72),(1,73),
(1,80),(1,81),(1,82),(1,83),
(1,90),(1,91),(1,92),(1,93),
(1,100),(1,101),(1,102),(1,103),
(1,110),(1,111),(1,112),(1,113);

SELECT 'New module data inserted!' AS result;
