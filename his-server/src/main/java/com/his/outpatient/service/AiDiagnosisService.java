package com.his.outpatient.service;

import com.his.outpatient.dto.AiDiagnosisResult;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * AI辅助诊断服务
 * 基于临床指南的智能诊断推荐
 */
@Service
public class AiDiagnosisService {

    private static final Map<String, AiDiagnosisResult> DISEASE_DB = new HashMap<>();

    static {
        // ===== 呼吸系统疾病 =====
        register("咳嗽,咳痰,发热,咽痛,流涕,鼻塞",
                "J06.9", "急性上呼吸道感染",
                "急性上呼吸道感染（感冒）",
                "患者因咳嗽、咳痰伴发热就诊。",
                "病毒或细菌感染引起的上呼吸道炎症，常见病原体包括鼻病毒、冠状病毒、流感病毒等。",
                "无特殊既往史。",
                "T 38.2℃, 咽部充血, 双肺呼吸音清，未闻及干湿啰音。",
                "1. 一般治疗：多饮水，注意休息，清淡饮食\n2. 对症治疗：退热、止咳化痰\n3. 抗感染治疗：如有细菌感染证据可使用抗生素\n4. 随访：如症状加重及时复诊",
                "根据《急性上呼吸道感染诊疗指南(2023版)》：\n" +
                "• 普通感冒以对症治疗为主，不推荐常规使用抗菌药物\n" +
                "• 如有细菌感染证据（WBC升高、CRP升高等），可选用青霉素类或头孢类\n" +
                "• 退热推荐对乙酰氨基酚或布洛芬\n" +
                "• 止咳可选用右美沙芬或复方甘草片",
                new String[][]{
                    {"阿莫西林胶囊", "0.5g*24粒", "盒", "15.50", "1粒", "TID", "口服", "7", "青霉素类抗生素，用于细菌感染"},
                    {"复方甘草片", "100片", "瓶", "8.00", "3片", "TID", "口服", "7", "镇咳祛痰，用于咳嗽症状"},
                    {"布洛芬缓释胶囊", "0.3g*20粒", "盒", "22.00", "1粒", "PRN", "口服", "3", "解热镇痛，体温>38.5℃时使用"},
                    {"盐酸氨溴索口服液", "100ml", "瓶", "18.00", "10ml", "TID", "口服", "7", "祛痰，促进痰液排出"}
                });

        register("咳嗽,气喘,胸闷,呼吸困难,咳痰",
                "J44.1", "慢性阻塞性肺疾病急性发作",
                "AECOPD（慢性阻塞性肺疾病急性加重）",
                "患者因咳嗽、气喘加重伴胸闷就诊。",
                "慢性阻塞性肺疾病急性加重，多由感染诱发，表现为呼吸困难加重、咳嗽加剧、痰量增多或痰液变脓。",
                "既往COPD病史。",
                "T 37.5℃, R 24次/分, 双肺可闻及哮鸣音及湿啰音，桶状胸。",
                "1. 控制性氧疗：维持SpO2 88-92%\n2. 支气管扩张剂：沙丁胺醇+异丙托溴铵雾化\n3. 糖皮质激素：甲泼尼龙静滴\n4. 抗感染治疗\n5. 祛痰、纠正电解质紊乱",
                "根据《慢性阻塞性肺疾病诊治指南(2021年修订版)》：\n" +
                "• AECOPD首选短效支气管扩张剂雾化吸入\n" +
                "• 全身糖皮质激素可缩短恢复时间，推荐泼尼松30-40mg/d，5-7天\n" +
                "• 抗生素适用于有脓性痰的患者\n" +
                "• 氧疗目标SpO2 88-92%，避免高浓度吸氧",
                new String[][]{
                    {"阿奇霉素片", "0.25g*6片", "盒", "35.80", "2片", "QD", "口服", "5", "大环内酯类抗生素，抗感染"},
                    {"氨茶碱片", "0.1g*100片", "瓶", "12.00", "1片", "TID", "口服", "7", "支气管扩张剂，缓解气喘"},
                    {"盐酸氨溴索口服液", "100ml", "瓶", "18.00", "10ml", "TID", "口服", "7", "祛痰"},
                    {"甲泼尼龙片", "4mg*30片", "盒", "28.00", "4片", "BID", "口服", "5", "糖皮质激素，控制炎症"}
                });

        register("发热,头痛,乏力,肌肉酸痛,畏寒,高热",
                "J11.1", "流行性感冒",
                "流行性感冒（流感）",
                "患者因发热、头痛、全身乏力就诊。",
                "流感病毒感染引起的急性呼吸道传染病，起病急，全身症状重。",
                "无特殊既往史。",
                "T 39.1℃, 咽部充血, 扁桃体无肿大，全身肌肉酸痛。",
                "1. 隔离治疗，避免交叉感染\n2. 抗病毒治疗：奥司他韦（48h内使用效果最佳）\n3. 对症治疗：退热、补液\n4. 休息，多饮水",
                "根据《流行性感冒诊疗方案(2020年版)》：\n" +
                "• 奥司他韦为首选抗流感病毒药物，发病48h内开始使用\n" +
                "• 成人剂量75mg bid，疗程5天\n" +
                "• 重症患者可适当延长疗程\n" +
                "• 不推荐常规使用抗生素",
                new String[][]{
                    {"磷酸奥司他韦胶囊", "75mg*10粒", "盒", "85.00", "1粒", "BID", "口服", "5", "神经氨酸酶抑制剂，抗流感病毒"},
                    {"布洛芬缓释胶囊", "0.3g*20粒", "盒", "22.00", "1粒", "PRN", "口服", "3", "退热镇痛"},
                    {"连花清瘟胶囊", "0.35g*48粒", "盒", "28.00", "4粒", "TID", "口服", "5", "中成药，清瘟解毒"}
                });

        // ===== 消化系统疾病 =====
        register("腹痛,腹泻,恶心,呕吐,发热",
                "K52.9", "急性胃肠炎",
                "急性胃肠炎",
                "患者因腹痛、腹泻伴恶心就诊。",
                "饮食不洁或病毒感染引起的急性胃肠道炎症，表现为腹痛、腹泻、恶心呕吐。",
                "无特殊既往史。",
                "腹部压痛, 无反跳痛, 肠鸣音亢进。",
                "1. 饮食调整：清淡流质饮食\n2. 补液治疗：口服补液盐\n3. 止泻治疗\n4. 如有细菌感染证据可使用抗生素\n5. 注意脱水情况",
                "根据《中国急性感染性腹泻诊疗指南(2023)》：\n" +
                "• 轻中度脱水首选口服补液盐\n" +
                "• 蒙脱石散可缩短腹泻病程\n" +
                "• 抗生素仅适用于细菌性腹泻\n" +
                "• 益生菌可辅助治疗",
                new String[][]{
                    {"蒙脱石散", "3g*10袋", "盒", "18.00", "1袋", "TID", "口服", "3", "止泻，保护肠黏膜"},
                    {"诺氟沙星胶囊", "0.1g*24粒", "盒", "12.00", "2粒", "TID", "口服", "3", "喹诺酮类抗生素，用于细菌性腹泻"},
                    {"口服补液盐III", "14.75g*6袋", "盒", "15.00", "1袋", "TID", "口服", "3", "补充电解质，预防脱水"},
                    {"双歧杆菌四联活菌片", "0.5g*24片", "盒", "35.00", "3片", "TID", "口服", "7", "调节肠道菌群"}
                });

        register("胃痛,反酸,嗳气,上腹痛,腹胀",
                "K25.9", "胃溃疡",
                "消化性溃疡（胃溃疡）",
                "患者因反复胃痛、反酸就诊。",
                "胃酸分泌过多、幽门螺杆菌感染等因素导致的胃黏膜损伤，形成溃疡。",
                "既往胃病史。",
                "上腹部压痛, 无反跳痛。",
                "1. 抑酸治疗\n2. 保护胃黏膜\n3. 抗幽门螺杆菌（如阳性）\n4. 规律饮食，忌辛辣刺激\n5. 复查胃镜",
                "根据《消化性溃疡诊疗指南(2022)》：\n" +
                "• PPI为首选抑酸药物，疗程4-8周\n" +
                "• Hp阳性者需行根除治疗（四联疗法）\n" +
                "• 铝碳酸镁可中和胃酸、保护黏膜\n" +
                "• 避免NSAIDs、酒精等损伤因素",
                new String[][]{
                    {"奥美拉唑肠溶胶囊", "20mg*14粒", "盒", "25.00", "1粒", "BID", "口服", "14", "质子泵抑制剂，强效抑酸"},
                    {"铝碳酸镁片", "0.5g*30片", "盒", "28.00", "2片", "TID", "口服", "14", "中和胃酸，保护胃黏膜"},
                    {"阿莫西林胶囊", "0.5g*24粒", "盒", "15.50", "1粒", "BID", "口服", "14", "抗Hp感染（四联疗法）"},
                    {"克拉霉素片", "0.25g*6片", "盒", "22.00", "1片", "BID", "口服", "14", "抗Hp感染（四联疗法）"}
                });

        // ===== 心血管疾病 =====
        register("头晕,心悸,血压高,头痛,颈项僵硬",
                "I10", "原发性高血压",
                "原发性高血压",
                "患者因头晕、心悸就诊，测血压偏高。",
                "原发性高血压，可能与遗传、饮食、精神紧张等因素有关。",
                "高血压病史。",
                "BP 160/100mmHg, HR 88次/分。",
                "1. 生活方式干预：低盐饮食、戒烟限酒、适当运动\n2. 降压药物治疗\n3. 监测血压\n4. 定期随访",
                "根据《中国高血压防治指南(2023年修订版)》：\n" +
                "• 一般高血压目标<140/90mmHg\n" +
                "• 首选ACEI/ARB、CCB或利尿剂\n" +
                "• 联合用药优先推荐ACEI/ARB+CCB\n" +
                "• 低盐饮食（<6g/d），控制体重",
                new String[][]{
                    {"硝苯地平控释片", "30mg*7片", "盒", "32.00", "1片", "QD", "口服", "28", "CCB类降压药，平稳降压"},
                    {"缬沙坦胶囊", "80mg*7粒", "盒", "28.00", "1粒", "QD", "口服", "28", "ARB类降压药，保护靶器官"},
                    {"阿司匹林肠溶片", "100mg*30片", "盒", "15.00", "1片", "QD", "口服", "30", "抗血小板，预防心脑血管事件"}
                });

        // ===== 骨科疾病 =====
        register("腰痛,腿麻,活动受限,腰腿痛",
                "M54.5", "腰椎间盘突出症",
                "腰椎间盘突出症",
                "患者因腰痛伴下肢麻木就诊。",
                "腰椎间盘退变、纤维环破裂、髓核突出压迫神经根，引起腰腿痛。",
                "长期伏案工作。",
                "腰椎压痛, 直腿抬高试验阳性, 下肢感觉减退。",
                "1. 卧床休息\n2. 药物治疗：NSAIDs+肌肉松弛剂\n3. 物理治疗\n4. 避免久坐、弯腰\n5. 严重者考虑手术",
                "根据《腰椎间盘突出症诊疗指南(2020)》：\n" +
                "• 首选保守治疗，80-90%患者可缓解\n" +
                "• NSAIDs为一线镇痛药物\n" +
                "• 肌松剂可缓解肌肉痉挛\n" +
                "• 急性期卧床休息2-3天\n" +
                "• 保守治疗3个月无效考虑手术",
                new String[][]{
                    {"双氯芬酸钠缓释片", "75mg*20片", "盒", "28.00", "1片", "BID", "口服", "7", "NSAIDs，抗炎镇痛"},
                    {"甲钴胺片", "0.5mg*20片", "盒", "22.00", "1片", "TID", "口服", "14", "营养神经，促进神经修复"},
                    {"盐酸乙哌立松片", "50mg*20片", "盒", "25.00", "1片", "TID", "口服", "7", "肌肉松弛剂，缓解肌肉痉挛"}
                });

        // ===== 儿科疾病 =====
        register("小孩,发热,咳嗽,流涕,小儿",
                "J06.9", "小儿急性上呼吸道感染",
                "小儿急性上呼吸道感染",
                "患儿因发热、咳嗽、流涕就诊。",
                "小儿上呼吸道感染，多由病毒感染引起。",
                "无特殊既往史。",
                "T 38.5℃, 咽部充血, 扁桃体I°肿大。",
                "1. 对症治疗为主\n2. 退热：体温>38.5℃使用退热药\n3. 多饮水\n4. 注意观察精神状态",
                "根据《儿童急性上呼吸道感染诊疗指南(2023)》：\n" +
                "• 不推荐常规使用抗菌药物\n" +
                "• 退热推荐对乙酰氨基酚或布洛芬\n" +
                "• 禁用阿司匹林（Reye综合征风险）\n" +
                "• 中成药可辅助治疗",
                new String[][]{
                    {"阿莫西林颗粒", "0.125g*12袋", "盒", "18.00", "1袋", "TID", "口服", "5", "青霉素类，用于细菌感染"},
                    {"布洛芬混悬液", "100ml", "瓶", "25.00", "5ml", "PRN", "口服", "3", "退热，体温>38.5℃使用"},
                    {"小儿氨酚黄那敏颗粒", "10袋", "盒", "15.00", "1袋", "TID", "口服", "5", "缓解感冒症状"},
                    {"开喉剑喷雾剂(儿童型)", "20ml", "瓶", "28.00", "适量", "TID", "喷喉", "5", "清热解毒，缓解咽痛"}
                });

        // ===== 皮肤科疾病 =====
        register("皮疹,瘙痒,红斑,过敏,荨麻疹",
                "L30.9", "过敏性皮炎",
                "过敏性皮炎",
                "患者因皮疹、瘙痒就诊。",
                "接触过敏原引起的皮肤过敏反应，表现为红斑、丘疹、瘙痒。",
                "过敏体质。",
                "皮肤可见散在红色丘疹, 部分抓痕。",
                "1. 避免接触过敏原\n2. 抗组胺药物\n3. 外用糖皮质激素\n4. 严重者口服激素",
                "根据《中国荨麻疹诊疗指南(2022)》：\n" +
                "• 第二代抗组胺药为首选\n" +
                "• 外用糖皮质激素用于局部皮损\n" +
                "• 避免搔抓、热水烫洗\n" +
                "• 寻找并避免过敏原",
                new String[][]{
                    {"氯雷他定片", "10mg*6片", "盒", "18.00", "1片", "QD", "口服", "7", "第二代抗组胺药，止痒抗过敏"},
                    {"炉甘石洗剂", "100ml", "瓶", "12.00", "适量", "TID", "外用", "7", "收敛止痒"},
                    {"醋酸地塞米松乳膏", "10g", "支", "8.00", "适量", "BID", "外用", "7", "糖皮质激素，抗炎止痒"}
                });

        // ===== 眼科疾病 =====
        register("眼睛红,眼痛,视力模糊,眼痒,结膜炎",
                "H10.9", "急性结膜炎",
                "急性结膜炎（红眼病）",
                "患者因眼红、眼痛就诊。",
                "细菌或病毒感染引起的结膜炎症，表现为结膜充血、分泌物增多。",
                "无特殊既往史。",
                "结膜充血, 分泌物增多。",
                "1. 局部抗感染治疗\n2. 注意眼部卫生\n3. 避免揉眼\n4. 避免传染他人",
                "根据《中国结膜炎诊疗指南(2021)》：\n" +
                "• 细菌性结膜炎首选局部抗生素\n" +
                "• 病毒性结膜炎以对症治疗为主\n" +
                "• 避免使用激素类眼药水\n" +
                "• 注意手卫生，防止交叉感染",
                new String[][]{
                    {"左氧氟沙星滴眼液", "5ml", "支", "15.00", "1滴", "QID", "滴眼", "7", "喹诺酮类，抗细菌感染"},
                    {"更昔洛韦眼用凝胶", "5g", "支", "22.00", "适量", "TID", "涂眼", "7", "抗病毒，用于病毒性结膜炎"},
                    {"玻璃酸钠滴眼液", "10ml", "瓶", "35.00", "1滴", "TID", "滴眼", "14", "人工泪液，缓解眼干"}
                });

        // ===== 内分泌疾病 =====
        register("口渴,多饮,多尿,消瘦,乏力",
                "E11.9", "2型糖尿病",
                "2型糖尿病",
                "患者因口渴、多饮、多尿就诊。",
                "胰岛素抵抗和/或胰岛素分泌缺陷引起的代谢性疾病。",
                "糖尿病家族史。",
                "BMI 26, 空腹血糖 12.5mmol/L。",
                "1. 饮食控制\n2. 运动治疗\n3. 药物治疗\n4. 血糖监测\n5. 糖尿病教育",
                "根据《中国2型糖尿病防治指南(2020年版)》：\n" +
                "• 二甲双胍为一线用药\n" +
                "• HbA1c目标<7%\n" +
                "• 生活方式干预为基础治疗\n" +
                "• 定期监测血糖、并发症筛查",
                new String[][]{
                    {"盐酸二甲双胍片", "0.5g*20片", "盒", "15.00", "1片", "BID", "口服", "30", "双胍类降糖药，一线用药"},
                    {"格列美脲片", "2mg*15片", "盒", "25.00", "1片", "QD", "口服", "30", "磺脲类促泌剂"},
                    {"阿卡波糖片", "50mg*30片", "盒", "45.00", "1片", "TID", "口服", "30", "α-糖苷酶抑制剂，降低餐后血糖"}
                });
    }

    private static void register(String keywords, String icdCode, String diagnosisName,
                                  String title, String chiefComplaint, String presentIllness,
                                  String pastHistory, String physicalExam, String treatmentPlan,
                                  String guideline, String[][] drugs) {
        AiDiagnosisResult result = new AiDiagnosisResult();
        result.setIcdCode(icdCode);
        result.setDiagnosisName(diagnosisName);
        result.setTitle(title);
        result.setChiefComplaint(chiefComplaint);
        result.setPresentIllness(presentIllness);
        result.setPastHistory(pastHistory);
        result.setPhysicalExam(physicalExam);
        result.setTreatmentPlan(treatmentPlan);
        result.setGuideline(guideline);

        List<Map<String, Object>> drugList = new ArrayList<>();
        for (String[] drug : drugs) {
            Map<String, Object> d = new HashMap<>();
            d.put("drugName", drug[0]);
            d.put("drugSpec", drug[1]);
            d.put("unit", drug[2]);
            d.put("unitPrice", drug[3]);
            d.put("dosage", drug[4]);
            d.put("frequency", drug[5]);
            d.put("usageMethod", drug[6]);
            d.put("days", drug[7]);
            d.put("description", drug[8]);
            drugList.add(d);
        }
        result.setDrugs(drugList);

        // 存储到知识库
        for (String keyword : keywords.split(",")) {
            DISEASE_DB.put(keyword.trim(), result);
        }
    }

    /**
     * AI诊断
     */
    public AiDiagnosisResult diagnose(String chiefComplaint) {
        if (chiefComplaint == null || chiefComplaint.trim().isEmpty()) {
            return getDefaultResult(chiefComplaint);
        }

        String complaint = chiefComplaint.toLowerCase();
        Map<String, Integer> scoreMap = new HashMap<>();
        Map<String, AiDiagnosisResult> resultMap = new HashMap<>();

        // 关键词匹配评分
        for (Map.Entry<String, AiDiagnosisResult> entry : DISEASE_DB.entrySet()) {
            String keyword = entry.getKey();
            if (complaint.contains(keyword)) {
                String key = entry.getValue().getDiagnosisName();
                scoreMap.merge(key, 1, Integer::sum);
                resultMap.put(key, entry.getValue());
            }
        }

        // 返回得分最高的诊断
        AiDiagnosisResult best = scoreMap.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(e -> resultMap.get(e.getKey()))
                .orElse(getDefaultResult(chiefComplaint));

        // 计算置信度
        int maxScore = scoreMap.values().stream().mapToInt(Integer::intValue).max().orElse(0);
        best.setConfidence(Math.min(0.95, 0.5 + maxScore * 0.15));

        return best;
    }

    private AiDiagnosisResult getDefaultResult(String chiefComplaint) {
        AiDiagnosisResult result = new AiDiagnosisResult();
        result.setIcdCode("R69");
        result.setDiagnosisName("待查");
        result.setTitle("待明确诊断");
        result.setChiefComplaint(chiefComplaint != null ? chiefComplaint : "");
        result.setPresentIllness("患者主诉" + (chiefComplaint != null ? chiefComplaint : "") + "，待进一步检查明确诊断。");
        result.setPastHistory("待补充。");
        result.setPhysicalExam("待补充。");
        result.setTreatmentPlan("1. 完善相关检查\n2. 对症治疗\n3. 随诊观察");
        result.setGuideline("症状不典型，建议完善相关检查后明确诊断。");
        result.setDrugs(new ArrayList<>());
        result.setConfidence(0.2);
        return result;
    }
}
