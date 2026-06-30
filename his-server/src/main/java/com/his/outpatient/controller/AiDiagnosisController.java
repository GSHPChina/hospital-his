package com.his.outpatient.controller;

import com.his.common.result.R;
import com.his.outpatient.dto.AiDiagnosisResult;
import com.his.outpatient.service.AiDiagnosisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/doctor/ai")
@RequiredArgsConstructor
public class AiDiagnosisController {

    private final AiDiagnosisService aiDiagnosisService;

    /**
     * AI诊断接口
     * 根据患者主诉，返回诊断建议、病历模板、处方建议
     */
    @PostMapping("/diagnose")
    public R<AiDiagnosisResult> diagnose(@RequestBody java.util.Map<String, String> params) {
        String chiefComplaint = params.get("chiefComplaint");
        AiDiagnosisResult result = aiDiagnosisService.diagnose(chiefComplaint);
        return R.ok(result);
    }
}
