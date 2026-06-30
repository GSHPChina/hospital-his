package com.his.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.his.system.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    @Select("SELECT u.*, d.name as dept_name FROM sys_user u LEFT JOIN sys_dept d ON u.dept_id = d.id WHERE u.username = #{username}")
    SysUser selectByUsername(@Param("username") String username);

    @Select("SELECT u.*, d.name as dept_name FROM sys_user u LEFT JOIN sys_dept d ON u.dept_id = d.id ORDER BY u.create_time DESC")
    List<SysUser> selectAllWithDept();
}
