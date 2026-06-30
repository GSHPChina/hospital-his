package com.his.outpatient.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.his.common.exception.BusinessException;
import com.his.outpatient.entity.OpAppointment;
import com.his.outpatient.entity.OpQueue;
import com.his.outpatient.entity.OpRegister;
import com.his.outpatient.mapper.OpQueueMapper;
import com.his.outpatient.service.OpAppointmentService;
import com.his.outpatient.service.OpQueueService;
import com.his.outpatient.service.OpRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OpQueueServiceImpl extends ServiceImpl<OpQueueMapper, OpQueue> implements OpQueueService {

    private final OpAppointmentService appointmentService;
    private final OpRegisterService registerService;

    @Override
    public List<OpQueue> listToday() {
        return baseMapper.selectByDate(LocalDate.now());
    }

    @Override
    @Transactional
    public void addToQueue(Long appointmentId, Long registerId) {
        OpQueue queue = new OpQueue();
        queue.setQueueDate(LocalDate.now());
        queue.setStatus(1); // 1-等待中

        if (appointmentId != null) {
            OpAppointment appointment = appointmentService.getById(appointmentId);
            if (appointment == null) {
                throw new BusinessException("预约记录不存在");
            }
            queue.setAppointmentId(appointmentId);
            queue.setDoctorId(appointment.getDoctorId());
            queue.setPatientId(appointment.getPatientId());
        } else if (registerId != null) {
            OpRegister register = registerService.getById(registerId);
            if (register == null) {
                throw new BusinessException("挂号记录不存在");
            }
            queue.setRegisterId(registerId);
            queue.setDoctorId(register.getDoctorId());
            queue.setPatientId(register.getPatientId());
        } else {
            throw new BusinessException("预约ID或挂号ID不能为空");
        }

        // 生成排队号并分配
        List<OpQueue> existing = lambdaQuery()
                .eq(OpQueue::getDoctorId, queue.getDoctorId())
                .eq(OpQueue::getQueueDate, queue.getQueueDate())
                .list();
        int nextNo = existing.size() + 1;
        queue.setQueueNo(String.valueOf(nextNo));

        save(queue);
    }

    @Override
    @Transactional
    public void callNext(Long doctorId) {
        List<OpQueue> waiting = lambdaQuery()
                .eq(OpQueue::getDoctorId, doctorId)
                .eq(OpQueue::getQueueDate, LocalDate.now())
                .eq(OpQueue::getStatus, 1)
                .orderByAsc(OpQueue::getQueueNo)
                .list();

        if (waiting.isEmpty()) {
            throw new BusinessException("没有等待中的患者");
        }

        OpQueue next = waiting.get(0);
        next.setStatus(2); // 2-呼叫中
        next.setCallTime(LocalDateTime.now());
        updateById(next);
    }

    @Override
    @Transactional
    public void callPatient(Long id) {
        OpQueue queue = getById(id);
        if (queue == null) {
            throw new BusinessException("排队记录不存在");
        }
        if (queue.getStatus() != 1) {
            throw new BusinessException("当前状态不允许呼叫");
        }
        queue.setStatus(2); // 2-呼叫中
        queue.setCallTime(LocalDateTime.now());
        updateById(queue);
    }

    @Override
    @Transactional
    public void completePatient(Long id) {
        OpQueue queue = getById(id);
        if (queue == null) {
            throw new BusinessException("排队记录不存在");
        }
        if (queue.getStatus() != 2) {
            throw new BusinessException("当前状态不允许完成");
        }
        queue.setStatus(3); // 3-已完成
        queue.setEndTime(LocalDateTime.now());
        updateById(queue);
    }

    @Override
    @Transactional
    public void skipPatient(Long id) {
        OpQueue queue = getById(id);
        if (queue == null) {
            throw new BusinessException("排队记录不存在");
        }
        if (queue.getStatus() != 2) {
            throw new BusinessException("当前状态不允许跳过");
        }
        queue.setStatus(4); // 4-过号
        updateById(queue);
    }
}
