package com.his.system.controller;

import com.his.common.result.R;
import com.his.system.entity.SysDict;
import com.his.system.entity.SysDictItem;
import com.his.system.service.SysDictService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/system/dicts")
@RequiredArgsConstructor
public class SysDictController {

    private final SysDictService dictService;

    @GetMapping
    public R<List<SysDict>> list() {
        return R.ok(dictService.list());
    }

    @GetMapping("/{code}/items")
    public R<List<SysDictItem>> getItems(@PathVariable String code) {
        return R.ok(dictService.getDictItems(code));
    }

    @PostMapping
    public R<Void> add(@RequestBody SysDict dict) {
        dictService.save(dict);
        return R.ok();
    }

    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @RequestBody SysDict dict) {
        dict.setId(id);
        dictService.updateById(dict);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        dictService.removeById(id);
        return R.ok();
    }
}
