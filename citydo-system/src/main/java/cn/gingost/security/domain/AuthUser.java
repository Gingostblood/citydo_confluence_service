package cn.gingost.security.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author:lezzy
 * @Date:2020/7/27 17:04
 */

@Data
@ApiModel("登录授权")
public class AuthUser {

    @ApiModelProperty("用户名")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @ApiModelProperty("用户名")
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty("验证码id")
    private String uuid;

    @ApiModelProperty("验证码结果")
    @NotBlank(message = "验证码不能为空")
    private String code;
}
