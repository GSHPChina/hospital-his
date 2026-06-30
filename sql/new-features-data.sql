USE his_db;

-- 病历模板
INSERT INTO emr_template (name, dept_id, category, content, creator_id) VALUES
('内科通用病历模板', 10, 'INTERNAL', '{"sections":["chief_complaint","present_illness","past_history","physical_exam","diagnosis","treatment"]}', 1),
('外科通用病历模板', 20, 'SURGICAL', '{"sections":["chief_complaint","present_illness","past_history","physical_exam","surgical_history","diagnosis","treatment"]}', 1),
('儿科病历模板', 40, 'PEDIATRIC', '{"sections":["chief_complaint","present_illness","birth_history","immunization","physical_exam","diagnosis","treatment"]}', 1),
('急诊病历模板', 58, 'EMERGENCY', '{"sections":["chief_complaint","present_illness","vital_signs","physical_exam","emergency_treatment","diagnosis"]}', 1);

-- 设备分类
INSERT INTO eq_category (id, parent_id, name, code, description, sort_order) VALUES
(1, 0, '医疗设备', 'MEDICAL', '医疗专用设备', 1),
(2, 0, '办公设备', 'OFFICE', '办公用设备', 2),
(3, 0, '后勤设备', 'LOGISTICS', '后勤保障设备', 3),
(4, 1, '影像设备', 'IMAGING', 'CT、MRI、X光等', 1),
(5, 1, '检验设备', 'LAB', '生化分析仪等', 2),
(6, 1, '手术设备', 'SURGICAL', '手术器械、麻醉机等', 3),
(7, 1, '监护设备', 'MONITOR', '心电监护、呼吸机等', 4),
(8, 1, '治疗设备', 'TREATMENT', '理疗、放疗设备', 5),
(9, 2, '电脑设备', 'COMPUTER', '电脑、打印机等', 1),
(10, 2, '办公家具', 'FURNITURE', '桌椅、文件柜等', 2);

-- 设备台账
INSERT INTO eq_equipment (equip_no, name, category_id, brand, model, manufacturer, serial_no, dept_id, location, purchase_date, purchase_price, warranty_date, depreciation_years, current_value, status, responsible_person) VALUES
('EQ001', 'CT扫描仪', 4, '西门子', 'SOMATOM', 'Siemens Healthineers', 'CT2024001', 16, '放射科CT室', '2024-01-15', 3500000.00, '2027-01-15', 10, 3150000.00, 1, '张主任'),
('EQ002', '核磁共振仪', 4, 'GE', 'SIGNA', 'GE Healthcare', 'MRI2024001', 16, '放射科MRI室', '2024-03-20', 5000000.00, '2027-03-20', 10, 4500000.00, 1, '张主任'),
('EQ003', '生化分析仪', 5, '罗氏', 'cobas c513', 'Roche Diagnostics', 'LAB2024001', 71, '检验科', '2024-02-10', 800000.00, '2027-02-10', 5, 640000.00, 1, '李主任'),
('EQ004', '呼吸机', 7, '迈瑞', 'SV300', 'Mindray', 'VENT2024001', 59, 'ICU', '2024-05-01', 250000.00, '2026-05-01', 5, 200000.00, 1, '王护士长'),
('EQ005', '心电监护仪', 7, '迈瑞', 'iPM12', 'Mindray', 'MON2024001', 59, 'ICU', '2024-05-01', 80000.00, '2026-05-01', 5, 64000.00, 1, '王护士长'),
('EQ006', '麻醉机', 6, '德尔格', 'Primus', 'Drager', 'ANES2024001', 21, '手术室', '2024-04-15', 450000.00, '2027-04-15', 8, 405000.00, 1, '赵主任'),
('EQ007', '超声诊断仪', 4, '飞利浦', 'EPIQ7', 'Philips', 'US2024001', 17, '超声科', '2024-06-01', 1200000.00, '2027-06-01', 8, 1080000.00, 1, '周主任'),
('EQ008', 'X光机', 4, '锐珂', 'DRX-Revolution', 'Carestream', 'XRAY2024001', 16, '放射科X光室', '2024-01-20', 600000.00, '2027-01-20', 8, 540000.00, 1, '张主任'),
('EQ009', '腹腔镜系统', 6, '奥林巴斯', 'VISERA ELITE II', 'Olympus', 'LAP2024001', 21, '手术室', '2024-07-01', 350000.00, '2027-07-01', 5, 315000.00, 1, '赵主任'),
('EQ010', '全自动血液分析仪', 5, '希森美康', 'XN-1000', 'Sysmex', 'HEMA2024001', 71, '检验科', '2024-03-01', 500000.00, '2027-03-01', 5, 400000.00, 1, '李主任');

-- 设备维修记录
INSERT INTO eq_maintenance (equip_id, type, description, cost, technician, start_time, end_time, status, result) VALUES
(1, 'MAINTENANCE', '季度保养，检查球管、校准图像', 5000.00, '工程师张工', '2026-06-01 09:00:00', '2026-06-01 17:00:00', 3, '保养完成，设备正常'),
(3, 'REPAIR', '试剂针堵塞，需要清洗', 2000.00, '工程师李工', '2026-06-15 10:00:00', '2026-06-15 15:00:00', 3, '维修完成，已恢复正常'),
(4, 'INSPECTION', '例行巡检，检查管路和报警系统', 0, '王护士长', '2026-06-20 14:00:00', '2026-06-20 15:00:00', 3, '巡检正常'),
(7, 'REPAIR', '探头故障，需要更换', 15000.00, '工程师王工', '2026-06-22 09:00:00', NULL, 2, NULL);

-- 预约挂号数据
INSERT INTO op_appointment (appointment_no, patient_id, dept_id, doctor_id, appointment_date, time_slot, queue_no, fee_type, fee, status) VALUES
('APT20260626001', 3, 11, 2, '2026-06-27', 'AM', 1, 'EXPERT', 50.00, 1),
('APT20260626002', 2, 12, 5, '2026-06-27', 'AM', 2, 'EXPERT', 50.00, 1),
('APT20260626003', 1, 22, 17, '2026-06-28', 'PM', 1, 'EXPERT', 50.00, 1);

-- 排队叫号数据
INSERT INTO op_queue (queue_no, doctor_id, patient_id, register_id, queue_date, status) VALUES
('A001', 2, 3, 2, CURDATE(), 1),
('A002', 2, 1, 1, CURDATE(), 1);

-- 新增菜单
INSERT INTO sys_menu (id, parent_id, name, path, component, icon, sort_order, type, permission) VALUES
-- 院长驾驶舱
(120, 0, 'Dashboard', '/bigscreen', NULL, 'Monitor', 12, 1, NULL),
(121, 120, 'Hospital Dashboard', '/bigscreen/dashboard', 'bigscreen/dashboard', 'DataBoard', 1, 2, 'bigscreen:dashboard'),
-- 预约挂号
(130, 2, 'Appointment', '/outpatient/appointment', 'outpatient/appointment', 'Calendar', 5, 2, 'outpatient:appointment'),
(131, 2, 'Queue Management', '/outpatient/queue', 'outpatient/queue', 'Bell', 6, 2, 'outpatient:queue'),
-- 电子病历
(140, 0, 'EMR', '/emr', NULL, 'Notebook', 13, 1, NULL),
(141, 140, 'EMR Record', '/emr/record', 'emr/record', 'Document', 1, 2, 'emr:record'),
(142, 140, 'EMR Template', '/emr/template', 'emr/template', 'Collection', 2, 2, 'emr:template'),
(143, 140, 'Quality Control', '/emr/quality', 'emr/quality', 'CircleCheck', 3, 2, 'emr:quality'),
-- 设备管理
(150, 0, 'Equipment', '/equipment', NULL, 'Cpu', 14, 1, NULL),
(151, 150, 'Equipment List', '/equipment/list', 'equipment/list', 'List', 1, 2, 'equipment:list'),
(152, 150, 'Maintenance', '/equipment/maintenance', 'equipment/maintenance', 'Tools', 2, 2, 'equipment:maintenance'),
(153, 150, 'Depreciation', '/equipment/depreciation', 'equipment/depreciation', 'TrendCharts', 3, 2, 'equipment:depreciation');

-- 管理员权限
INSERT INTO sys_role_menu (role_id, menu_id) VALUES
(1,120),(1,121),(1,130),(1,131),(1,140),(1,141),(1,142),(1,143),(1,150),(1,151),(1,152),(1,153);

SELECT 'New features data inserted!' AS result;
