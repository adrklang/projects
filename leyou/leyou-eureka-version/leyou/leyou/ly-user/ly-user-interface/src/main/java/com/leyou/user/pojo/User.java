package com.leyou.user.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import tk.mybatis.mapper.annotation.KeySql;

@Table(name = "tb_user")
@Data
@ApiModel(value = "用户",description = "用户对象")
public class User {
    @Id
    @KeySql(useGeneratedKeys = true)
    @ApiModelProperty(hidden = true)
    private Long id;

    @NotEmpty(message = "用户名不能为空")
    @Length(min = 4,max = 32,message = "用户名长度必须在4-32位")
    @ApiModelProperty(dataType = "String",required = true,name = "username",value = "用户名")
    private String username;// 用户名

    @Length(min = 4,max = 32,message = "密码长度必须在4-32位")
    @JsonIgnore
    @ApiModelProperty(dataType = "String",required = true,name = "password",value = "密码")
    private String password;// 密码

    @Pattern(regexp = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$",message = "手机号不正确")
    @ApiModelProperty(dataType = "String",required = true,name = "phone",value = "手机号")
    private String phone;// 电话

    @ApiModelProperty(hidden = true)
    private Date created;// 创建时间

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private String salt;// 密码的盐值
}
