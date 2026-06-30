package com.his.common.security;

import com.his.system.entity.SysUser;
import com.his.system.mapper.SysUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final SysUserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = userMapper.selectByUsername(username);
        if (sysUser == null) {
            throw new UsernameNotFoundException("用户不存在: " + username);
        }
        if (sysUser.getStatus() != 1) {
            throw new UsernameNotFoundException("用户已被禁用: " + username);
        }

        return new User(
                sysUser.getUsername(),
                sysUser.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
}
