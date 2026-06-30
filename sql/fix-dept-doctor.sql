-- ============================================
-- 重新设计科室和医生数据 (参照北京大学第三医院)
-- ============================================

USE his_db;

-- 清空原有数据
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE sys_dept;
TRUNCATE TABLE sys_user;
TRUNCATE TABLE sys_user_role;
TRUNCATE TABLE op_schedule;
SET FOREIGN_KEY_CHECKS = 1;

-- ============================================
-- 科室数据 (参照北大三院)
-- ============================================
INSERT INTO sys_dept (id, parent_id, name, code, type, sort_order) VALUES
-- 一级: 临床科室
(1, 0, '临床科室', 'CLINICAL', 1, 1),
-- 二级: 内科系统
(10, 1, '内科系统', 'INTERNAL', 1, 1),
(11, 10, '呼吸内科', 'HXNK', 1, 1),
(12, 10, '心血管内科', 'XGNK', 1, 2),
(13, 10, '消化内科', 'XHNK', 1, 3),
(14, 10, '内分泌科', 'NFMK', 1, 4),
(15, 10, '神经内科', 'SJNK', 1, 5),
(16, 10, '肾内科', 'SNK', 1, 6),
(17, 10, '血液内科', 'XYNK', 1, 7),
(18, 10, '风湿免疫科', 'FSMYK', 1, 8),
(19, 10, '感染疾病科', 'GRJBK', 1, 9),
-- 二级: 外科系统
(20, 1, '外科系统', 'SURGICAL', 1, 2),
(21, 20, '普通外科', 'PTWK', 1, 1),
(22, 20, '骨科', 'GK', 1, 2),
(23, 20, '泌尿外科', 'MNWK', 1, 3),
(24, 20, '神经外科', 'SJWK', 1, 4),
(25, 20, '心外科', 'XWK', 1, 5),
(26, 20, '胸外科', 'XWK2', 1, 6),
(27, 20, '血管外科', 'XGWK', 1, 7),
(28, 20, '整形外科', 'ZXWK', 1, 8),
-- 二级: 妇产科系统
(30, 1, '妇产科系统', 'FCK', 1, 3),
(31, 30, '妇科', 'FK', 1, 1),
(32, 30, '产科', 'CK', 1, 2),
(33, 30, '生殖医学中心', 'SZYZX', 1, 3),
-- 二级: 儿科
(40, 1, '儿科', 'EK', 1, 4),
(41, 40, '新生儿科', 'XSEK', 1, 1),
(42, 40, '小儿内科', 'XENK', 1, 2),
(43, 40, '小儿外科', 'XEWK', 1, 3),
-- 二级: 其他临床科室
(50, 1, '其他临床科室', 'OTHER', 1, 5),
(51, 50, '眼科', 'YK', 1, 1),
(52, 50, '耳鼻喉科', 'EBHK', 1, 2),
(53, 50, '口腔科', 'KQK', 1, 3),
(54, 50, '皮肤科', 'PFK', 1, 4),
(55, 50, '中医科', 'ZYK', 1, 5),
(56, 50, '康复医学科', 'KFYXK', 1, 6),
(57, 50, '疼痛科', 'TTK', 1, 7),
(58, 50, '急诊科', 'JZK', 1, 8),
(59, 50, '重症医学科', 'ZCYXK', 1, 9),
(60, 50, '全科医学科', 'QKYXK', 1, 10),

-- 一级: 医技科室
(70, 0, '医技科室', 'MEDICAL_TECH', 3, 2),
(71, 70, '医学检验科', 'YXJYK', 3, 1),
(72, 70, '放射科', 'FSK', 3, 2),
(73, 70, '超声诊断科', 'CSZDK', 3, 3),
(74, 70, '核医学科', 'HYXK', 3, 4),
(75, 70, '病理科', 'BLK', 3, 5),
(76, 70, '药剂科', 'YJK', 3, 6),
(77, 70, '输血科', 'SXK', 3, 7),
(78, 70, '营养科', 'YYK', 3, 8),

-- 一级: 行政后勤
(80, 0, '行政后勤', 'ADMIN', 4, 3),
(81, 80, '医务处', 'YWC', 4, 1),
(82, 80, '护理部', 'HLB', 4, 2),
(83, 80, '院感办', 'YGB', 4, 3),
(84, 80, '信息中心', 'XXZX', 4, 4);

-- ============================================
-- 用户数据 (虚构医生)
-- ============================================
INSERT INTO sys_user (id, username, password, real_name, gender, phone, dept_id, post) VALUES
-- 管理员
(1, 'admin', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '系统管理员', 1, '13800000001', 84, '系统管理员'),

-- 呼吸内科
(2, 'zhangwei', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '张伟', 1, '13800001001', 11, '主任医师'),
(3, 'liuna', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '刘娜', 2, '13800001002', 11, '副主任医师'),
(4, 'wangfang', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '王芳', 2, '13800001003', 11, '主治医师'),

-- 心血管内科
(5, 'chenming', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '陈明', 1, '13800001011', 12, '主任医师'),
(6, 'yanghua', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '杨华', 1, '13800001012', 12, '副主任医师'),
(7, 'zhaoli', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '赵丽', 2, '13800001013', 12, '主治医师'),

-- 消化内科
(8, 'huanghai', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '黄海', 1, '13800001021', 13, '主任医师'),
(9, 'sunxia', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '孙霞', 2, '13800001022', 13, '副主任医师'),

-- 内分泌科
(10, 'zhoujie', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '周杰', 1, '13800001031', 14, '主任医师'),
(11, 'wuying', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '吴英', 2, '13800001032', 14, '主治医师'),

-- 神经内科
(12, 'zhengqiang', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '郑强', 1, '13800001041', 15, '主任医师'),
(13, 'linjing', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '林静', 2, '13800001042', 15, '副主任医师'),

-- 普通外科
(14, 'xujian', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '徐建', 1, '13800002001', 21, '主任医师'),
(15, 'helan', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '何兰', 2, '13800002002', 21, '副主任医师'),
(16, 'guowei', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '郭伟', 1, '13800002003', 21, '主治医师'),

-- 骨科
(17, 'liqiang', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '李强', 1, '13800002011', 22, '主任医师'),
(18, 'wangxue', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '王雪', 2, '13800002012', 22, '副主任医师'),
(19, 'zhaopeng', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '赵鹏', 1, '13800002013', 22, '主治医师'),

-- 泌尿外科
(20, 'sunli', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '孙力', 1, '13800002021', 23, '主任医师'),

-- 神经外科
(21, 'zhouwei', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '周伟', 1, '13800002031', 24, '主任医师'),

-- 妇科
(22, 'chenying', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '陈英', 2, '13800003001', 31, '主任医师'),
(23, 'liuhua', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '刘华', 2, '13800003002', 31, '副主任医师'),

-- 产科
(24, 'wangpin', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '王萍', 2, '13800003011', 32, '主任医师'),

-- 儿科
(25, 'zhangmin', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '张敏', 2, '13800004001', 42, '主任医师'),
(26, 'liuxing', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '刘星', 1, '13800004002', 42, '副主任医师'),

-- 眼科
(27, 'huanglei', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '黄磊', 1, '13800005001', 51, '主任医师'),
(28, 'sunmei', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '孙梅', 2, '13800005002', 51, '副主任医师'),

-- 耳鼻喉科
(29, 'zhougang', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '周刚', 1, '13800005011', 52, '主任医师'),

-- 口腔科
(30, 'wudan', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '吴丹', 2, '13800005021', 53, '主任医师'),

-- 皮肤科
(31, 'zhengli', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '郑丽', 2, '13800005031', 54, '主任医师'),

-- 中医科
(32, 'wangguoqing', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '王国庆', 1, '13800005041', 55, '主任医师'),
(33, 'liumei', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '刘梅', 2, '13800005042', 55, '副主任医师'),

-- 急诊科
(34, 'chenqiang', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '陈强', 1, '13800006001', 58, '主任医师'),
(35, 'yangfang', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '杨芳', 2, '13800006002', 58, '副主任医师'),

-- 收费员
(36, 'cashier01', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '赵收费', 2, '13800009001', 81, '收费员'),
(37, 'cashier02', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '钱收费', 2, '13800009002', 81, '收费员'),

-- 药剂师
(38, 'pharmacist01', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '孙药剂', 1, '13800009011', 76, '主管药师'),
(39, 'pharmacist02', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '李药剂', 2, '13800009012', 76, '药师'),

-- 挂号员
(40, 'registrar01', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '周挂号', 2, '13800009021', 81, '挂号员');

-- ============================================
-- 用户角色关联
-- ============================================
INSERT INTO sys_user_role (user_id, role_id) VALUES
(1, 1),  -- admin -> 管理员
(2, 2), (3, 2), (4, 2),  -- 呼吸内科医生
(5, 2), (6, 2), (7, 2),  -- 心血管内科医生
(8, 2), (9, 2),          -- 消化内科医生
(10, 2), (11, 2),        -- 内分泌科医生
(12, 2), (13, 2),        -- 神经内科医生
(14, 2), (15, 2), (16, 2), -- 普通外科医生
(17, 2), (18, 2), (19, 2), -- 骨科医生
(20, 2),                 -- 泌尿外科医生
(21, 2),                 -- 神经外科医生
(22, 2), (23, 2),        -- 妇科医生
(24, 2),                 -- 产科医生
(25, 2), (26, 2),        -- 儿科医生
(27, 2), (28, 2),        -- 眼科医生
(29, 2),                 -- 耳鼻喉科医生
(30, 2),                 -- 口腔科医生
(31, 2),                 -- 皮肤科医生
(32, 2), (33, 2),        -- 中医科医生
(34, 2), (35, 2),        -- 急诊科医生
(36, 3), (37, 3),        -- 收费员
(38, 4), (39, 4),        -- 药剂师
(40, 5);                 -- 挂号员

-- ============================================
-- 医生排班 (本周，主要科室)
-- ============================================
INSERT INTO op_schedule (doctor_id, dept_id, schedule_date, time_slot, total_num, used_num, fee_type) VALUES
-- 呼吸内科 (张伟-主任 周一三五上午, 刘娜-副主任 周二四全天, 王芳-主治 周一至五全天)
(2, 11, CURDATE(), 'AM', 25, 0, 'EXPERT'),
(2, 11, DATE_ADD(CURDATE(), INTERVAL 2 DAY), 'AM', 25, 0, 'EXPERT'),
(2, 11, DATE_ADD(CURDATE(), INTERVAL 4 DAY), 'AM', 25, 0, 'EXPERT'),
(3, 11, DATE_ADD(CURDATE(), INTERVAL 1 DAY), 'AM', 30, 0, 'EXPERT'),
(3, 11, DATE_ADD(CURDATE(), INTERVAL 1 DAY), 'PM', 30, 0, 'EXPERT'),
(3, 11, DATE_ADD(CURDATE(), INTERVAL 3 DAY), 'AM', 30, 0, 'EXPERT'),
(3, 11, DATE_ADD(CURDATE(), INTERVAL 3 DAY), 'PM', 30, 0, 'EXPERT'),
(4, 11, CURDATE(), 'AM', 40, 0, 'NORMAL'),
(4, 11, CURDATE(), 'PM', 40, 0, 'NORMAL'),
(4, 11, DATE_ADD(CURDATE(), INTERVAL 1 DAY), 'AM', 40, 0, 'NORMAL'),
(4, 11, DATE_ADD(CURDATE(), INTERVAL 1 DAY), 'PM', 40, 0, 'NORMAL'),
(4, 11, DATE_ADD(CURDATE(), INTERVAL 2 DAY), 'AM', 40, 0, 'NORMAL'),
(4, 11, DATE_ADD(CURDATE(), INTERVAL 2 DAY), 'PM', 40, 0, 'NORMAL'),
(4, 11, DATE_ADD(CURDATE(), INTERVAL 3 DAY), 'AM', 40, 0, 'NORMAL'),
(4, 11, DATE_ADD(CURDATE(), INTERVAL 3 DAY), 'PM', 40, 0, 'NORMAL'),
(4, 11, DATE_ADD(CURDATE(), INTERVAL 4 DAY), 'AM', 40, 0, 'NORMAL'),
(4, 11, DATE_ADD(CURDATE(), INTERVAL 4 DAY), 'PM', 40, 0, 'NORMAL'),

-- 心血管内科 (陈明-主任 周一二四上午, 杨华-副主任 周三五全天, 赵丽-主治 周一至五全天)
(5, 12, CURDATE(), 'AM', 20, 0, 'EXPERT'),
(5, 12, DATE_ADD(CURDATE(), INTERVAL 1 DAY), 'AM', 20, 0, 'EXPERT'),
(5, 12, DATE_ADD(CURDATE(), INTERVAL 3 DAY), 'AM', 20, 0, 'EXPERT'),
(6, 12, DATE_ADD(CURDATE(), INTERVAL 2 DAY), 'AM', 25, 0, 'EXPERT'),
(6, 12, DATE_ADD(CURDATE(), INTERVAL 2 DAY), 'PM', 25, 0, 'EXPERT'),
(6, 12, DATE_ADD(CURDATE(), INTERVAL 4 DAY), 'AM', 25, 0, 'EXPERT'),
(6, 12, DATE_ADD(CURDATE(), INTERVAL 4 DAY), 'PM', 25, 0, 'EXPERT'),
(7, 12, CURDATE(), 'AM', 35, 0, 'NORMAL'),
(7, 12, CURDATE(), 'PM', 35, 0, 'NORMAL'),
(7, 12, DATE_ADD(CURDATE(), INTERVAL 1 DAY), 'AM', 35, 0, 'NORMAL'),
(7, 12, DATE_ADD(CURDATE(), INTERVAL 1 DAY), 'PM', 35, 0, 'NORMAL'),
(7, 12, DATE_ADD(CURDATE(), INTERVAL 2 DAY), 'AM', 35, 0, 'NORMAL'),
(7, 12, DATE_ADD(CURDATE(), INTERVAL 2 DAY), 'PM', 35, 0, 'NORMAL'),
(7, 12, DATE_ADD(CURDATE(), INTERVAL 3 DAY), 'AM', 35, 0, 'NORMAL'),
(7, 12, DATE_ADD(CURDATE(), INTERVAL 3 DAY), 'PM', 35, 0, 'NORMAL'),
(7, 12, DATE_ADD(CURDATE(), INTERVAL 4 DAY), 'AM', 35, 0, 'NORMAL'),
(7, 12, DATE_ADD(CURDATE(), INTERVAL 4 DAY), 'PM', 35, 0, 'NORMAL'),

-- 消化内科 (黄海-主任 周一三上午, 孙霞-副主任 周二四五全天)
(8, 13, CURDATE(), 'AM', 25, 0, 'EXPERT'),
(8, 13, DATE_ADD(CURDATE(), INTERVAL 2 DAY), 'AM', 25, 0, 'EXPERT'),
(9, 13, DATE_ADD(CURDATE(), INTERVAL 1 DAY), 'AM', 30, 0, 'EXPERT'),
(9, 13, DATE_ADD(CURDATE(), INTERVAL 1 DAY), 'PM', 30, 0, 'EXPERT'),
(9, 13, DATE_ADD(CURDATE(), INTERVAL 3 DAY), 'AM', 30, 0, 'EXPERT'),
(9, 13, DATE_ADD(CURDATE(), INTERVAL 3 DAY), 'PM', 30, 0, 'EXPERT'),
(9, 13, DATE_ADD(CURDATE(), INTERVAL 4 DAY), 'AM', 30, 0, 'EXPERT'),
(9, 13, DATE_ADD(CURDATE(), INTERVAL 4 DAY), 'PM', 30, 0, 'EXPERT'),

-- 内分泌科 (周杰-主任 周一三五上午, 吴英-主治 周一至五全天)
(10, 14, CURDATE(), 'AM', 25, 0, 'EXPERT'),
(10, 14, DATE_ADD(CURDATE(), INTERVAL 2 DAY), 'AM', 25, 0, 'EXPERT'),
(10, 14, DATE_ADD(CURDATE(), INTERVAL 4 DAY), 'AM', 25, 0, 'EXPERT'),
(11, 14, CURDATE(), 'AM', 35, 0, 'NORMAL'),
(11, 14, CURDATE(), 'PM', 35, 0, 'NORMAL'),
(11, 14, DATE_ADD(CURDATE(), INTERVAL 1 DAY), 'AM', 35, 0, 'NORMAL'),
(11, 14, DATE_ADD(CURDATE(), INTERVAL 1 DAY), 'PM', 35, 0, 'NORMAL'),
(11, 14, DATE_ADD(CURDATE(), INTERVAL 2 DAY), 'AM', 35, 0, 'NORMAL'),
(11, 14, DATE_ADD(CURDATE(), INTERVAL 2 DAY), 'PM', 35, 0, 'NORMAL'),
(11, 14, DATE_ADD(CURDATE(), INTERVAL 3 DAY), 'AM', 35, 0, 'NORMAL'),
(11, 14, DATE_ADD(CURDATE(), INTERVAL 3 DAY), 'PM', 35, 0, 'NORMAL'),
(11, 14, DATE_ADD(CURDATE(), INTERVAL 4 DAY), 'AM', 35, 0, 'NORMAL'),
(11, 14, DATE_ADD(CURDATE(), INTERVAL 4 DAY), 'PM', 35, 0, 'NORMAL'),

-- 神经内科 (郑强-主任 周二四上午, 林静-副主任 周一三五全天)
(12, 15, DATE_ADD(CURDATE(), INTERVAL 1 DAY), 'AM', 20, 0, 'EXPERT'),
(12, 15, DATE_ADD(CURDATE(), INTERVAL 3 DAY), 'AM', 20, 0, 'EXPERT'),
(13, 15, CURDATE(), 'AM', 30, 0, 'EXPERT'),
(13, 15, CURDATE(), 'PM', 30, 0, 'EXPERT'),
(13, 15, DATE_ADD(CURDATE(), INTERVAL 2 DAY), 'AM', 30, 0, 'EXPERT'),
(13, 15, DATE_ADD(CURDATE(), INTERVAL 2 DAY), 'PM', 30, 0, 'EXPERT'),
(13, 15, DATE_ADD(CURDATE(), INTERVAL 4 DAY), 'AM', 30, 0, 'EXPERT'),
(13, 15, DATE_ADD(CURDATE(), INTERVAL 4 DAY), 'PM', 30, 0, 'EXPERT'),

-- 普通外科 (徐建-主任 周一三上午, 何兰-副主任 周二四全天, 郭伟-主治 周一至五全天)
(14, 21, CURDATE(), 'AM', 20, 0, 'EXPERT'),
(14, 21, DATE_ADD(CURDATE(), INTERVAL 2 DAY), 'AM', 20, 0, 'EXPERT'),
(15, 21, DATE_ADD(CURDATE(), INTERVAL 1 DAY), 'AM', 25, 0, 'EXPERT'),
(15, 21, DATE_ADD(CURDATE(), INTERVAL 1 DAY), 'PM', 25, 0, 'EXPERT'),
(15, 21, DATE_ADD(CURDATE(), INTERVAL 3 DAY), 'AM', 25, 0, 'EXPERT'),
(15, 21, DATE_ADD(CURDATE(), INTERVAL 3 DAY), 'PM', 25, 0, 'EXPERT'),
(16, 21, CURDATE(), 'AM', 35, 0, 'NORMAL'),
(16, 21, CURDATE(), 'PM', 35, 0, 'NORMAL'),
(16, 21, DATE_ADD(CURDATE(), INTERVAL 1 DAY), 'AM', 35, 0, 'NORMAL'),
(16, 21, DATE_ADD(CURDATE(), INTERVAL 1 DAY), 'PM', 35, 0, 'NORMAL'),
(16, 21, DATE_ADD(CURDATE(), INTERVAL 2 DAY), 'AM', 35, 0, 'NORMAL'),
(16, 21, DATE_ADD(CURDATE(), INTERVAL 2 DAY), 'PM', 35, 0, 'NORMAL'),
(16, 21, DATE_ADD(CURDATE(), INTERVAL 3 DAY), 'AM', 35, 0, 'NORMAL'),
(16, 21, DATE_ADD(CURDATE(), INTERVAL 3 DAY), 'PM', 35, 0, 'NORMAL'),
(16, 21, DATE_ADD(CURDATE(), INTERVAL 4 DAY), 'AM', 35, 0, 'NORMAL'),
(16, 21, DATE_ADD(CURDATE(), INTERVAL 4 DAY), 'PM', 35, 0, 'NORMAL'),

-- 骨科 (李强-主任 周一二四上午, 王雪-副主任 周三五全天, 赵鹏-主治 周一至五全天)
(17, 22, CURDATE(), 'AM', 20, 0, 'EXPERT'),
(17, 22, DATE_ADD(CURDATE(), INTERVAL 1 DAY), 'AM', 20, 0, 'EXPERT'),
(17, 22, DATE_ADD(CURDATE(), INTERVAL 3 DAY), 'AM', 20, 0, 'EXPERT'),
(18, 22, DATE_ADD(CURDATE(), INTERVAL 2 DAY), 'AM', 25, 0, 'EXPERT'),
(18, 22, DATE_ADD(CURDATE(), INTERVAL 2 DAY), 'PM', 25, 0, 'EXPERT'),
(18, 22, DATE_ADD(CURDATE(), INTERVAL 4 DAY), 'AM', 25, 0, 'EXPERT'),
(18, 22, DATE_ADD(CURDATE(), INTERVAL 4 DAY), 'PM', 25, 0, 'EXPERT'),
(19, 22, CURDATE(), 'AM', 35, 0, 'NORMAL'),
(19, 22, CURDATE(), 'PM', 35, 0, 'NORMAL'),
(19, 22, DATE_ADD(CURDATE(), INTERVAL 1 DAY), 'AM', 35, 0, 'NORMAL'),
(19, 22, DATE_ADD(CURDATE(), INTERVAL 1 DAY), 'PM', 35, 0, 'NORMAL'),
(19, 22, DATE_ADD(CURDATE(), INTERVAL 2 DAY), 'AM', 35, 0, 'NORMAL'),
(19, 22, DATE_ADD(CURDATE(), INTERVAL 2 DAY), 'PM', 35, 0, 'NORMAL'),
(19, 22, DATE_ADD(CURDATE(), INTERVAL 3 DAY), 'AM', 35, 0, 'NORMAL'),
(19, 22, DATE_ADD(CURDATE(), INTERVAL 3 DAY), 'PM', 35, 0, 'NORMAL'),
(19, 22, DATE_ADD(CURDATE(), INTERVAL 4 DAY), 'AM', 35, 0, 'NORMAL'),
(19, 22, DATE_ADD(CURDATE(), INTERVAL 4 DAY), 'PM', 35, 0, 'NORMAL'),

-- 妇科 (陈英-主任 周一三上午, 刘华-副主任 周二四五全天)
(22, 31, CURDATE(), 'AM', 25, 0, 'EXPERT'),
(22, 31, DATE_ADD(CURDATE(), INTERVAL 2 DAY), 'AM', 25, 0, 'EXPERT'),
(23, 31, DATE_ADD(CURDATE(), INTERVAL 1 DAY), 'AM', 30, 0, 'EXPERT'),
(23, 31, DATE_ADD(CURDATE(), INTERVAL 1 DAY), 'PM', 30, 0, 'EXPERT'),
(23, 31, DATE_ADD(CURDATE(), INTERVAL 3 DAY), 'AM', 30, 0, 'EXPERT'),
(23, 31, DATE_ADD(CURDATE(), INTERVAL 3 DAY), 'PM', 30, 0, 'EXPERT'),
(23, 31, DATE_ADD(CURDATE(), INTERVAL 4 DAY), 'AM', 30, 0, 'EXPERT'),
(23, 31, DATE_ADD(CURDATE(), INTERVAL 4 DAY), 'PM', 30, 0, 'EXPERT'),

-- 产科 (王萍-主任 周一至五上午)
(24, 32, CURDATE(), 'AM', 30, 0, 'EXPERT'),
(24, 32, DATE_ADD(CURDATE(), INTERVAL 1 DAY), 'AM', 30, 0, 'EXPERT'),
(24, 32, DATE_ADD(CURDATE(), INTERVAL 2 DAY), 'AM', 30, 0, 'EXPERT'),
(24, 32, DATE_ADD(CURDATE(), INTERVAL 3 DAY), 'AM', 30, 0, 'EXPERT'),
(24, 32, DATE_ADD(CURDATE(), INTERVAL 4 DAY), 'AM', 30, 0, 'EXPERT'),

-- 儿科 (张敏-主任 周一三五上午, 刘星-副主任 周二四全天)
(25, 42, CURDATE(), 'AM', 25, 0, 'EXPERT'),
(25, 42, DATE_ADD(CURDATE(), INTERVAL 2 DAY), 'AM', 25, 0, 'EXPERT'),
(25, 42, DATE_ADD(CURDATE(), INTERVAL 4 DAY), 'AM', 25, 0, 'EXPERT'),
(26, 42, DATE_ADD(CURDATE(), INTERVAL 1 DAY), 'AM', 30, 0, 'EXPERT'),
(26, 42, DATE_ADD(CURDATE(), INTERVAL 1 DAY), 'PM', 30, 0, 'EXPERT'),
(26, 42, DATE_ADD(CURDATE(), INTERVAL 3 DAY), 'AM', 30, 0, 'EXPERT'),
(26, 42, DATE_ADD(CURDATE(), INTERVAL 3 DAY), 'PM', 30, 0, 'EXPERT'),

-- 眼科 (黄磊-主任 周一三上午, 孙梅-副主任 周二四五全天)
(27, 51, CURDATE(), 'AM', 25, 0, 'EXPERT'),
(27, 51, DATE_ADD(CURDATE(), INTERVAL 2 DAY), 'AM', 25, 0, 'EXPERT'),
(28, 51, DATE_ADD(CURDATE(), INTERVAL 1 DAY), 'AM', 30, 0, 'EXPERT'),
(28, 51, DATE_ADD(CURDATE(), INTERVAL 1 DAY), 'PM', 30, 0, 'EXPERT'),
(28, 51, DATE_ADD(CURDATE(), INTERVAL 3 DAY), 'AM', 30, 0, 'EXPERT'),
(28, 51, DATE_ADD(CURDATE(), INTERVAL 3 DAY), 'PM', 30, 0, 'EXPERT'),
(28, 51, DATE_ADD(CURDATE(), INTERVAL 4 DAY), 'AM', 30, 0, 'EXPERT'),
(28, 51, DATE_ADD(CURDATE(), INTERVAL 4 DAY), 'PM', 30, 0, 'EXPERT'),

-- 耳鼻喉科 (周刚-主任 周一至五上午)
(29, 52, CURDATE(), 'AM', 30, 0, 'EXPERT'),
(29, 52, DATE_ADD(CURDATE(), INTERVAL 1 DAY), 'AM', 30, 0, 'EXPERT'),
(29, 52, DATE_ADD(CURDATE(), INTERVAL 2 DAY), 'AM', 30, 0, 'EXPERT'),
(29, 52, DATE_ADD(CURDATE(), INTERVAL 3 DAY), 'AM', 30, 0, 'EXPERT'),
(29, 52, DATE_ADD(CURDATE(), INTERVAL 4 DAY), 'AM', 30, 0, 'EXPERT'),

-- 口腔科 (吴丹-主任 周一至五全天)
(30, 53, CURDATE(), 'AM', 20, 0, 'EXPERT'),
(30, 53, CURDATE(), 'PM', 20, 0, 'EXPERT'),
(30, 53, DATE_ADD(CURDATE(), INTERVAL 1 DAY), 'AM', 20, 0, 'EXPERT'),
(30, 53, DATE_ADD(CURDATE(), INTERVAL 1 DAY), 'PM', 20, 0, 'EXPERT'),
(30, 53, DATE_ADD(CURDATE(), INTERVAL 2 DAY), 'AM', 20, 0, 'EXPERT'),
(30, 53, DATE_ADD(CURDATE(), INTERVAL 2 DAY), 'PM', 20, 0, 'EXPERT'),
(30, 53, DATE_ADD(CURDATE(), INTERVAL 3 DAY), 'AM', 20, 0, 'EXPERT'),
(30, 53, DATE_ADD(CURDATE(), INTERVAL 3 DAY), 'PM', 20, 0, 'EXPERT'),
(30, 53, DATE_ADD(CURDATE(), INTERVAL 4 DAY), 'AM', 20, 0, 'EXPERT'),
(30, 53, DATE_ADD(CURDATE(), INTERVAL 4 DAY), 'PM', 20, 0, 'EXPERT'),

-- 皮肤科 (郑丽-主任 周一至五全天)
(31, 54, CURDATE(), 'AM', 35, 0, 'EXPERT'),
(31, 54, CURDATE(), 'PM', 35, 0, 'EXPERT'),
(31, 54, DATE_ADD(CURDATE(), INTERVAL 1 DAY), 'AM', 35, 0, 'EXPERT'),
(31, 54, DATE_ADD(CURDATE(), INTERVAL 1 DAY), 'PM', 35, 0, 'EXPERT'),
(31, 54, DATE_ADD(CURDATE(), INTERVAL 2 DAY), 'AM', 35, 0, 'EXPERT'),
(31, 54, DATE_ADD(CURDATE(), INTERVAL 2 DAY), 'PM', 35, 0, 'EXPERT'),
(31, 54, DATE_ADD(CURDATE(), INTERVAL 3 DAY), 'AM', 35, 0, 'EXPERT'),
(31, 54, DATE_ADD(CURDATE(), INTERVAL 3 DAY), 'PM', 35, 0, 'EXPERT'),
(31, 54, DATE_ADD(CURDATE(), INTERVAL 4 DAY), 'AM', 35, 0, 'EXPERT'),
(31, 54, DATE_ADD(CURDATE(), INTERVAL 4 DAY), 'PM', 35, 0, 'EXPERT'),

-- 中医科 (王国庆-主任 周一三五上午, 刘梅-副主任 周二四全天)
(32, 55, CURDATE(), 'AM', 20, 0, 'EXPERT'),
(32, 55, DATE_ADD(CURDATE(), INTERVAL 2 DAY), 'AM', 20, 0, 'EXPERT'),
(32, 55, DATE_ADD(CURDATE(), INTERVAL 4 DAY), 'AM', 20, 0, 'EXPERT'),
(33, 55, DATE_ADD(CURDATE(), INTERVAL 1 DAY), 'AM', 25, 0, 'EXPERT'),
(33, 55, DATE_ADD(CURDATE(), INTERVAL 1 DAY), 'PM', 25, 0, 'EXPERT'),
(33, 55, DATE_ADD(CURDATE(), INTERVAL 3 DAY), 'AM', 25, 0, 'EXPERT'),
(33, 55, DATE_ADD(CURDATE(), INTERVAL 3 DAY), 'PM', 25, 0, 'EXPERT'),

-- 急诊科 (陈强-主任 全天, 杨芳-副主任 全天)
(34, 58, CURDATE(), 'AM', 50, 0, 'EMERGENCY'),
(34, 58, CURDATE(), 'PM', 50, 0, 'EMERGENCY'),
(35, 58, CURDATE(), 'AM', 50, 0, 'EMERGENCY'),
(35, 58, CURDATE(), 'PM', 50, 0, 'EMERGENCY');

SELECT '科室和医生数据更新完成！' AS result;
