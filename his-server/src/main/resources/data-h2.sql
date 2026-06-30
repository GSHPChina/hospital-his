-- H2 Initial Data for HIS System

-- Departments
INSERT INTO sys_dept (id, parent_id, name, code, type, sort_order) VALUES
(1, 0, '门诊部', 'OUTPATIENT', 1, 1),
(2, 0, '住院部', 'INPATIENT', 2, 2),
(3, 0, '医技科室', 'MEDICAL_TECH', 3, 3),
(4, 0, '行政后勤', 'ADMIN', 4, 4),
(5, 1, '内科门诊', 'NKMZ', 1, 1),
(6, 1, '外科门诊', 'WKMZ', 1, 2),
(7, 1, '儿科门诊', 'EKMZ', 1, 3),
(8, 1, '妇产科门诊', 'FCKMZ', 1, 4),
(9, 1, '眼科门诊', 'YKMZ', 1, 5),
(10, 1, '耳鼻喉科门诊', 'EBHKMZ', 1, 6),
(11, 1, '口腔科门诊', 'KQKMZ', 1, 7),
(12, 1, '皮肤科门诊', 'PFKMZ', 1, 8),
(13, 1, '中医科门诊', 'ZYKMZ', 1, 9),
(14, 1, '急诊科', 'JZK', 1, 10),
(15, 3, '检验科', 'JYK', 3, 1),
(16, 3, '放射科', 'FSK', 3, 2),
(17, 3, '超声科', 'CSK', 3, 3),
(18, 3, '药剂科', 'YJK', 3, 4);

-- Roles
INSERT INTO sys_role (id, name, code, description) VALUES
(1, '系统管理员', 'ADMIN', '系统管理员，拥有所有权限'),
(2, '门诊医生', 'DOCTOR', '门诊医生，负责看诊开方'),
(3, '收费员', 'CASHIER', '收费员，负责门诊收费'),
(4, '药剂师', 'PHARMACIST', '药剂师，负责发药'),
(5, '挂号员', 'REGISTRAR', '挂号员，负责挂号');

-- Users (password: 123456 - plain text for dev mode)
INSERT INTO sys_user (id, username, password, real_name, gender, phone, dept_id, post) VALUES
(1, 'admin', '123456', '系统管理员', 1, '13800000001', 4, '系统管理员'),
(2, 'doctor01', '123456', '张医生', 1, '13800000002', 5, '主治医师'),
(3, 'doctor02', '123456', '李医生', 2, '13800000003', 6, '副主任医师'),
(4, 'cashier01', '123456', '王收费员', 2, '13800000004', 4, '收费员'),
(5, 'pharmacist01', '123456', '赵药剂师', 1, '13800000005', 18, '药剂师'),
(6, 'registrar01', '123456', '刘挂号员', 2, '13800000006', 4, '挂号员');

-- User-Role mappings
INSERT INTO sys_user_role (user_id, role_id) VALUES
(1, 1), (2, 2), (3, 2), (4, 3), (5, 4), (6, 5);

-- Menus
INSERT INTO sys_menu (id, parent_id, name, path, component, icon, sort_order, type, permission) VALUES
(1, 0, '系统管理', '/system', NULL, 'Setting', 1, 1, NULL),
(2, 0, '门诊管理', '/outpatient', NULL, 'FirstAidKit', 2, 1, NULL),
(3, 0, '医生工作站', '/doctor', NULL, 'User', 3, 1, NULL),
(4, 0, '门诊收费', '/charge', NULL, 'Money', 4, 1, NULL),
(5, 0, '药房管理', '/pharmacy', NULL, 'Box', 5, 1, NULL),
(10, 1, '用户管理', '/system/user', 'system/user', 'User', 1, 2, 'system:user:list'),
(11, 1, '角色管理', '/system/role', 'system/role', 'UserFilled', 2, 2, 'system:role:list'),
(12, 1, '科室管理', '/system/dept', 'system/dept', 'OfficeBuilding', 3, 2, 'system:dept:list'),
(13, 1, '字典管理', '/system/dict', 'system/dict', 'Collection', 4, 2, 'system:dict:list'),
(20, 2, '门诊挂号', '/outpatient/register', 'outpatient/register', 'EditPen', 1, 2, 'outpatient:register'),
(21, 2, '挂号记录', '/outpatient/records', 'outpatient/records', 'Document', 2, 2, 'outpatient:records'),
(22, 2, '患者管理', '/outpatient/patient', 'outpatient/patient', 'User', 3, 2, 'outpatient:patient'),
(23, 2, '医生排班', '/outpatient/schedule', 'outpatient/schedule', 'Calendar', 4, 2, 'outpatient:schedule'),
(30, 3, '医生工作站', '/doctor/workstation', 'doctor/workstation', 'Monitor', 1, 2, 'doctor:workstation'),
(40, 4, '门诊收费', '/charge/list', 'charge/list', 'Wallet', 1, 2, 'charge:list'),
(41, 4, '收费记录', '/charge/records', 'charge/records', 'Tickets', 2, 2, 'charge:records'),
(50, 5, '药房发药', '/pharmacy/dispensing', 'pharmacy/dispensing', 'TakeawayBox', 1, 2, 'pharmacy:dispensing'),
(51, 5, '药品管理', '/pharmacy/drug', 'pharmacy/drug', 'FirstAidKit', 2, 2, 'pharmacy:drug'),
(52, 5, '库存管理', '/pharmacy/stock', 'pharmacy/stock', 'Goods', 3, 2, 'pharmacy:stock');

-- Role-Menu mappings
INSERT INTO sys_role_menu (role_id, menu_id) VALUES
(1,1),(1,2),(1,3),(1,4),(1,5),
(1,10),(1,11),(1,12),(1,13),
(1,20),(1,21),(1,22),(1,23),
(1,30),(1,40),(1,41),(1,50),(1,51),(1,52),
(2,3),(2,30),
(4,4),(4,40),(4,41),
(5,5),(5,50),(5,51),(5,52),
(6,2),(6,20),(6,21),(6,22);

-- Dictionaries
INSERT INTO sys_dict (id, name, code) VALUES
(1, '挂号类型', 'REGISTER_TYPE'),
(2, '性别', 'GENDER'),
(3, '血型', 'BLOOD_TYPE'),
(4, '支付方式', 'PAY_TYPE'),
(5, '药品分类', 'DRUG_CATEGORY');

INSERT INTO sys_dict_item (dict_id, label, "value", sort_order) VALUES
(1, '普通号', 'NORMAL', 1),
(1, '专家号', 'EXPERT', 2),
(1, '急诊号', 'EMERGENCY', 3),
(2, '男', '1', 1),
(2, '女', '2', 2),
(3, 'A型', 'A', 1),
(3, 'B型', 'B', 2),
(3, 'AB型', 'AB', 3),
(3, 'O型', 'O', 4),
(4, '现金', 'CASH', 1),
(4, '医保', 'MEDICAL_INSURANCE', 2),
(4, '微信', 'WECHAT', 3),
(4, '支付宝', 'ALIPAY', 4),
(5, '西药', 'WESTERN', 1),
(5, '中成药', 'CHINESE_PATENT', 2),
(5, '中草药', 'CHINESE_HERBAL', 3),
(5, '注射剂', 'INJECTION', 4);

-- Drug info
INSERT INTO drug_info (drug_code, drug_name, generic_name, spec, unit, manufacturer, category, unit_price) VALUES
('DRG001', '阿莫西林胶囊', '阿莫西林', '0.5g*24粒', '盒', '华北制药', 'WESTERN', 15.50),
('DRG002', '布洛芬缓释胶囊', '布洛芬', '0.3g*20粒', '盒', '中美史克', 'WESTERN', 22.00),
('DRG003', '阿奇霉素片', '阿奇霉素', '0.25g*6片', '盒', '辉瑞制药', 'WESTERN', 35.80),
('DRG004', '头孢克洛胶囊', '头孢克洛', '0.25g*12粒', '盒', '礼来制药', 'WESTERN', 28.50),
('DRG005', '复方甘草片', '复方甘草', '100片', '瓶', '上海医药', 'CHINESE_PATENT', 8.00),
('DRG006', '板蓝根颗粒', '板蓝根', '10g*20袋', '盒', '白云山', 'CHINESE_PATENT', 18.00),
('DRG007', '感冒灵颗粒', '感冒灵', '10g*9袋', '盒', '华润三九', 'CHINESE_PATENT', 16.50),
('DRG008', '维生素C片', '维生素C', '0.1g*100片', '瓶', '东北制药', 'WESTERN', 3.50),
('DRG009', '葡萄糖注射液', '葡萄糖', '250ml', '瓶', '石药集团', 'INJECTION', 4.20),
('DRG010', '氯化钠注射液', '氯化钠', '250ml', '瓶', '石药集团', 'INJECTION', 3.80);

-- Drug stock
INSERT INTO drug_stock (drug_id, batch_no, quantity, safe_stock, expire_date) VALUES
(1, 'B20240101', 500, 50, '2026-12-31'),
(2, 'B20240102', 300, 50, '2026-06-30'),
(3, 'B20240103', 200, 30, '2026-09-30'),
(4, 'B20240104', 400, 50, '2027-03-31'),
(5, 'B20240105', 800, 100, '2026-12-31'),
(6, 'B20240106', 600, 100, '2027-06-30'),
(7, 'B20240107', 450, 80, '2026-12-31'),
(8, 'B20240108', 1000, 200, '2028-01-31'),
(9, 'B20240109', 2000, 300, '2026-06-30'),
(10, 'B20240110', 2000, 300, '2026-06-30');

-- Doctor schedule
INSERT INTO op_schedule (doctor_id, dept_id, schedule_date, time_slot, total_num, used_num, fee_type) VALUES
(2, 5, CURRENT_DATE, 'AM', 30, 0, 'NORMAL'),
(2, 5, CURRENT_DATE, 'PM', 30, 0, 'NORMAL'),
(3, 6, CURRENT_DATE, 'AM', 20, 0, 'EXPERT'),
(3, 6, CURRENT_DATE, 'PM', 20, 0, 'EXPERT'),
(2, 5, DATEADD('DAY', 1, CURRENT_DATE), 'AM', 30, 0, 'NORMAL'),
(2, 5, DATEADD('DAY', 1, CURRENT_DATE), 'PM', 30, 0, 'NORMAL'),
(3, 6, DATEADD('DAY', 1, CURRENT_DATE), 'AM', 20, 0, 'EXPERT'),
(3, 6, DATEADD('DAY', 1, CURRENT_DATE), 'PM', 20, 0, 'EXPERT');
