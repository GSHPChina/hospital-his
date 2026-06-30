package com.his.adverse.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.his.adverse.entity.AdverseEvent;
import com.his.adverse.entity.AdverseEventLog;

import java.util.List;
import java.util.Map;

public interface AdverseEventService extends IService<AdverseEvent> {

    void handle(Long id, Long handlerId, String handleResult, String causeAnalysis, String improvement);

    List<AdverseEvent> listWithNames(String type, Integer level, Integer status);

    AdverseEvent getDetailWithLogs(Long id);

    List<AdverseEventLog> getLogs(Long eventId);

    List<Map<String, Object>> getStatisticsByType();

    List<Map<String, Object>> getStatisticsByLevel();
}
