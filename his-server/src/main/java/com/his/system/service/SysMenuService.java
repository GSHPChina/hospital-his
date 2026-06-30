package com.his.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.his.system.entity.SysMenu;

import java.util.List;

public interface SysMenuService extends IService<SysMenu> {
    List<SysMenu> getUserMenus(Long userId);
    List<SysMenu> listTree();
}
