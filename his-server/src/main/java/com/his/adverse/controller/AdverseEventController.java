package com.his.adverse.controller;

import cn.hutool.core.util.IdUtil;
import com.his.adverse.entity.AdverseEvent;
import com.his.adverse.entity.AdverseEventLog;
import com.his.adverse.service.AdverseEventService;
import com.his.common.result.R;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/adverse")
@RequiredArgsConstructor
public class AdverseEventController {

    private final AdverseEventService adverseEventService;

    @GetMapping("/events")
    public R<List<AdverseEvent>> listEvents(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Integer level,
            @RequestParam(required = false) Integer status) {
        return R.ok(adverseEventService.listWithNames(type, level, status));
    }

    @PostMapping("/events")
    public R<Void> reportEvent(@RequestBody AdverseEvent event) {
        Long reporterId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        event.setEventNo("AE" + java.util.UUID.randomUUID().toString().replace("-", "").substring(0, 12));
        event.setReporterId(reporterId);
        event.setStatus(1);
        event.setReportTime(LocalDateTime.now());
        adverseEventService.save(event);
        return R.ok();
    }

    @GetMapping("/events/{id}")
    public R<Map<String, Object>> getEventDetail(@PathVariable Long id) {
        AdverseEvent event = adverseEventService.getDetailWithLogs(id);
        List<AdverseEventLog> logs = adverseEventService.getLogs(id);
        Map<String, Object> result = new HashMap<>();
        result.put("event", event);
        result.put("logs", logs);
        return R.ok(result);
    }

    @PutMapping("/events/{id}/handle")
    public R<Void> handleEvent(@PathVariable Long id, @RequestBody Map<String, String> body) {
        Long handlerId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String handleResult = body.get("handleResult");
        String causeAnalysis = body.get("causeAnalysis");
        String improvement = body.get("improvement");
        adverseEventService.handle(id, handlerId, handleResult, causeAnalysis, improvement);
        return R.ok();
    }

    @GetMapping("/statistics")
    public R<Map<String, Object>> getStatistics() {
        Map<String, Object> statistics = new HashMap<>();
        statistics.put("byType", adverseEventService.getStatisticsByType());
        statistics.put("byLevel", adverseEventService.getStatisticsByLevel());
        return R.ok(statistics);
    }
}
