package com.his.hr.controller;

import com.his.common.result.R;
import com.his.hr.entity.HrAttendance;
import com.his.hr.entity.HrEmployee;
import com.his.hr.entity.HrSalary;
import com.his.hr.service.HrAttendanceService;
import com.his.hr.service.HrEmployeeService;
import com.his.hr.service.HrSalaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/hr")
@RequiredArgsConstructor
public class HrController {

    private final HrEmployeeService employeeService;
    private final HrAttendanceService attendanceService;
    private final HrSalaryService salaryService;

    @GetMapping("/employees")
    public R<List<HrEmployee>> listEmployees() {
        return R.ok(employeeService.list());
    }

    @PostMapping("/employees")
    public R<Void> addEmployee(@RequestBody HrEmployee employee) {
        employeeService.save(employee);
        return R.ok();
    }

    @PutMapping("/employees/{id}")
    public R<Void> updateEmployee(@PathVariable Long id, @RequestBody HrEmployee employee) {
        employee.setId(id);
        employeeService.updateById(employee);
        return R.ok();
    }

    @GetMapping("/attendance")
    public R<List<HrAttendance>> listAttendance(
            @RequestParam(required = false) Long empId,
            @RequestParam(required = false) String date) {
        return R.ok(attendanceService.listByDate(empId, date));
    }

    @PostMapping("/attendance/checkin")
    public R<Void> checkIn(@RequestBody HrAttendance attendance) {
        attendance.setCheckInTime(LocalDateTime.now());
        attendance.setStatus(0);
        attendanceService.save(attendance);
        return R.ok();
    }

    @PostMapping("/attendance/checkout")
    public R<Void> checkOut(@RequestBody HrAttendance attendance) {
        attendance.setCheckOutTime(LocalDateTime.now());
        attendance.setStatus(1);
        attendanceService.updateById(attendance);
        return R.ok();
    }

    @GetMapping("/salary")
    public R<List<HrSalary>> listSalary(@RequestParam(required = false) String ym) {
        return R.ok(salaryService.listByMonth(ym));
    }

    @PostMapping("/salary")
    public R<Void> addSalary(@RequestBody HrSalary salary) {
        salary.setStatus(0);
        salaryService.save(salary);
        return R.ok();
    }

    @PutMapping("/salary/{id}/pay")
    public R<Void> paySalary(@PathVariable Long id) {
        HrSalary salary = new HrSalary();
        salary.setId(id);
        salary.setStatus(1);
        salary.setPayTime(LocalDateTime.now());
        salaryService.updateById(salary);
        return R.ok();
    }
}
