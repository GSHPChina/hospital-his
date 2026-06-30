package com.his.outpatient.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.his.common.exception.BusinessException;
import com.his.outpatient.entity.OpAppointment;
import com.his.outpatient.entity.OpSchedule;
import com.his.outpatient.mapper.OpAppointmentMapper;
import com.his.outpatient.mapper.OpScheduleMapper;
import com.his.outpatient.service.OpAppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OpAppointmentServiceImpl extends ServiceImpl<OpAppointmentMapper, OpAppointment> implements OpAppointmentService {

    private final OpScheduleMapper scheduleMapper;

    @Override
    public List<OpAppointment> listWithDetails() {
        return baseMapper.selectAllWithDetails();
    }

    @Override
    @Transactional
    public void book(OpAppointment appointment) {
        // 检查排班号源
        if (appointment.getScheduleId() != null) {
            OpSchedule schedule = scheduleMapper.selectById(appointment.getScheduleId());
            if (schedule == null) {
                throw new BusinessException("排班不存在");
            }
            if (schedule.getUsedNum() >= schedule.getTotalNum()) {
                throw new BusinessException("该时段号源已满");
            }
            schedule.setUsedNum(schedule.getUsedNum() + 1);
            scheduleMapper.updateById(schedule);
        }

        // 生成预约单号
        appointment.setAppointmentNo("APT" + IdUtil.getSnowflakeNextIdStr());
        appointment.setStatus(1); // 1-已预约

        // 计算费用
        if (appointment.getFee() == null) {
            appointment.setFee(new BigDecimal("0.00"));
        }

        // 分配排队号
        List<OpAppointment> existing = lambdaQuery()
                .eq(OpAppointment::getDoctorId, appointment.getDoctorId())
                .eq(OpAppointment::getAppointmentDate, appointment.getAppointmentDate())
                .list();
        appointment.setQueueNo(existing.size() + 1);

        save(appointment);
    }

    @Override
    @Transactional
    public void checkIn(Long id) {
        OpAppointment appointment = getById(id);
        if (appointment == null) {
            throw new BusinessException("预约记录不存在");
        }
        if (appointment.getStatus() != 1) {
            throw new BusinessException("当前状态不允许签到");
        }
        appointment.setStatus(2); // 2-已签到
        updateById(appointment);
    }

    @Override
    @Transactional
    public void cancel(Long id, String reason) {
        OpAppointment appointment = getById(id);
        if (appointment == null) {
            throw new BusinessException("预约记录不存在");
        }
        if (appointment.getStatus() != 1) {
            throw new BusinessException("当前状态不允许取消");
        }
        appointment.setStatus(3); // 3-已取消
        appointment.setCancelReason(reason);
        updateById(appointment);

        // 释放号源
        if (appointment.getScheduleId() != null) {
            OpSchedule schedule = scheduleMapper.selectById(appointment.getScheduleId());
            if (schedule != null) {
                schedule.setUsedNum(Math.max(0, schedule.getUsedNum() - 1));
                scheduleMapper.updateById(schedule);
            }
        }
    }

    @Override
    public List<OpAppointment> listByDoctorAndDate(Long doctorId, LocalDate date) {
        return baseMapper.selectByDoctorAndDate(doctorId, date);
    }
}
