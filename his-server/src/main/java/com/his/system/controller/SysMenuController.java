package com.his.system.controller;

import com.his.common.result.R;
import com.his.system.entity.SysMenu;
import com.his.system.service.SysMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/system/menus")
@RequiredArgsConstructor
public class SysMenuController {

    private final SysMenuService menuService;

    @GetMapping("/tree")
    public R<List<SysMenu>> tree() {
        return R.ok(menuService.listTree());
    }

    @GetMapping
    public R<List<SysMenu>> list() {
        return R.ok(menuService.list());
    }

    @PostMapping
    public R<Void> add(@RequestBody SysMenu menu) {
        menuService.save(menu);
        return R.ok();
    }

    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @RequestBody SysMenu menu) {
        menu.setId(id);
        menuService.updateById(menu);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        menuService.removeById(id);
        return R.ok();
    }
}
