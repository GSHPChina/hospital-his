package com.his.system.dto;

import javax.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private Long id;
    @NotBlank(message = "用户名不能为空")
    private String username;
    private String password;
    @NotBlank(message = "真实姓名不能为空")
    private String realName;
    private Integer gender;
    private String phone;
    private String idCard;
    private Long deptId;
    private String post;
    private Integer status;
    private List<Long> roleIds;
}
