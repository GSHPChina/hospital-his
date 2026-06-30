package com.his.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.his.system.entity.SysDept;

import java.util.List;

public interface SysDeptService extends IService<SysDept> {
    List<SysDept> listTree();
}
