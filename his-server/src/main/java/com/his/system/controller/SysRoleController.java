package com.his.system.controller;

import com.his.common.result.R;
import com.his.system.entity.SysRole;
import com.his.system.service.SysRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/system/roles")
@RequiredArgsConstructor
public class SysRoleController {

    private final SysRoleService roleService;

    @GetMapping
    public R<List<SysRole>> list() {
        return R.ok(roleService.list());
    }

    @PostMapping
    public R<Void> add(@RequestBody SysRole role) {
        roleService.save(role);
        return R.ok();
    }

    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @RequestBody SysRole role) {
        role.setId(id);
        roleService.updateById(role);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        roleService.removeById(id);
        return R.ok();
    }
}
