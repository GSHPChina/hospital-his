package com.his.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.his.common.exception.BusinessException;
import com.his.system.dto.UserDTO;
import com.his.system.entity.SysMenu;
import com.his.system.entity.SysUser;
import com.his.system.entity.SysUserRole;
import com.his.system.mapper.SysMenuMapper;
import com.his.system.mapper.SysUserMapper;
import com.his.system.mapper.SysUserRoleMapper;
import com.his.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final SysUserMapper userMapper;
    private final SysUserRoleMapper userRoleMapper;
    private final SysMenuMapper menuMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public SysUser getByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    @Override
    public List<SysUser> listWithDept() {
        return userMapper.selectAllWithDept();
    }

    @Override
    @Transactional
    public void addUser(UserDTO dto) {
        SysUser existUser = userMapper.selectByUsername(dto.getUsername());
        if (existUser != null) {
            throw new BusinessException("用户名已存在");
        }

        SysUser user = new SysUser();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword() != null ? dto.getPassword() : "123456"));
        user.setRealName(dto.getRealName());
        user.setGender(dto.getGender());
        user.setPhone(dto.getPhone());
        user.setIdCard(dto.getIdCard());
        user.setDeptId(dto.getDeptId());
        user.setPost(dto.getPost());
        user.setStatus(dto.getStatus() != null ? dto.getStatus() : 1);
        userMapper.insert(user);

        saveUserRoles(user.getId(), dto.getRoleIds());
    }

    @Override
    @Transactional
    public void updateUser(UserDTO dto) {
        SysUser user = userMapper.selectById(dto.getId());
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        user.setRealName(dto.getRealName());
        user.setGender(dto.getGender());
        user.setPhone(dto.getPhone());
        user.setIdCard(dto.getIdCard());
        user.setDeptId(dto.getDeptId());
        user.setPost(dto.getPost());
        if (dto.getStatus() != null) {
            user.setStatus(dto.getStatus());
        }
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        userMapper.updateById(user);

        userRoleMapper.deleteByUserId(user.getId());
        saveUserRoles(user.getId(), dto.getRoleIds());
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userMapper.deleteById(id);
        userRoleMapper.deleteByUserId(id);
    }

    @Override
    public Map<String, Object> getUserInfo(Long userId) {
        SysUser user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        user.setPassword(null);

        List<SysMenu> menus = menuMapper.selectMenusByUserId(userId);

        Map<String, Object> result = new HashMap<>();
        result.put("user", user);
        result.put("menus", menus);
        return result;
    }

    private void saveUserRoles(Long userId, List<Long> roleIds) {
        if (!CollectionUtils.isEmpty(roleIds)) {
            for (Long roleId : roleIds) {
                SysUserRole userRole = new SysUserRole();
                userRole.setUserId(userId);
                userRole.setRoleId(roleId);
                userRoleMapper.insert(userRole);
            }
        }
    }
}
