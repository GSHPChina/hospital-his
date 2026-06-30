package com.his.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.his.system.entity.SysDict;
import com.his.system.entity.SysDictItem;
import com.his.system.mapper.SysDictItemMapper;
import com.his.system.mapper.SysDictMapper;
import com.his.system.service.SysDictService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements SysDictService {

    private final SysDictItemMapper dictItemMapper;

    @Override
    public List<SysDictItem> getDictItems(String dictCode) {
        SysDict dict = getOne(new LambdaQueryWrapper<SysDict>().eq(SysDict::getCode, dictCode));
        if (dict == null) {
            return Collections.emptyList();
        }
        return dictItemMapper.selectList(
                new LambdaQueryWrapper<SysDictItem>()
                        .eq(SysDictItem::getDictId, dict.getId())
                        .eq(SysDictItem::getStatus, 1)
                        .orderByAsc(SysDictItem::getSortOrder)
        );
    }
}
