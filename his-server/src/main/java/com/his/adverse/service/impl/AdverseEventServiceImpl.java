package com.his.adverse.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.his.adverse.entity.AdverseEvent;
import com.his.adverse.entity.AdverseEventLog;
import com.his.adverse.mapper.AdverseEventLogMapper;
import com.his.adverse.mapper.AdverseEventMapper;
import com.his.adverse.service.AdverseEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdverseEventServiceImpl extends ServiceImpl<AdverseEventMapper, AdverseEvent>
        implements AdverseEventService {

    private final AdverseEventLogMapper logMapper;

    @Override
    @Transactional
    public void handle(Long id, Long handlerId, String handleResult, String causeAnalysis, String improvement) {
        AdverseEvent event = getById(id);
        if (event == null) {
            throw new RuntimeException("Adverse event not found with id: " + id);
        }

        event.setStatus(3);
        event.setHandlerId(handlerId);
        event.setHandleTime(LocalDateTime.now());
        event.setHandleResult(handleResult);
        event.setCauseAnalysis(causeAnalysis);
        event.setImprovement(improvement);
        updateById(event);

        AdverseEventLog log = new AdverseEventLog();
        log.setEventId(id);
        log.setOperatorId(handlerId);
        log.setAction("handle");
        log.setContent("handleResult: " + handleResult + ", causeAnalysis: " + causeAnalysis + ", improvement: " + improvement);
        logMapper.insert(log);
    }

    @Override
    public List<AdverseEvent> listWithNames(String type, Integer level, Integer status) {
        return baseMapper.selectAllWithNames(type, level, status);
    }

    @Override
    public AdverseEvent getDetailWithLogs(Long id) {
        return baseMapper.selectOneWithNames(id);
    }

    @Override
    public List<AdverseEventLog> getLogs(Long eventId) {
        LambdaQueryWrapper<AdverseEventLog> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AdverseEventLog::getEventId, eventId);
        wrapper.orderByDesc(AdverseEventLog::getCreateTime);
        return logMapper.selectList(wrapper);
    }

    @Override
    public List<Map<String, Object>> getStatisticsByType() {
        return baseMapper.selectMaps(
                new QueryWrapper<AdverseEvent>()
                        .select("type", "count(*) as count")
                        .groupBy("type")
        );
    }

    @Override
    public List<Map<String, Object>> getStatisticsByLevel() {
        return baseMapper.selectMaps(
                new QueryWrapper<AdverseEvent>()
                        .select("level", "count(*) as count")
                        .groupBy("level")
        );
    }
}
