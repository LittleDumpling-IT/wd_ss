package com.wendu.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 用户实体类
 * @author LittleDumpling
 */

@ApiModel("用户实体")
@Data
@Table(name = "wd_admin")
public class Admin implements Serializable {
    @Id
    @ApiModelProperty("用户id")
    private Integer id;

    @Column(name = "username")
    @ApiModelProperty("用户名")
    private String username;

    @Column(name = "password")
    @ApiModelProperty("用户密码")
    private String password;

    @Column(name = "mobile")
    @ApiModelProperty("用户手机号")
    private String mobile;

    @Column(name = "is_delete")
    @ApiModelProperty("删除")
    private String isDelete;
}
