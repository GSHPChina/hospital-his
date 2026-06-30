package com.his.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.his.system.entity.SysUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Delete;

@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    @Delete("DELETE FROM sys_user_role WHERE user_id = #{userId}")
    int deleteByUserId(@Param("userId") Long userId);
}
