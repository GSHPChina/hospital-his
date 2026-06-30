package com.his.outpatient.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.his.common.exception.BusinessException;
import com.his.outpatient.entity.OpRegister;
import com.his.outpatient.entity.OpSchedule;
import com.his.outpatient.mapper.OpRegisterMapper;
import com.his.outpatient.mapper.OpScheduleMapper;
import com.his.outpatient.service.OpRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OpRegisterServiceImpl extends ServiceImpl<OpRegisterMapper, OpRegister> implements OpRegisterService {

    private final OpScheduleMapper scheduleMapper;

    @Override
    public List<OpRegister> listWithDetails() {
        return baseMapper.selectAllWithDetails();
    }

    @Override
    public List<OpRegister> getTodayPatients(Long doctorId) {
        return baseMapper.selectTodayPatients(doctorId);
    }

    @Override
    @Transactional
    public void register(OpRegister register) {
        // 生成挂号单号
        register.setRegisterNo("REG" + IdUtil.getSnowflakeNextIdStr());
        register.setStatus(1);
        register.setRegisterTime(LocalDateTime.now());

        // 计算挂号费
        BigDecimal fee;
        switch (register.getRegisterType()) {
            case "EXPERT":
                fee = new BigDecimal("50.00");
                break;
            case "EMERGENCY":
                fee = new BigDecimal("30.00");
                break;
            default:
                fee = new BigDecimal("15.00");
                break;
        }
        register.setFee(fee);

        // 更新排班已用号源
        if (register.getScheduleId() != null) {
            OpSchedule schedule = scheduleMapper.selectById(register.getScheduleId());
            if (schedule != null) {
                if (schedule.getUsedNum() >= schedule.getTotalNum()) {
                    throw new BusinessException("该时段号源已满");
                }
                schedule.setUsedNum(schedule.getUsedNum() + 1);
                scheduleMapper.updateById(schedule);
            }
        }

        save(register);
    }

    @Override
    @Transactional
    public void cancelRegister(Long id) {
        OpRegister register = getById(id);
        if (register == null) {
            throw new BusinessException("挂号记录不存在");
        }
        if (register.getStatus() != 1) {
            throw new BusinessException("当前状态不允许退号");
        }

        register.setStatus(3);
        register.setCancelTime(LocalDateTime.now());
        updateById(register);

        // 释放号源
        if (register.getScheduleId() != null) {
            OpSchedule schedule = scheduleMapper.selectById(register.getScheduleId());
            if (schedule != null) {
                schedule.setUsedNum(Math.max(0, schedule.getUsedNum() - 1));
                scheduleMapper.updateById(schedule);
            }
        }
    }
}
