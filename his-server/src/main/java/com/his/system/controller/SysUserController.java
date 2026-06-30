package com.his.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.his.common.result.R;
import com.his.system.dto.UserDTO;
import com.his.system.entity.SysUser;
import com.his.system.service.SysUserService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/system/users")
@RequiredArgsConstructor
public class SysUserController {

    private final SysUserService userService;

    @GetMapping
    public R<List<SysUser>> list(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long deptId) {
        List<SysUser> users = userService.listWithDept();
        // 过滤
        if (StringUtils.hasText(keyword)) {
            users = users.stream()
                    .filter(u -> u.getRealName().contains(keyword) || u.getUsername().contains(keyword))
                    .collect(java.util.stream.Collectors.toList());
        }
        if (deptId != null) {
            users = users.stream()
                    .filter(u -> deptId.equals(u.getDeptId()))
                    .collect(java.util.stream.Collectors.toList());
        }
        users.forEach(u -> u.setPassword(null));
        return R.ok(users);
    }

    @PostMapping
    public R<Void> add(@Valid @RequestBody UserDTO dto) {
        userService.addUser(dto);
        return R.ok();
    }

    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @Valid @RequestBody UserDTO dto) {
        dto.setId(id);
        userService.updateUser(dto);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return R.ok();
    }

    @PutMapping("/{id}/status")
    public R<Void> updateStatus(@PathVariable Long id, @RequestParam Integer status) {
        SysUser user = new SysUser();
        user.setId(id);
        user.setStatus(status);
        userService.updateById(user);
        return R.ok();
    }
}
