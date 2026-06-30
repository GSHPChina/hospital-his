package com.his.system.controller;

import com.his.common.result.R;
import com.his.common.security.JwtUtils;
import com.his.system.dto.LoginDTO;
import com.his.system.entity.SysUser;
import com.his.system.service.SysMenuService;
import com.his.system.service.SysUserService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final SysUserService userService;
    private final SysMenuService menuService;

    @PostMapping("/login")
    public R<Map<String, Object>> login(@Valid @RequestBody LoginDTO dto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        SysUser user = userService.getByUsername(dto.getUsername());
        if (user == null || user.getStatus() != 1) {
            return R.fail("用户不存在或已被禁用");
        }

        String token = jwtUtils.generateToken(user.getId(), user.getUsername());

        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("userId", user.getId());
        result.put("username", user.getUsername());
        result.put("realName", user.getRealName());

        return R.ok(result);
    }

    @GetMapping("/info")
    public R<Map<String, Object>> getUserInfo() {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String, Object> info = userService.getUserInfo(userId);
        return R.ok(info);
    }

    @PostMapping("/logout")
    public R<Void> logout() {
        SecurityContextHolder.clearContext();
        return R.ok();
    }
}
