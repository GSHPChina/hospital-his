package com.his.system.controller;

import com.his.common.result.R;
import com.his.system.entity.SysDept;
import com.his.system.service.SysDeptService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/system/depts")
@RequiredArgsConstructor
public class SysDeptController {

    private final SysDeptService deptService;

    @GetMapping("/tree")
    public R<List<SysDept>> tree() {
        return R.ok(deptService.listTree());
    }

    @GetMapping
    public R<List<SysDept>> list() {
        return R.ok(deptService.list());
    }

    @PostMapping
    public R<Void> add(@RequestBody SysDept dept) {
        deptService.save(dept);
        return R.ok();
    }

    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @RequestBody SysDept dept) {
        dept.setId(id);
        deptService.updateById(dept);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        deptService.removeById(id);
        return R.ok();
    }
}
