package com.his.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.his.system.entity.SysDict;
import com.his.system.entity.SysDictItem;

import java.util.List;

public interface SysDictService extends IService<SysDict> {
    List<SysDictItem> getDictItems(String dictCode);
}
