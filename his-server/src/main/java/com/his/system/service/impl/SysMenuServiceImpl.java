package com.his.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.his.system.entity.SysMenu;
import com.his.system.mapper.SysMenuMapper;
import com.his.system.service.SysMenuService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Override
    public List<SysMenu> getUserMenus(Long userId) {
        List<SysMenu> menus = baseMapper.selectMenusByUserId(userId);
        return buildTree(menus, 0L);
    }

    @Override
    public List<SysMenu> listTree() {
        List<SysMenu> allMenus = list();
        return buildTree(allMenus, 0L);
    }

    private List<SysMenu> buildTree(List<SysMenu> menus, Long parentId) {
        return menus.stream()
                .filter(m -> parentId.equals(m.getParentId()))
                .peek(m -> m.setChildren(buildTree(menus, m.getId())))
                .collect(Collectors.toList());
    }
}
