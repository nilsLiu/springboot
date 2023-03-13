package com.lch.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 *
 * </p>
 *
 * @author hai
 * @since 2023-02-11
 */
@Getter
@Setter
@TableName("sys_user")
@ApiModel(value = "User对象", description = "")
@ToString

public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    @ApiModelProperty("年龄")
    private Double age;

    @ApiModelProperty("PSA指数")
    private Double psa;

    @ApiModelProperty("患病概率")
    private Double pcaProbability;

    @ApiModelProperty("创建时间")
    private LocalDateTime creatTime;

    @ApiModelProperty("username")
    private String username;

    @ApiModelProperty("password")
    private String password;

}
