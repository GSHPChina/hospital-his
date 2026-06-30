package com.his.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.his.system.entity.SysRole;
import com.his.system.mapper.SysRoleMapper;
import com.his.system.service.SysRoleService;
import org.springframework.stereotype.Service;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
}
