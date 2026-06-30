package com.his.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.his.system.dto.UserDTO;
import com.his.system.entity.SysUser;

import java.util.List;
import java.util.Map;

public interface SysUserService extends IService<SysUser> {
    SysUser getByUsername(String username);
    List<SysUser> listWithDept();
    void addUser(UserDTO dto);
    void updateUser(UserDTO dto);
    void deleteUser(Long id);
    Map<String, Object> getUserInfo(Long userId);
}
