package cn.gingost.system.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

/**
 * @author Lezzy
 * @description
 * @data 2020/8/1 22:47
 **/

@Data
@ApiModel("用户")
public class UserReqDto implements Serializable {
    @ApiModelProperty("id")
    @NotNull(message = "用户id不能为空", groups = Update.class)
    private Long id;

    @ApiModelProperty("用户名")
    @NotBlank(message = "用户id不能为空", groups = {Update.class, Create.class})
    private String nickName;

    @ApiModelProperty("性别")
    @NotBlank(message = "用户id不能为空", groups = {Update.class, Create.class})
    private String sex;

    @ApiModelProperty("部门id")
    private Long deptId;

    @ApiModelProperty("部门名")
    private Long deptName;

    @ApiModelProperty("角色")
    private Set<Long> roleId;

    @ApiModelProperty("角色名")
    private Set<Long> roleName;

//    @ApiModelProperty("职位id")
//    private Long jobId;
//
//    @ApiModelProperty("职位名")
//    private Long jobName;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("工号")
    private String card;

    @ApiModelProperty("电话")
    private String phone;


    public @interface Update {
    }

    public @interface Create {
    }
}
